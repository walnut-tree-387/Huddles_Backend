package com.example.Threading.HuddleMember;

import com.example.Threading.Huddle.Huddle;
import com.example.Threading.HuddleMember.Dto.HuddleMemberRelation;
import com.example.Threading.Users.AppUser;
import com.example.Threading.Users.Dto.AppUserCreateDto;

import java.util.List;
import java.util.UUID;

public interface HuddleMemberService {
    List<HuddleMember> getHuddleUsers(UUID huddleUuid);
    List<HuddleMemberRepository.huddleMember> getHuddleUserList(UUID huddleUuid);
    void addNewUsers(List<AppUserCreateDto> users, Huddle huddle);
    void removeUser(UUID userUuid, UUID huddleUuid);
    void addCurrentUserToHuddleAsCreator(Huddle huddle);
    Boolean checkIfAlreadyExist(UUID userUuid, UUID huddleUuid);
    void createHuddleMemberRequest(Huddle huddle);
    HuddleMemberRelation getHuddleMemberRelation(Huddle huddle);
    List<HuddleMemberRepository.huddleJoinRequest> getJoinRequests(UUID huddleUuid);
    void processHuddleMemberRequest(Huddle huddle, AppUser user, String action);
    HuddleMember getHuddleMemberEntry(UUID huddleUuid, UUID userUuid, HuddleMemberStatus huddleMemberStatus);
}
