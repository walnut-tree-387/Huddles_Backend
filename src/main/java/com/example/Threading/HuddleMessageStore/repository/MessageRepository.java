package com.example.Threading.HuddleMessageStore.repository;

import com.example.Threading.HuddleMessageStore.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m from Message m WHERE m.huddle.uuid = :huddleUuid ")
    Page<Message> getMessages(Pageable pageable, UUID huddleUuid);
}
