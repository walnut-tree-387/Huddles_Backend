package com.example.Threading.HuddleEvent;

import com.example.Threading.Huddle.Huddle;
import com.example.Threading.Users.AppUser;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class HuddleEventServiceImpl implements HuddleEventService{
    private final ApplicationEventPublisher eventPublisher;
    private final HuddleEventRepository eventRepository;

    public HuddleEventServiceImpl(ApplicationEventPublisher eventPublisher, HuddleEventRepository eventRepository) {
        this.eventPublisher = eventPublisher;
        this.eventRepository = eventRepository;
    }

    @Override
    public void create(HuddleEventType type, String message, AppUser creator, Huddle huddle) {
        HuddleEvent newEvent = new HuddleEvent();
        newEvent.setEventType(type);
        newEvent.setMessage(message);
        newEvent.setCreator(creator);
        newEvent.setHuddle(huddle);
        newEvent.setCreatedAt(Instant.now().getEpochSecond());
        newEvent = eventRepository.save(newEvent);
        eventPublisher.publishEvent(newEvent);
    }
}
