package com.example.Threading.HuddleMember.Dto;

import com.example.Threading.HuddleMember.HuddleMember;
import com.example.Threading.HuddleMember.HuddleMemberStatus;
import com.example.Threading.HuddleMember.HuddleRole;
import lombok.Data;

@Data
public class HuddleMemberBaseDto {
    private HuddleMemberStatus status;
    private HuddleRole role;
}
