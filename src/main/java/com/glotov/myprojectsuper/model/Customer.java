package com.glotov.myprojectsuper.model;

import com.glotov.myprojectsuper.util.RoleConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "customers", uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @Convert(converter = RoleConverter.class)
    private Role role = Role.USER;

    @Column(name = "banned")
    private boolean banned;
}