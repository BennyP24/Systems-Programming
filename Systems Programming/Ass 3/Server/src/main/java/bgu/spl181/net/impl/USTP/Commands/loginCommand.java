package bgu.spl181.net.impl.USTP.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.srv.ConnectionsImpl;

import java.io.IOException;
import java.util.List;

public class loginCommand extends baseCommand {
    private String username;
    private String password;
    public loginCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
        this.username = args.get(0);
        this.password = args.get(1);
    }

    public void execute() {
        if(islogin || !data.equalpass(username,password) ||
               !data.containUserByName(username)) {
            errorCommand();
        }
        else {
            data.updateLogin(connectionId,username);
            ackCommand();
        }

    }

    public void errorCommand(){
        try {
            connections.send(connectionId, "ERROR login failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ackCommand(){
        try {
            connections.send(connectionId, "ACK login succeeded");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
