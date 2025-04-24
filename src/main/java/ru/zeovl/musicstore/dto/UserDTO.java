package ru.zeovl.musicstore.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.zeovl.musicstore.security.AuthProvider;

@Data
public class UserDTO {

    @NotNull
    @Size(min = 2, max = 100)
    private String username;

    @Size(min = 8, max = 150)
    private String password;

    @Size(min = 8, max = 150)
    private String password2;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @NotNull
    private String providerId;

    @NotNull
    private boolean enabled;

    @NotNull
    private String role;

    public UserDTO() {
        this.role = "ROLE_USER";
        this.enabled = true;
        this.provider = AuthProvider.LOCAL;
        this.providerId = "";
    }
}
