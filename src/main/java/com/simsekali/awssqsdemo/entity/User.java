package com.simsekali.awssqsdemo.entity;

import com.simsekali.awssqsdemo.controller.dto.UserCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column
    private String validationToken;

    private LocalDateTime validationTokenExpiry;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public static User create(UserCreateRequest request) {
        User user = new User();
        user.email = request.getEmail();
        user.username = request.getUsername();
        user.password = request.getPassword();
        user.validationToken = UUID.randomUUID().toString();
        user.validationTokenExpiry = LocalDateTime.now().plusHours(24);
        return user;
    }

    public void validateUser() {
        this.isActive = true;
        this.validationToken = null;
        this.validationTokenExpiry = null;
    }
}
