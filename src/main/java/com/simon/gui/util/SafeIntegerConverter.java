package com.simon.gui.util;

public class SafeIntegerConverter extends javafx.util.converter.IntegerStringConverter {
    @Override
    public Integer fromString(String value) {
        try {
            if (value == null || value.trim().isEmpty())
                return null;
            Integer result = Integer.parseInt(value.trim());
            return
                (result < 1) ? null : result;
        }

        catch (Exception e) {
            return null;
        }
    }
}