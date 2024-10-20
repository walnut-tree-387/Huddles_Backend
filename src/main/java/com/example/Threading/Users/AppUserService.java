package com.example.Threading.Users;

import com.example.Threading.Users.Dto.AppUserCreateDto;
import com.example.Threading.Users.Dto.AppUserGetDto;

import java.util.List;
import java.util.UUID;

public interface AppUserService {
    AppUserGetDto create(AppUserCreateDto createDto);

    List<AppUserGetDto> getUsersExceptCurrentUser();
    List<AppUserGetDto> getUsersNotInAHuddle(UUID huddleUuid);
    AppUser getUserByUuid(UUID uuid);
    AppUser getByUserName(String userName);
    AppUser getCurrentUser();
}