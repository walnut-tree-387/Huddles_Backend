package com.example.Threading.HuddleMember;
import com.example.Threading.Huddle.Huddle;
import com.example.Threading.HuddleMember.Dto.HuddleMemberRelation;
import com.example.Threading.Users.AppUser;
import com.example.Threading.Users.AppUserDetailsService;
import com.example.Threading.Users.AppUserService;
import com.example.Threading.Users.Dto.AppUserCreateDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
        return huddleMemberRepository.getMembersByIdHuddleUuid(huddleUuid, HuddleMemberStatus.JOINED);
    }
    @Override
    public List<HuddleMemberRepository.huddleMember> getHuddleUserList(UUID huddleUuid) {
        return huddleMemberRepository.getHuddleMemberListByIdHuddleUuid(huddleUuid, HuddleMemberStatus.JOINED);
    }


    @Override
    public void addNewUsers(List<AppUserCreateDto> users, Huddle huddle) {
        users.forEach(user -> {
            if(!checkIfAlreadyExist(user.getUuid(), huddle.getUuid())){
                HuddleMember huddleMember = new HuddleMember();
                huddleMember.setHuddle(huddle);
                AppUser userEntity = appUserService.getUserByUuid(user.getUuid());
                huddleMember.setMember(userEntity);
                huddleMember.setHuddleMemberStatus(HuddleMemberStatus.JOINED);
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
        huddleMember.setHuddleMemberStatus(HuddleMemberStatus.JOINED);
        huddleMember.setMember(userEntity);
        huddleMember.setHuddleRole(HuddleRole.CREATOR);
        huddleMember.setJoinedAt(Instant.now().getEpochSecond());
        huddleMemberRepository.save(huddleMember);
    }
    @Override
    public Boolean checkIfAlreadyExist(UUID uuid, UUID huddleUuid){
        return huddleMemberRepository.userExistInHuddle(huddleUuid, uuid, HuddleMemberStatus.JOINED);
    }

    @Override
    public void createHuddleMemberRequest(Huddle huddle) {
        if(checkIfAlreadyExist(userDetailsService.getLoggedInUser().getUuid(), huddle.getUuid())) return;
        HuddleMember huddleMember = new HuddleMember();
        huddleMember.setHuddle(huddle);
        AppUser userEntity = userDetailsService.getLoggedInUser();
        huddleMember.setHuddleMemberStatus(HuddleMemberStatus.REQUESTED);
        huddleMember.setMember(userEntity);
        huddleMember.setHuddleRole(HuddleRole.MEMBER);
        huddleMemberRepository.save(huddleMember);
    }

    @Override
    public HuddleMemberRelation getHuddleMemberRelation(Huddle huddle) {
        Optional<HuddleMember> huddleMemberOptional = huddleMemberRepository
                .findTopByHuddleAndMemberOrderByJoinedAtDesc(huddle, userDetailsService.getLoggedInUser());
        if(huddleMemberOptional.isEmpty()){
            return new HuddleMemberRelation().setHuddleUuid(huddle.getUuid())
                    .setMemberUuid(userDetailsService.getLoggedInUser().getUuid());
        }
        else{
            return new HuddleMemberRelation().setHuddleUuid(huddle.getUuid())
                    .setMemberUuid(userDetailsService.getLoggedInUser().getUuid())
                    .setStatus(huddleMemberOptional.get().getHuddleMemberStatus());
        }
    }

    @Override
    public List<HuddleMemberRepository.huddleJoinRequest> getJoinRequests(UUID huddleUuid) {
        return huddleMemberRepository.findByHuddleMemberStatus(huddleUuid, HuddleMemberStatus.REQUESTED);
    }

    @Override
    public HuddleMember getHuddleMemberEntry(UUID huddleUuid, UUID userUuid, HuddleMemberStatus huddleMemberStatus) {
        Optional<HuddleMember> optional = huddleMemberRepository.findHuddleMemberByHuddleAndMember(
                huddleUuid, userUuid, huddleMemberStatus
        );
        if(optional.isEmpty()) throw new RuntimeException("Huddle member not found");
        return optional.get();
    }

    @Override
    public void processHuddleMemberRequest(Huddle huddle, AppUser user, String action) {
        HuddleMember huddleMember = getHuddleMemberEntry(huddle.getUuid(), user.getUuid(), HuddleMemberStatus.REQUESTED);
        if(action.equals("ACCEPT")){
            huddleMember.setHuddleMemberStatus(HuddleMemberStatus.JOINED);
        }
        else if(action.equals("DENIED")){
            huddleMember.setHuddleMemberStatus(HuddleMemberStatus.DENIED);
        }
        huddleMemberRepository.save(huddleMember);
    }
}
