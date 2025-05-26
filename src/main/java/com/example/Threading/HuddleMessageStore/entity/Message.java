package com.example.Threading.HuddleMessageStore.entity;

import com.example.Threading.Huddle.Huddle;
import com.example.Threading.HuddleMember.HuddleMember;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;
    private Long createdAt;
    private Long updatedAt;
    @Column(length = 1000)
    private String text;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private HuddleMember createdBy;
    @ManyToOne
    @JoinColumn(name = "huddle_uuid")
    private Huddle huddle;
}
