package pl.lukaszprasek.randomchat.models;


import lombok.Data;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


@Data
public class UserModel {
    private String nickname;
    private WebSocketSession userSession;

    public UserModel(WebSocketSession userSession) {
        this.userSession = userSession;
    }

    public void sendMessage(TextMessage textMessage) throws IOException {
        userSession.sendMessage(textMessage);
    }

    public void sendServerMessage(TextMessage message) throws IOException {
        userSession.sendMessage(new TextMessage("server:" + message.getPayload()));
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String replace) {
        this.nickname = replace;
    }

    public WebSocketSession getUserSession() {
        return userSession;
    }
}
