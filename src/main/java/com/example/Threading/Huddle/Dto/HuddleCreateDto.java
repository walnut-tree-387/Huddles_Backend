package com.example.Threading.Huddle.Dto;

import com.example.Threading.Users.Dto.AppUserCreateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class HuddleCreateDto extends HuddleBaseDto{
    private List<AppUserCreateDto> newUsers = new ArrayList<>();
}
