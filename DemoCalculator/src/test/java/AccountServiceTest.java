
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import volenguyenbao.example.AccountService;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final AccountService service = new AccountService();

    @ParameterizedTest
    @ValueSource(strings = {"john@example.com", "a.b+1@mail.co", "user@sub.domain.org"})
    @DisplayName("isValidEmail returns true for valid samples")
    void validEmails(String email) {
        assertTrue(service.isValidEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "no-at.com", "name@domain", "x@y", "bad@@mail.com"})
    @DisplayName("isValidEmail returns false for invalid samples")
    void invalidEmails(String email) {
        assertFalse(service.isValidEmail(email));
    }

    @ParameterizedTest(name = "[{index}] user={0}, pass={1}, email={2} => expected={3}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("registerAccount: check result from CSV")
    void registerFromCsv(String username, String password, String email, boolean expected) {
        boolean actual = service.registerAccount(username, password, email);
        assertEquals(expected, actual);
    }
}
