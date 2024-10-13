package com.example.Threading.Huddle;

import com.example.Threading.Huddle.Dto.HuddleCreateDto;
import com.example.Threading.Huddle.Dto.HuddleGetDto;
import com.example.Threading.Huddle.Dto.HuddleUpdateDto;

import java.util.List;
import java.util.UUID;

public interface HuddleService {
    void create(HuddleCreateDto createDto);
    List<HuddleGetDto> getHuddles(UUID currentUserUuid);
    void addUsersToHuddle(HuddleUpdateDto updateDto);
    Huddle getById(UUID uuid);
    List<HuddleGetDto> getAllHuddlesOfLoggedInUser(UUID userUuid);
}
