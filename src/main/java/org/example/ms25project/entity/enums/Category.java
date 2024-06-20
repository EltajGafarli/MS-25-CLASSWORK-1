package org.example.ms25project.entity.enums;

public enum Category {
    BOOK("KITAB"), ELECTRONICS("ELEKTIRONKA"), MEISET("MEISET");
    private String value;
    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
