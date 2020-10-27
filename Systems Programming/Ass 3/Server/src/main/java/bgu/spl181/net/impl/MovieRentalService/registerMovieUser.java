package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RegisterCommand;
import bgu.spl181.net.impl.USTP.Commands.baseCommand;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.impl.USTP.User;

import java.io.IOException;
import java.util.List;

public class registerMovieUser extends RegisterCommand {
    private String country;

    public registerMovieUser(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
    }

    @Override
    public void execute() {
        if(args.size() < 4
                || data.containUserByName(args.get(0)) || !args.get(2).equals("country")
                || islogin){
            errorCommand();
        }
        else{
            data.addUser(args.get(0), args.get(1), args.get(3));
            ackCommand();
        }
    }


    @Override
    public void errorCommand() {
        super.errorCommand();
    }

    @Override
    public void ackCommand() {
        super.ackCommand();
    }
}
