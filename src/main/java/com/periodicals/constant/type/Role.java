package com.periodicals.constant.type;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    NOT_FOUND("not_found");

    private final String name;

    public String getName() {
        return name;
    }

    Role(String title) {
        this.name = title;
    }

    public static Role fromValue(String name) {
        for (Role role : values()) {
            if (role.name.equalsIgnoreCase(name) || name.equalsIgnoreCase(role.name()))
                return role;
        }
        return NOT_FOUND;
    }

    @Override
    public String toString() {
        return name;
    }
}
