package com.mirald.persistence.repository.contract;

public enum TableNames {
    CLIENT("client"),
    AUTHORS("authors"),
    TITLE("title"),
    STORY("story"),
    USER("users");
    private final String name;

    TableNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
