package com.example.Threading.HuddleBuddies.Dto;

import lombok.Data;

import java.util.UUID;

@Data
public class HuddleBuddyGetDto {
    private UUID uuid;
    private String name;

    public HuddleBuddyGetDto(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
