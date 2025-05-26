package com.example.Threading.HuddleMessageStore.service;

import com.example.Threading.HuddleMessageStore.dto.MessageCreateDto;
import com.example.Threading.HuddleMessageStore.dto.MessageGetDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageGetDto create(MessageCreateDto createDto);
    Page<MessageGetDto> getHuddleMessages(UUID huddleUuid, int pageSize, int pageNumber);
}
