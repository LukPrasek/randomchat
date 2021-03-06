package pl.lukaszprasek.randomchat.models.commands;

import lombok.Getter;
import pl.lukaszprasek.randomchat.models.UserModel;

import java.util.List;
import java.util.Optional;

@Getter
public abstract class MainCommand implements Command {

       private List<UserModel> allUsers;

    public MainCommand(List<UserModel> allUsers) {
        this.allUsers = allUsers;
    }
    protected Optional<UserModel> findUserByNickname(String nickname){
        return allUsers.stream().filter(s->s.getNickname().equals(nickname)).findAny();
    }
    public List<UserModel> getAllUsers() {
        return allUsers;
    }
}
