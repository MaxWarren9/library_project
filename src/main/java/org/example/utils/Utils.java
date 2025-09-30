package org.example.utils;

public class Utils {
    public static int parseNumber(String str) {
        try {
            String value = str.trim().replaceAll("[^\\d-]", "");
            if (value.isEmpty() || value.equals("-")) {
                throw new IllegalArgumentException("В строке '" + str + "' не найдено числового значения");
            }
            return Integer.parseInt(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка парсинга числа: " + e.getMessage());
        }
    }
}
