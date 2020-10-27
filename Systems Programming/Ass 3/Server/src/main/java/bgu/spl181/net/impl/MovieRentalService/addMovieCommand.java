package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.SharedData;

import java.util.ArrayList;
import java.util.List;

public class addMovieCommand extends RequestCommand {
    private String movieName;
    private long amount;
    private long price;

    public addMovieCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
        this.movieName = args.get(0);
        this.amount = Long.parseLong(args.get(1));
        this.price = Long.parseLong(args.get(2));
    }

    @Override
    public void execute() {
        if(!islogin || !data.checkAdmin(data.getLogUser(connectionId).getUsername())
                || data.findMovie(movieName,data.getAvaMovies().getMovies())
                || price <= 0 || amount <= 0) {
            errorCommand("addmovie");
        }
        else {
            ArrayList<String> bannedcounterys = new ArrayList<>();
            for (int i = 3; i <args.size() ; i++) {
                bannedcounterys.add(args.get(i));
            }
            data.addMovie(data.getMaxid()+1,movieName,amount,price,bannedcounterys);
            ackCommand("addmovie" + " " + "\"" + movieName + "\"");
            connections.broadcast("BROADCAST " + "movie " + "\"" + movieName + "\"" + " " + data.returnAsString(amount) + " " + data.returnAsString(price));
        }
    }

    @Override
    public void errorCommand(String comm) {
        super.errorCommand(comm);
    }

    @Override
    public void ackCommand(String s) {
        super.ackCommand(s);
    }
}