package com.backend.myhouse.util;

import java.io.IOException;
import java.util.Base64;

public class Base64Utility {
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String base64Data) throws IOException {
        return Base64.getDecoder().decode(base64Data);
    }
}
