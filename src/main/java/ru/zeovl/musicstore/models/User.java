package ru.zeovl.musicstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

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

    @NotNull
    @Size(min = 8, max = 150)
    @Column(name = "password")
    private String password;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "date_joined")
    private LocalDate dateJoined;

    public User() {
        this.role = "ROLE_USER";
        this.dateJoined = LocalDate.now();
    }
}

