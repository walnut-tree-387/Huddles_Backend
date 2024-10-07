package com.example.Threading.Huddle.Dto;

import com.example.Threading.Users.Dto.AppUserCreateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class HuddleUpdateDto extends HuddleBaseDto{
    private UUID uuid;
    private List<AppUserCreateDto> newUsers = new ArrayList<>();
}
