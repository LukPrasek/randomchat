package pl.lukaszprasek.randomchat.models.commands;

import pl.lukaszprasek.randomchat.models.UserModel;

import java.io.IOException;

public interface Command {
   public boolean executeCommand (UserModel sender, String ... args) throws IOException;
   String info();

}
