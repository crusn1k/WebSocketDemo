import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/test")
public class WebSocketTest {

    @OnOpen
    public void onOpen(){
        System.out.println("Connection opened ...");
    }

    @OnClose
    public void onClose(){
        System.out.println("Connection closed ...");
    }

    @OnMessage
    public String onMessage(String message){
        return "Reply for : " + message;
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }
}