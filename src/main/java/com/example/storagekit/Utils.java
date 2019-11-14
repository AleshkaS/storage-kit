package com.example.storagekit;

import java.util.HashMap;
import java.util.Map;

public final class Utils {

    private Utils() {
    }

    public static Map<String, String> getMessage(String text) {
        Map<String, String> message = new HashMap<>();
        message.put("message", text);
        return message;
    }
}
