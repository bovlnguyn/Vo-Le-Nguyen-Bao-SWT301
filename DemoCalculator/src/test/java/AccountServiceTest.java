import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import volenguyenbao.example.AccountService;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final AccountService service = new AccountService();

    @ParameterizedTest(name = "[{index}] user={0}, pass={1}, email={2} => expected={3}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("registerAccount: check all rules from CSV and log each case")
    void registerFromCsv(String username, String password, String email, boolean expected) {
        boolean actual = service.registerAccount(username, password, email);

        System.out.printf(
                "→ CASE: username='%s' | password='%s' | email='%s' | expected=%s | actual=%s | %s%n",
                username,
                password,
                email,
                expected,
                actual,
                (expected == actual ? "✅ PASS" : "❌ FAIL")
        );

        assertEquals(expected, actual);
    }
}
