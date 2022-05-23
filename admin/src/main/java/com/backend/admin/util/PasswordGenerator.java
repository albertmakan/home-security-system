package com.backend.admin.util;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
    private static final char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] UPPERCASE = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
    private static final char[] DIGITS = "0123456789".toCharArray();
    private static final char[] SYMBOLS = "^$?!@#%&".toCharArray();
    private static final char[] ALL_ALLOWED = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789^$?!@#%&".toCharArray();
    private static final Random RANDOM = new SecureRandom();

    public static String generateNumberSequence(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(DIGITS[RANDOM.nextInt(DIGITS.length)]);
        return sb.toString();
    }

    public static String generatePassword (int length) {
        if (length < 4) length = 6;
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length - 4; i++)
            password.append(ALL_ALLOWED[RANDOM.nextInt(ALL_ALLOWED.length)]);
        password.insert(RANDOM.nextInt(password.length()), LOWERCASE[RANDOM.nextInt(LOWERCASE.length)]);
        password.insert(RANDOM.nextInt(password.length()), UPPERCASE[RANDOM.nextInt(UPPERCASE.length)]);
        password.insert(RANDOM.nextInt(password.length()), DIGITS[RANDOM.nextInt(DIGITS.length)]);
        password.insert(RANDOM.nextInt(password.length()), SYMBOLS[RANDOM.nextInt(SYMBOLS.length)]);
        return password.toString();
    }

}
