package com.example.Threading.HuddleMember;

import com.example.Threading.Huddle.Huddle;
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
}
