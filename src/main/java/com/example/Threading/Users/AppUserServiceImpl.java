package com.example.Threading.Users;

import com.example.Threading.Helpers.SystemMapper;
import com.example.Threading.Users.Dto.AppUserCreateDto;
import com.example.Threading.Users.Dto.AppUserGetDto;
import com.example.Threading.exception.HuddleExceptionMessages;
import com.example.Threading.exception.types.HuddleMappingException;
import com.example.Threading.exception.types.HuddleNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void create(AppUserCreateDto createDto) {
        AppUser user = SystemMapper.toEntity(createDto, AppUser.class);
        user.setVersion(user.getVersion() + 1);
        user.setUserRole(UserRole.COMMONER);
        appUserRepository.save(user);
    }

    @Override
    public List<AppUserGetDto> getUsersExceptCurrentUser() {
        return SystemMapper.toDtoList(appUserRepository.findAllExceptCurrentUser(getCurrentUser().getUuid()), AppUserGetDto.class);
    }

    @Override
    public List<AppUserGetDto> getUsersNotInAHuddle(UUID huddleUuid) {
        List<AppUserGetDto> appUsers;
        try{
            appUsers = SystemMapper.toDtoList(appUserRepository.findAllFilteredByHuddle(huddleUuid), AppUserGetDto.class);
        }catch (Exception e){
            throw new HuddleMappingException(AppUserServiceImpl.class, HuddleExceptionMessages.MAPPING_ERROR_ENTITY_TO_DTO);
        }
        return appUsers;
    }

    @Override
    public AppUser getUserByUuid(UUID uuid) {
        Optional<AppUser> userOptional = appUserRepository.findById(uuid);
        if(userOptional.isEmpty()) throw new HuddleNotFoundException(AppUserServiceImpl.class, "User couldn't be found with this uuid " + uuid);
        return userOptional.get();
    }

    @Override
    public AppUser getByUserName(String userName) {
        Optional<AppUser> userOptional = appUserRepository.getUserByUserName(userName);
        if(userOptional.isEmpty()) throw new HuddleNotFoundException(AppUserServiceImpl.class, "User couldn't be found with this userName " + userName);
        return userOptional.get();
    }

    @Override
    public AppUser getCurrentUser() {;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getByUserName(userDetails.getUsername());
    }
}
