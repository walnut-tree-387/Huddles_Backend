package com.example.Threading.HuddleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class NotificationController {
    @SendToUser("/topic/notification")
    public ResponseEntity<HuddleEvent> greeting(HuddleEvent notification, @Header("simpSessionId") String sessionId, Principal principal) throws Exception {
        log.info("Received new notification {} from {} with sessionId {}", notification, principal.getName(), sessionId);
        Thread.sleep(1000);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
    @MessageMapping("/topic/notification")
    public void receiveMessageFromClient(Object msg) throws Exception {
        log.info("Received new notification {} .", msg);
    }
}