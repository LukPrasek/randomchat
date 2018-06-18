package pl.lukaszprasek.randomchat.models;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component//
public class ChatSocket extends TextWebSocketHandler implements WebSocketConfigurer {


    //ze bedziemy wysylac tekst, a nie bajty, itp
    private List<UserModel> userList = new ArrayList<>();

    private Queue<String> lastTenMessages = new ArrayDeque<>();//limit do 10 wiadomosci

    private void addMessageToQue(String message) {
        if (lastTenMessages.size() >= 10) {
            lastTenMessages.poll();//wyrzucamy pierwsza wiadomosc
        }
        lastTenMessages.offer(message);//dodajemy nowa wiadomosc do kolejki
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(this, "/room").setAllowedOrigins("*");
        //Co nam to daje?
        //obsluguje dane i wystawiamy endpoint, czyli adres url
        //* czyli wszystkie adresy ip beda mogly sie podlaczyc pod naszego socketa
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //gdy juz jestem na stronie (załadowano)
        userList.add(new UserModel(session));
        //rozsylam wiadomosci do pozostalych gosci na czacie
        lastTenMessages.forEach(s -> {
            try {
                session.sendMessage(new TextMessage(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userList.remove(findBySession(session));

    }

    private UserModel findBySession(WebSocketSession session) {
        return userList.stream()
                .filter(s -> s.getUserSession().getId().equals(session.getId())).findAny()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        UserModel sender = findBySession(session);
        if (setNickname(message, sender)) return;
        if (sender.getNickname() == null) {
            sender.sendMessage(new TextMessage("Najpierw wprowadz swoj nick"));
        }
        addMessageToQue(message.getPayload());
        userList.forEach(s-> {
            try {
                s.sendMessage(new TextMessage(sender.getNickname()+": "+message.getPayload()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean setNickname(TextMessage message, UserModel sender) throws IOException {
        if (message.getPayload().startsWith("nickname:")) {
            if (sender.getNickname() == null) {
                sender.setNickname(message.getPayload().replace("nickname", ""));
            } else {
                sender.sendMessage(new TextMessage("Nie mozna zmieniac nicku"));
                return true;
            }
        }
        return false;
    }
}
