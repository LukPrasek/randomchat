package pl.lukaszprasek.randomchat.models.commands;

import org.springframework.web.socket.TextMessage;
import pl.lukaszprasek.randomchat.models.UserModel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OnlineIPCommand extends MainCommand {
    public OnlineIPCommand(List<UserModel> userModels) {
        super(userModels);
    }

    @Override
    public boolean executeCommand(UserModel sender, String... args) throws IOException {
        String allUsers = getAllUsers().stream().map(s -> s.getUserSession()
                .getRemoteAddress().getHostName())
                .collect(Collectors.joining("; ", "Adresy IP uwszystkich uczestnikow czatu: ", "."));
        sender.sendMessage(new TextMessage(allUsers));
        return true;
    }

    @Override
    public String info() {
        return null;
    }
}
