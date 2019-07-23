import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/test")
public class WebSocketTest {
    private final Logger logger = LogManager.getLogger(WebSocketTest.class);
    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connection opened for {}", session.getId());
        sessionMap.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("Connection closed for {}", session.getId());
        sessionMap.remove(session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        sessionMap.forEach((id, _session) -> {
            if (id.hashCode() != session.getId().hashCode() && !id.equals(session.getId())) {
                _session.getAsyncRemote().sendText("Reply for : " + message);
            }
        });
    }

    @OnError
    public void onError(Session session, Throwable e) {
        logger.error(session.getId() + "\t" + e.getMessage(), e);
    }
}