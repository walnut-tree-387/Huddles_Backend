package com.example.Threading.HuddleEvent;

import com.example.Threading.WebSocket.StompPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HuddleEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    public HuddleEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Async("notificationExecutor")
    @EventListener
    public void handleHuddleEvent(HuddleEvent event){
        log.info(event.getMessage());
        String userUuid = event.getHuddle().getCreator().getUuid().toString();
        HuddleEventDto response = new HuddleEventDto().setHuddle(event.getHuddle().getName())
                .setEventType(event.getEventType())
                .setMessage(event.getMessage())
                .setCreator(event.getCreator().getName())
                .setCreatedAt(event.getCreatedAt());
        messagingTemplate.convertAndSendToUser(userUuid, "/queue/notification", response);
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        System.out.println("Disconnected session: " + sessionId);
    }
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String topic = accessor.getDestination();
        System.out.println("New subscription to: " + topic);
    }
}
