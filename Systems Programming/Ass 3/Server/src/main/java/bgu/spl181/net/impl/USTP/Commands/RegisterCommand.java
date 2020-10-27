package bgu.spl181.net.impl.USTP.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.impl.USTP.User;

import java.io.IOException;
import java.util.List;


public class RegisterCommand extends baseCommand {
    protected String username;
    protected String password;


    public RegisterCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
    }

    public void execute() {
        if(args.size() < 2
                || data.containUserByName(args.get(0))
                || islogin){
          errorCommand();
        }
        else{
            data.addUser(args.get(0),args.get(1));
            ackCommand();
        }
    }

    public void errorCommand(){
        try {
            connections.send(connectionId, "ERROR registration failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ackCommand(){
        try {
            connections.send(connectionId, "ACK registration succeeded");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
