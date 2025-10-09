package volenguyenbao.example;

import java.util.regex.Pattern;

public class AccountService {

    // Regex kiểm tra email đơn giản
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Regex kiểm tra mật khẩu mạnh
    private static final Pattern PASS_REGEX =
            Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?/{}~\\-]).{8,}$");

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        String e = email.trim();
        return !e.isEmpty() && EMAIL_REGEX.matcher(e).matches();
    }

    public boolean isValidUsername(String username) {
        if (username == null) return false;
        String u = username.trim();
        return !u.isEmpty() && u.length() > 3;
    }

    public boolean isValidPassword(String password) {
        if (password == null) return false;
        String p = password.trim();
        return PASS_REGEX.matcher(p).matches();
    }

    public boolean registerAccount(String username, String password, String email) {
        if (!isValidUsername(username)) {
            System.out.printf("❌ Invalid username: '%s'%n", username);
            return false;
        }
        if (!isValidPassword(password)) {
            System.out.printf("❌ Weak password: '%s'%n", password);
            return false;
        }
        if (!isValidEmail(email)) {
            System.out.printf("❌ Invalid email: '%s'%n", email);
            return false;
        }
        System.out.printf("✅ All checks passed for user '%s'%n", username);
        return true;
    }

}
