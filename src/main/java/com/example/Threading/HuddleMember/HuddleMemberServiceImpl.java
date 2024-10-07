package com.example.Threading.HuddleMember;
import com.example.Threading.Huddle.Huddle;
import com.example.Threading.Users.AppUser;
import com.example.Threading.Users.AppUserDetailsService;
import com.example.Threading.Users.AppUserService;
import com.example.Threading.Users.Dto.AppUserCreateDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class HuddleMemberServiceImpl implements HuddleMemberService{
    private final HuddleMemberRepository huddleMemberRepository;
    private final AppUserService appUserService;
    private final AppUserDetailsService userDetailsService;

    public HuddleMemberServiceImpl(HuddleMemberRepository huddleMemberRepository, AppUserService appUserService, AppUserDetailsService userDetailsService) {
        this.huddleMemberRepository = huddleMemberRepository;
        this.appUserService = appUserService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public List<HuddleMember> getHuddleUsers(UUID huddleUuid) {
        return huddleMemberRepository.getMembersByIdHuddleUuid(huddleUuid);
    }
    @Override
    public List<HuddleMemberRepository.huddleMember> getHuddleUserList(UUID huddleUuid) {
        return huddleMemberRepository.getHuddleMemberListByIdHuddleUuid(huddleUuid);
    }


    @Override
    public void addNewUsers(List<AppUserCreateDto> users, Huddle huddle) {
        users.forEach(user -> {
            if(!checkIfAlreadyExist(user.getUuid(), huddle.getUuid())){
                HuddleMember huddleMember = new HuddleMember();
                huddleMember.setHuddle(huddle);
                AppUser userEntity = appUserService.getUserByUuid(user.getUuid());
                huddleMember.setMember(userEntity);
                huddleMember.setHuddleRole(HuddleRole.MEMBER);
                huddleMember.setJoinedAt(Instant.now().getEpochSecond());
                huddleMemberRepository.save(huddleMember);
            }
        });
    }

    @Override
    public void removeUser(UUID userUuid, UUID huddleUuid) {
        huddleMemberRepository.removeUserFromHuddle(huddleUuid, userUuid);
    }
    @Override
    public void addCurrentUserToHuddleAsCreator(Huddle huddle){
        if(checkIfAlreadyExist(userDetailsService.getLoggedInUser().getUuid(), huddle.getUuid())) return;
        HuddleMember huddleMember = new HuddleMember();
        huddleMember.setHuddle(huddle);
        AppUser userEntity = userDetailsService.getLoggedInUser();
        huddleMember.setMember(userEntity);
        huddleMember.setHuddleRole(HuddleRole.CREATOR);
        huddleMember.setJoinedAt(Instant.now().getEpochSecond());
        huddleMemberRepository.save(huddleMember);
    }
    private Boolean checkIfAlreadyExist(UUID uuid, UUID huddleUuid){
        return huddleMemberRepository.userExistInHuddle(uuid, huddleUuid);
    }
}
