package com.github.zambrinn.mvcproject.model;

import com.github.zambrinn.mvcproject.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    UserRole role;

    @Column(name = "isActive", nullable = false)
    boolean isActive = true;
}
