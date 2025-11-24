package com.github.zambrinn.mvcproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_name", nullable = false)
    private String name;

    @Column(name = "customer_cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "customer_phone")
    private String phone;

    @Column(name = "customer_email", unique = true)
    private String email;

    @Column(name = "customer_password", nullable = false)
    private String passwordHash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}