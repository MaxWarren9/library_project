package org.example.util;

public class Utils {
    public static int parseNumber(String str) {
        try {
            String value = str.trim().replaceAll("\\D+", "");
            if (value.isEmpty()) {
                throw new IllegalArgumentException("В строке '" + value + "' не найдено числового значения");
            }
            return Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
            return -1;
        }
    }
}
