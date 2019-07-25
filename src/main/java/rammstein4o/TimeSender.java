package rammstein4o;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TimeSender {
    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    @Autowired
    public TimeSender(final SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @Scheduled(fixedRate = 30000)
    public void auth() {
        Response resp = new Response();
        resp.type = "AUTHENTICATE";
        resp.payload = new String("0000a8f2334f4b93-de09d91657c60000");
        sendingOperations.convertAndSend("/topic/greetings", resp);
    }

    @Scheduled(fixedRate = 8000)
    public void balance() {
        Response resp = new Response();
        resp.type = "ACCOUNT_CHANGE_BALANCE";
        resp.payload = new Balance();
        sendingOperations.convertAndSend("/topic/greetings", resp);
    }

    @Scheduled(fixedRate = 50000)
    public void unknown() {
        Response resp = new Response();
        resp.type = "UNKNOWN_TYPE_JUST_FOR_TEST";
        resp.payload = new Balance();
        sendingOperations.convertAndSend("/topic/greetings", resp);
    }
}
