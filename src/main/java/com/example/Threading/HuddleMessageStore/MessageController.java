package com.example.Threading.HuddleMessageStore;

import com.example.Threading.HuddleMessageStore.dto.MessageCreateDto;
import com.example.Threading.HuddleMessageStore.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MessageCreateDto message) {
        return new ResponseEntity<>(messageService.create(message), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getMessages(@RequestParam("huddleUuid") UUID huddleUuid,
                                    @RequestParam("pageSize") int pageSize,
                                    @RequestParam("pageNumber") int pageNumber) {
        return new ResponseEntity<>(messageService
                .getHuddleMessages(huddleUuid, pageSize, pageNumber), HttpStatus.OK);
    }
}
