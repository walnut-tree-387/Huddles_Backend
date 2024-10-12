package com.example.Threading.Huddle;

import com.example.Threading.Helpers.SystemMapper;
import com.example.Threading.Huddle.Dto.HuddleCreateDto;
import com.example.Threading.Huddle.Dto.HuddleGetDto;
import com.example.Threading.Huddle.Dto.HuddleUpdateDto;
import com.example.Threading.HuddleMember.HuddleMemberService;
import com.example.Threading.Users.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HuddleServiceImpl implements HuddleService{
    private final HuddleRepository huddleRepository;
    private final HuddleMemberService huddleMemberService;
    public HuddleServiceImpl(HuddleRepository huddleRepository, HuddleMemberService huddleMemberService) {
        this.huddleRepository = huddleRepository;
        this.huddleMemberService = huddleMemberService;
    }

    @Override
    public void create(HuddleCreateDto createDto) {
        Huddle huddle = new Huddle();
        huddle.setName(createDto.getName());
        huddle = huddleRepository.save(huddle);
        huddleMemberService.addNewUsers(createDto.getNewUsers(), huddle);
        huddleMemberService.addCurrentUserToHuddleAsCreator(huddle);
    }

    @Override
    public List<HuddleGetDto> getHuddles() {
        return SystemMapper.toDtoList(huddleRepository.findAll(), HuddleGetDto.class);
    }

    @Override
    public void addUsersToHuddle(HuddleUpdateDto updateDto) {
        Huddle huddle = getById(updateDto.getUuid());
        huddleMemberService.addNewUsers(updateDto.getNewUsers(), huddle);
    }

    @Override
    public Huddle getById(UUID uuid) {
        Optional<Huddle> huddleOptional = huddleRepository.findById(uuid);
        if(huddleOptional.isEmpty()) throw new RuntimeException("Huddle couldn't be found with this uuid " + uuid);
        return huddleOptional.get();
    }

    @Override
    public List<HuddleGetDto> getAllHuddlesOfLoggedInUser(UUID userUuid) {
        return SystemMapper.toDtoList(huddleRepository.findUserHuddles(userUuid), HuddleGetDto.class);
    }
}
