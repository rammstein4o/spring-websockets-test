package rammstein4o;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class StompEventListener {
    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @Autowired
    public StompEventListener(final SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @EventListener
    private void handleSessionConnectedEvent(SessionConnectedEvent event) {
        log.info("Someone connected!");
    }

    @EventListener
    private void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        log.info("Someone disconnected!");
    }

    @EventListener
    private void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        Response resp = new Response();
        resp.type = "SOCKET_READY";
        sendingOperations.convertAndSend("/topic/greetings", resp);
    }
}
