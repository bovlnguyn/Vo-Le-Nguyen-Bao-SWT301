
import org.junit.jupiter.api.Test;
import volenguyenbao.example.InsecureLogin;

import static org.junit.jupiter.api.Assertions.*;

class InsecureLoginTest {

    @Test
    void testLoginSuccess() {
        InsecureLogin.login("admin", "123456");
        assertDoesNotThrow(() -> InsecureLogin.login("admin", "123456"));
    }

    @Test
    void testLoginFail() {
        assertDoesNotThrow(() -> InsecureLogin.login("user", "wrongpassword"));
    }

    @Test
    void testPrintUserInfo() {
        InsecureLogin insecureLogin = new InsecureLogin();
        assertDoesNotThrow(() -> insecureLogin.printUserInfo("John Doe"));
    }
}
