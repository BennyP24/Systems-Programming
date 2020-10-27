package bgu.spl181.net.impl.USTP.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.MovieRentalService.balanceAddCommand;
import bgu.spl181.net.impl.MovieRentalService.balanceInfoCommand;
import bgu.spl181.net.impl.USTP.SharedData;


import java.io.IOException;
import java.util.List;

public class RequestCommand extends baseCommand {
    public RequestCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
    }

    public void execute() {
        if(!islogin)
            errorCommand("");
    }

    public void errorCommand(String comm){
        try {
            connections.send(connectionId, "ERROR request " + comm + " failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ackCommand(String s){
        try {
            connections.send(connectionId, "ACK " + s + " success") ;
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
