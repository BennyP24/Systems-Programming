package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.Commands.baseCommand;
import bgu.spl181.net.impl.USTP.SharedData;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class balanceAddCommand extends RequestCommand {
    private long amount;

    public balanceAddCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
        this.amount = Long.parseLong(args.get(1));
    }

    public void execute() {
        if(!islogin ){
            errorCommand();
        }else {
          long balance = data.getLogUser(connectionId).getBalance();
            balance += amount;
            String s = data.returnAsString(balance);
            data.updateBalance(amount, connectionId);
            ackCommand(s);
        }
    }

    public void ackCommand(String s){
        try {
            connections.send(connectionId, "ACK balance " + s + " added " + args.get(1));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void errorCommand(){
        try {
            connections.send(connectionId, "ERROR request balance failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
