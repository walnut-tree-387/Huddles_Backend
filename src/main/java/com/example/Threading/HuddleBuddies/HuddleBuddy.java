package com.example.Threading.HuddleBuddies;

import com.example.Threading.Users.AppUser;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Table(name = "huddle_buddies")
@Entity
public class HuddleBuddy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private HuddleRequestStatus status;
    private Long createdAt;
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "requested_by_id")
    private AppUser requestedBy;

    @ManyToOne
    @JoinColumn(name = "accepted_by_id")
    private AppUser acceptedBy;
}
