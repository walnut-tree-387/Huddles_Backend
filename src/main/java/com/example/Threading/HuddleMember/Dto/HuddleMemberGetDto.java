package com.example.Threading.HuddleMember.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class HuddleMemberGetDto extends HuddleMemberBaseDto{
    private UUID uuid;
}
