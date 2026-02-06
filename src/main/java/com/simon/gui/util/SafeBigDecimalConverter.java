package com.simon.gui.util;

public class SafeBigDecimalConverter extends javafx.util.converter.BigDecimalStringConverter {
    @Override
    public java.math.BigDecimal fromString(String value) {
        try {
            if (value == null || value.trim().isEmpty())
                return null;


            return super.fromString(value.replace(",", ".").trim());
        }

        catch (NumberFormatException e) {
            return null;
        }
    }
}