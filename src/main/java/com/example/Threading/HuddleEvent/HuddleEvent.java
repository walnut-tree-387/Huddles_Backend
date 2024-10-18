package com.example.Threading.HuddleEvent;

import com.example.Threading.Huddle.Huddle;
import com.example.Threading.Users.AppUser;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Table(name = "huddle_events")
@Entity
public class HuddleEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    private Long createdAt;
    @Enumerated(EnumType.STRING)
    private HuddleEventType eventType;
    private String message;
    @ManyToOne
    @JoinColumn(name = "creator_uuid")
    private AppUser creator;
    @ManyToOne
    @JoinColumn(name = "huddle_uuid")
    private Huddle huddle;
}
