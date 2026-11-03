package utils;

import java.security.SecureRandom;

public final class RandomTextUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomTextUtils() {
    }

    public static String getRandomText(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must not be negative");
        }

        StringBuilder text = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            text.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return text.toString();
    }
}
