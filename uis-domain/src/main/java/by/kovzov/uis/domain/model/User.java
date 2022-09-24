package by.kovzov.uis.domain.model;

import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String username;
    private String password;
    private UserRole role;
}
