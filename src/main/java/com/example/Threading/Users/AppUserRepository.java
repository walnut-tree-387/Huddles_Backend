package com.example.Threading.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> getUserByUserName(String userName);
    @Query("SELECT user FROM AppUser user WHERE user.uuid != :uuid")
    List<AppUser> findAllExceptCurrentUser(@Param("uuid") UUID uuid);

    @Query("SELECT user FROM AppUser user " +
            "WHERE user.uuid NOT IN ( " +
            "    SELECT member.member.uuid FROM HuddleMember member " +
            "    WHERE member.huddle.uuid = :huddleUuid" +
            ")")
    List<AppUser> findAllFilteredByHuddle(@Param("huddleUuid") UUID huddleUuid);
}
