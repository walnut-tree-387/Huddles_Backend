package com.example.Threading.Users;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
@Data
@Table(name = "app_users")
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    private String name;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Column(unique = true, nullable = false)
    private String email;
    @CreationTimestamp
    private Long createdAt;
    private Long version = 0L;
}
