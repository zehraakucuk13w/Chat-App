package util;

public class ValidationUtils {
    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9_.]{6,15}$");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^[a-zA-Z0-9!@#$%^&*()_+=\\-]{8,20}$");
    }

}
