package com.book.enums;

public enum LanguageEnum {
    CHINESE("CHINESE"),
    ENGLISH("ENGLISH");
    private final String code;

    LanguageEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
