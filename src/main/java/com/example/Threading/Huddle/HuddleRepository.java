package com.example.Threading.Huddle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HuddleRepository extends JpaRepository<Huddle, UUID> {
    @Query("SELECT huddle FROM Huddle huddle " +
            "JOIN HuddleMember hm ON hm.huddle = huddle " +
            "WHERE hm.member.uuid = :userUuid AND hm.huddleMemberStatus = 'JOINED'")
    List<Huddle> findUserHuddles(@Param("userUuid") UUID userUuid);

}
