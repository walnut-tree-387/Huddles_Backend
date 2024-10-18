package com.example.Threading.HuddleEvent;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HuddleEventDto {
    private String message;
    private HuddleEventType eventType;
    private String creator;
    private String huddle;
    private Long createdAt;
}
