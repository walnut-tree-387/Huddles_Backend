package com.example.Threading.HuddleEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface HuddleEventRepository extends JpaRepository<HuddleEvent, UUID> {
}
