package com.example.Threading.Huddle;

import com.example.Threading.Helpers.SystemMapper;
import com.example.Threading.Huddle.Dto.HuddleCreateDto;
import com.example.Threading.Huddle.Dto.HuddleGetDto;
import com.example.Threading.Huddle.Dto.HuddleUpdateDto;
import com.example.Threading.HuddleMember.HuddleMemberService;
import com.example.Threading.Users.AppUser;
import com.example.Threading.Users.AppUserService;
import com.example.Threading.exception.HuddleExceptionMessages;
import com.example.Threading.exception.types.HuddleMappingException;
import com.example.Threading.exception.types.HuddleNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HuddleServiceImpl implements HuddleService{
    private final HuddleRepository huddleRepository;
    private final HuddleMemberService huddleMemberService;
    private final AppUserService userService;

    public HuddleServiceImpl(HuddleRepository huddleRepository, HuddleMemberService huddleMemberService, AppUserService userService) {
        this.huddleRepository = huddleRepository;
        this.huddleMemberService = huddleMemberService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void create(HuddleCreateDto createDto) {
        Huddle huddle = new Huddle();
        huddle.setName(createDto.getName());
        huddle = huddleRepository.save(huddle);
        huddle.setCreator(userService.getCurrentUser());
        huddleMemberService.addNewUsers(createDto.getNewUsers(), huddle);
        huddleMemberService.addCurrentUserToHuddleAsCreator(huddle);
    }

    @Override
    public List<HuddleGetDto> getHuddles(UUID loggedInUserUuid) {
        List<HuddleGetDto> huddles;
        try{
            huddles = SystemMapper.toDtoList(huddleRepository.findAll(), HuddleGetDto.class);
        }catch (Exception exception){
            throw new HuddleMappingException(HuddleServiceImpl.class, HuddleExceptionMessages.MAPPING_ERROR_ENTITY_TO_DTO);
        }
        huddles.forEach(huddle -> {
            huddle.setMembers((long) huddleMemberService.getHuddleUsers(huddle.getUuid()).size());
            huddle.setRelation(huddleMemberService.getHuddleMemberRelation(getById(huddle.getUuid())));
        });
        return huddles;
    }

    @Override
    public void addUsersToHuddle(HuddleUpdateDto updateDto) {
        Huddle huddle = getById(updateDto.getUuid());
        huddleMemberService.addNewUsers(updateDto.getNewUsers(), huddle);
    }

    @Override
    public Huddle getById(UUID uuid) {
        Optional<Huddle> huddleOptional = huddleRepository.findById(uuid);
        if(huddleOptional.isEmpty()) throw new HuddleNotFoundException(HuddleServiceImpl.class, "Huddle couldn't be found with this uuid " + uuid);
        return huddleOptional.get();
    }

    @Override
    public List<HuddleGetDto> getAllHuddlesOfLoggedInUser(UUID userUuid) {
        List<HuddleGetDto> response;
        try{
            response = SystemMapper.toDtoList(huddleRepository.findUserHuddles(userUuid), HuddleGetDto.class);
        }catch (Exception exception){
            throw new HuddleMappingException(HuddleServiceImpl.class, HuddleExceptionMessages.MAPPING_ERROR_ENTITY_TO_DTO);
        }
        return response;
    }
}
