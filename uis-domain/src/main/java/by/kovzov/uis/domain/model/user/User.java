package by.kovzov.uis.domain.model.user;

import java.util.Set;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private Set<UserRole> roles;
    private UserStatus status;
}
