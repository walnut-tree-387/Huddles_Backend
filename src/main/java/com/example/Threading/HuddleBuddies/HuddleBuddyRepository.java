package com.example.Threading.HuddleBuddies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HuddleBuddyRepository extends JpaRepository<HuddleBuddy, UUID> {
    @Query("SELECT hb FROM HuddleBuddy hb WHERE hb.acceptedBy.uuid = :userUuid AND hb.status = :status")
    List<HuddleBuddy> getAllRequestedEntries(@Param("userUuid") UUID userUuid, @Param("status") HuddleRequestStatus status);
    @Query("SELECT hb FROM HuddleBuddy hb WHERE hb.status = :status AND " +
            "(hb.acceptedBy.uuid = : userUuid OR hb.requestedBy.uuid = :userUuid)")
    List<HuddleBuddy> getAllFriends(@Param("userUuid") UUID userUuid, @Param("status") HuddleRequestStatus status);
}
