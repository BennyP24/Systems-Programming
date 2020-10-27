package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.Commands.baseCommand;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.impl.rci.Command;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class balanceInfoCommand extends RequestCommand {
    long userBalance;

    public balanceInfoCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
    }

    public void execute() {
        if(!islogin){
            errorCommand();
        }else {
            userBalance = data.getLogUser(connectionId).getBalance();
            String s = data.returnAsString(userBalance);
            ackCommand(s);
        }
    }

    public void ackCommand(String s){
        try {
            connections.send(connectionId, "ACK balance " + s);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void errorCommand() {
        try {
            connections.send(connectionId, "ERROR request balance failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
