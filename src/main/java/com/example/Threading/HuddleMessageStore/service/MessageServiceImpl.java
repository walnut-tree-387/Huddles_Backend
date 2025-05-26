package com.example.Threading.HuddleMessageStore.service;

import com.example.Threading.Helpers.SystemMapper;
import com.example.Threading.Huddle.HuddleService;
import com.example.Threading.HuddleMember.HuddleMemberService;
import com.example.Threading.HuddleMember.HuddleMemberStatus;
import com.example.Threading.HuddleMessageStore.dto.MessageCreateDto;
import com.example.Threading.HuddleMessageStore.dto.MessageGetDto;
import com.example.Threading.HuddleMessageStore.entity.Message;
import com.example.Threading.HuddleMessageStore.repository.MessageRepository;
import com.example.Threading.Users.AppUserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    private final AppUserService appUserService;
    private final HuddleMemberService huddleMemberService;
    private final HuddleService huddleService;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(AppUserService appUserService, HuddleMemberService huddleMemberService, HuddleService huddleService, MessageRepository messageRepository) {
        this.appUserService = appUserService;
        this.huddleMemberService = huddleMemberService;
        this.huddleService = huddleService;
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageGetDto create(MessageCreateDto createDto) {
        Message message = new Message();
        message.setCreatedAt(Instant.now().toEpochMilli());
        message.setText(createDto.getText());
        message.setCreatedBy(huddleMemberService.getHuddleMemberEntry(createDto.getHuddleUuid(),
                appUserService.getCurrentUser().getUuid(), HuddleMemberStatus.JOINED));
        message.setHuddle(huddleService.getById(createDto.getHuddleUuid()));
        message = messageRepository.save(message);
        return SystemMapper.toDto(message, MessageGetDto.class);
    }

    @Override
    public Page<MessageGetDto> getHuddleMessages(UUID huddleUuid, int pageSize, int pageNumber) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Message> messages = messageRepository.getMessages(pageable, huddleUuid);
        return new PageImpl<>(SystemMapper.toDtoList(messages.getContent(), MessageGetDto.class));
    }
}
