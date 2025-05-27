package com.example.Threading.HuddleMessageStore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessageCreateDto extends MessageBaseDto{
    @NotNull(message = "huddle uuid is required")
    private UUID huddleUuid;
}
