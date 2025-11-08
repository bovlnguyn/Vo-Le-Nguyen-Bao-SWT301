package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.RegistrationPage;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("DemoQA – Practice Form (POM)")
class RegistrationTest extends BaseTest {

    static RegistrationPage form;
    static WebDriverWait wait;

    @BeforeAll
    static void init() {
        form = new RegistrationPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @ParameterizedTest(name = "CSV Row {index}: {0} {1} | modal={10}")
    @CsvFileSource(
            resources = "/test-data.csv",
            numLinesToSkip = 1,
            nullValues = {"NULL", "null"}
    )
    @DisplayName("Thử nhiều bộ dữ liệu từ CSV")
    void submitWithVariousData(
            String first, String last, String email,
            String gender, String mobile, String dob,
            String subjectsCSV, String hobbiesCSV,
            String state, String city,
            String expectedModal
    ) {
        form.open();

        form.setFirstName(first)
                .setLastName(last)
                .setEmail(email);

        if (gender != null && !gender.isBlank()) {
            form.selectGender(gender);
        }

        form.setMobile(mobile);

        if (dob != null && !dob.isBlank()) {
            form.setDateOfBirth(dob);
        }

        if (subjectsCSV != null && !subjectsCSV.isBlank()) {
            for (String s : subjectsCSV.split(";")) {
                form.addSubject(s.trim());
            }
        }

        if (hobbiesCSV != null && !hobbiesCSV.isBlank()) {
            for (String h : hobbiesCSV.split(";")) {
                form.selectHobby(h.trim());
            }
        }

        if (state != null && !state.isBlank() && city != null && !city.isBlank()) {
            form.selectStateAndCity(state, city);
        }

        form.submit();

        boolean modalShown = form.isResultModalVisible();
        boolean expectYes = "yes".equalsIgnoreCase(expectedModal.trim());
        if (modalShown) form.closeModal();

        Assertions.assertEquals(expectYes, modalShown,
                () -> "Kỳ vọng modal=" + expectedModal + " nhưng thực tế modal=" + modalShown);
    }
}
