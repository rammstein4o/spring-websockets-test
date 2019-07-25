package rammstein4o.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
public class MySessionHandler extends StompSessionHandlerAdapter {

    private static StompSession session;
    private static ObjectMapper oMapper = new ObjectMapper();
    private static HashMap<ResponseType, List<Map>> queues = new HashMap<>();

    @Override
    public void afterConnected(StompSession mySession, StompHeaders connectedHeaders) {
        session = mySession;
        session.subscribe("/topic/greetings", this);

        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession mySession, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Object.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object response) {
        Map<String, Object> responseMap = oMapper.convertValue(response, Map.class);
        String type = ((String) responseMap.get("type"));
        if (type != null) {
            ResponseType responseType;
            try {
                responseType = ResponseType.valueOf(type);
            } catch (IllegalArgumentException err) {
                responseType = ResponseType.OTHER;
            }

            addToQueue(responseType, responseMap);
        }
    }

    public void addToQueue(ResponseType type, Map item) {
        List<Map> queue = queues.get(type);
        if (queue == null) {
            queue = new ArrayList<Map>();
        }
        queue.add(item);
        queues.put(type, queue);
    }

    public Map getNext(ResponseType type) {
        try {
            List<Map> queue = queues.get(type);
            if (queue.size() == 0) {
                return null;
            }
            Map item = queue.remove(0);
            return item;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public StompSession getSession() {
        return session;
    }
}
