package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.SharedData;

import java.util.List;

public class changePriceCommand extends RequestCommand {
    private String movieName;
    private long price;
    public changePriceCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
        this.movieName = args.get(0);
        this.price = Long.parseLong(args.get(1));
    }

    public void execute() {
        if(!islogin || !data.checkAdmin(data.getLogUser(connectionId).getUsername())
                || !data.findMovie(movieName,data.getAvaMovies().getMovies())
                || price <= 0) {
            errorCommand("changeprice");
        }
        else{
            data.setPrice(movieName, price);
            ackCommand("changeprice " + "\"" + movieName + "\"");
            connections.broadcast("BROADCAST " + "movie " + "\"" + movieName + "\"" + " " +
                    data.returnAsString(data.getMovie(movieName).getAvailableAmount()) + " "
                    + data.returnAsString(price));
        }
    }


    public void errorCommand(String comm) {
        super.errorCommand(comm);
    }


    public void ackCommand(String s) {
        super.ackCommand(s);
    }
}
