package com.example.Threading.Huddle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HuddleRepository extends JpaRepository<Huddle, UUID> {
}
