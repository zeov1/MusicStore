package ru.zeovl.musicstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.zeovl.musicstore.security.AuthProvider;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "username", unique = true)
    private String username;

    @Size(min = 8, max = 150)
    @Column(name = "password")
    private String password;

    @NotNull
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProvider provider;

    @NotNull
    @Column(name = "provider_id")
    private String providerId;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @Column(name = "role")
    private String role;

    @CreationTimestamp
    @Column(name = "date_joined")
    private LocalDate dateJoined;

    public User() {
        this.role = "ROLE_USER";
        this.enabled = true;
        this.provider = AuthProvider.LOCAL;
        this.providerId = "";
    }
}

