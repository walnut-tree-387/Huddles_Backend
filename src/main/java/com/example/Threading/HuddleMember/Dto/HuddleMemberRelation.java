package com.example.Threading.HuddleMember.Dto;

import com.example.Threading.HuddleMember.HuddleMemberStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class HuddleMemberRelation {
    private UUID huddleUuid;
    private UUID memberUuid;
    private HuddleMemberStatus status;
}
