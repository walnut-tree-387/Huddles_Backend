package com.example.Threading.HuddleMember;

import com.example.Threading.Huddle.Huddle;
import com.example.Threading.Users.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.UUID;

@Data
@Table(name = "huddle_members")
@Entity
public class HuddleMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    private Long joinedAt;
    @Enumerated(EnumType.STRING)
    private HuddleRole huddleRole;
    @Enumerated(EnumType.STRING)
    private HuddleMemberStatus huddleMemberStatus;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "member_uuid")
    private AppUser member;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "huddle_uuid")
    private Huddle huddle;
}
