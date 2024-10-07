package com.example.Threading.Huddle.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class HuddleGetDto extends HuddleBaseDto{
    private UUID uuid;
}
