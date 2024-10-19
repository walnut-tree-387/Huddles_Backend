package com.example.Threading.HuddleMember;
import com.example.Threading.Huddle.Huddle;
import com.example.Threading.Users.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HuddleMemberRepository extends JpaRepository<HuddleMember, UUID> {
    @Query("SELECT hm FROM HuddleMember hm WHERE hm.huddle.uuid = :huddleUuid AND hm.huddleMemberStatus = :status")
    List<HuddleMember> getMembersByIdHuddleUuid(@Param("huddleUuid") UUID huddleUuid,
                                                @Param("status") HuddleMemberStatus status);
    Optional<HuddleMember> findTopByHuddleAndMemberOrderByJoinedAtDesc(Huddle huddle, AppUser member);

    @Query("    SELECT hm.joinedAt as joinedAt, hm.huddleRole as huddleRole, appUser.name as name, appUser.uuid as memberUuid" +
            "   FROM HuddleMember hm" +
            "   JOIN AppUser appUser ON appUser.uuid = hm.member.uuid" +
            "   WHERE hm.huddle.uuid = :huddleUuid AND hm.huddleMemberStatus = :status")
    List<huddleMember> getHuddleMemberListByIdHuddleUuid(@Param("huddleUuid") UUID huddleUuid,
                                                         @Param("status") HuddleMemberStatus status);
    @Transactional
    @Modifying
    @Query("DELETE FROM HuddleMember hm WHERE hm.huddle.uuid = :huddleUuid AND hm.member.uuid = :memberUuid")
    void removeUserFromHuddle(@Param("huddleUuid") UUID huddleUuid, @Param("memberUuid") UUID memberUuid);


    @Query("SELECT CASE WHEN COUNT(hm) > 0 THEN TRUE ELSE FALSE END " +
            "FROM HuddleMember hm WHERE hm.huddle.uuid = :huddleUuid AND hm.member.uuid = :userUuid AND hm.huddleMemberStatus = :status")
    Boolean userExistInHuddle(@Param("huddleUuid") UUID huddleUuid,
                              @Param("userUuid") UUID userUuid,
                              @Param("status") HuddleMemberStatus status);
    @Query("SELECT appUser as huddleMember" +
            " FROM HuddleMember hm" +
            "  JOIN AppUser appUser ON appUser.uuid = hm.member.uuid" +
            "   WHERE hm.huddle.uuid = :huddleUuid AND hm.huddleMemberStatus = :status")
    List<huddleJoinRequest> findByHuddleMemberStatus(@Param("huddleUuid") UUID huddleUuid,
                                                @Param("status") HuddleMemberStatus status);
    @Query("Select hm from HuddleMember hm WHERE hm.huddle.uuid = :huddleUuid AND hm.member.uuid = :memberUuid " +
            " AND hm.huddleMemberStatus = :huddleMemberStatus")
    Optional<HuddleMember> findHuddleMemberByHuddleAndMember(@Param("huddleUuid") UUID huddleUuid,
                                                             @Param("memberUuid") UUID memberUuid,
                                                             @Param("huddleMemberStatus") HuddleMemberStatus huddleMemberStatus);
    interface huddleMember {
        Long getJoinedAt();
        HuddleRole getHuddleRole();
        String getName();
        UUID getMemberUuid();
    }
    interface huddleJoinRequest{
        AppUser getHuddleMember();
    }

}
