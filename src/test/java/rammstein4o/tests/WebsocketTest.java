package rammstein4o.tests;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.testng.annotations.*;
import rammstein4o.utils.GetNextItem;
import rammstein4o.utils.MySessionHandler;
import rammstein4o.utils.ResponseType;

import java.util.Map;

import static java.util.concurrent.TimeUnit.*;
import static org.awaitility.Awaitility.*;
import static org.testng.Assert.assertEquals;

@Slf4j
public class WebsocketTest {

    private static MySessionHandler sessionHandler = new MySessionHandler();

    @BeforeClass
    public void setUp() {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

        String url = "ws://127.0.0.1:8080/hello";
        stompClient.connect(url, sessionHandler);

        await().until(() -> sessionHandler.getSession().isConnected());
    }

    @Test
    public void test1() {
        Map item = waitForItem(ResponseType.ACCOUNT_CHANGE_BALANCE);
        Map payload = (Map) item.get("payload");
        log.info("payload: {}", payload);
        assertEquals("00009321a800dc9c-de09d91657c60000", payload.get("accountId"));
    }

    @Test
    public void test2() {
        Map item = waitForItem(ResponseType.AUTHENTICATE);
        String payload = (String) item.get("payload");
        log.info("payload: {}", payload);
        assertEquals("0000a8f2334f4b93-de09d91657c60000", payload);
    }

    private Map waitForItem(ResponseType responseType) {
        GetNextItem getNextItem = new GetNextItem(sessionHandler, responseType);
        await().atMost(1, MINUTES).pollInterval(5, SECONDS).until(getNextItem);

        return getNextItem.getItem();
    }
}
