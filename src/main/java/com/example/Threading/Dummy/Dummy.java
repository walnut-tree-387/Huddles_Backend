package com.example.Threading.Dummy;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
@Data
@Table(name = "dummy")
@Entity
public class Dummy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    private String name;
    @CreationTimestamp
    private Long createdAt;
    @UpdateTimestamp
    private Long updatedAt;
    private Long version = 0L;
}
