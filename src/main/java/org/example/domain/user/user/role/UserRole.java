package org.example.domain.user.user.role;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    PAID("ROLE_PAID");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
