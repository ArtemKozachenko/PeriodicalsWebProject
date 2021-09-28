package com.periodicals.util;

import com.periodicals.bean.User;
import com.periodicals.exception.DBException;
import com.periodicals.manager.UserManager;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtils {
    public static final int ITERATIONS = 1000;
    public static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final SecureRandom RAND = new SecureRandom();

    private PasswordUtils() {
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        RAND.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashThePlainTextPassword(String textPassword, String salt) {
        char[] chars = textPassword.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
        Arrays.fill(chars, Character.MIN_VALUE);
        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            //Handle the exception
            return null;
        } finally {
            spec.clearPassword();
        }
    }

    public static User verifyThePlainTextPassword(String textPassword, String login) throws DBException {
        User user = UserManager.getInstance().findUserByLogin(login);
        if (user == null) {
            return null;
        }
        String hashedPassword = user.getPassword();
        String salt = user.getSalt();
        String optEncrypted = hashThePlainTextPassword(textPassword, salt);

        return hashedPassword.equals(optEncrypted) ? user : null;
    }

    public static boolean isValidatedPassword(String password) {
        return password.matches("(?m)^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$");
    }

    public static boolean isConfirmedPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
