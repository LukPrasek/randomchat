package pl.lukaszprasek.randomchat.models.commands;

import org.springframework.web.socket.TextMessage;
import pl.lukaszprasek.randomchat.models.UserModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OnlineCommand extends MainCommand {
    public OnlineCommand(List<UserModel> allUsers) {
        super(allUsers);
    }

    @Override
    public boolean executeCommand(UserModel sender, String... args) throws IOException {
        String allUser=getAllUsers().stream().map(s->s.getNickname())
                .collect(Collectors.joining(", ", "Wszyscy online: ", "."));
        sender.sendMessage(new TextMessage(allUser));
        return true;
    }



    @Override
    public String info() {
        return null;
    }
}
