package com.book.enums;

public enum BookCategory {
    CHILDREN("CHILDREN"),
    TEENAGER("TEENAGER"),
    ADULT("ADULT");
    private final String code;

    BookCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
