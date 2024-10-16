package com.example.Threading.HuddleEvent;

import com.example.Threading.Huddle.Huddle;
import com.example.Threading.Users.AppUser;

import java.util.UUID;

public interface HuddleEventService {
    void create(HuddleEventType type, String message, AppUser user, Huddle huddle);
}
