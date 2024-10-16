package com.example.Threading.Huddle;

import com.example.Threading.Users.AppUser;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.UUID;

@Data
@Table(name = "huddles")
@Entity
public class Huddle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    private String name;
    private String avatar;
    private Long members;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private AppUser creator;
}
