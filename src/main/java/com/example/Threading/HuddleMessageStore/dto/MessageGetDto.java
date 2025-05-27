package com.example.Threading.HuddleMessageStore.dto;

import com.example.Threading.HuddleMember.Dto.HuddleMemberGetDto;
import com.example.Threading.Users.Dto.AppUserGetDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageGetDto extends MessageBaseDto{
    private UUID uuid;
    private HuddleMemberGetDto createdBy;
    private Long createdAt;
    private Long updatedAt;
    private boolean isOutgoing;
}
