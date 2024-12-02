package AuthService;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashed = hashPassword(password);
        return BCrypt.checkpw(password, hashedPassword);
    }
}
