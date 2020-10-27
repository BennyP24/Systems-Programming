package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.Movie;
import bgu.spl181.net.impl.USTP.SharedData;

import java.io.IOException;
import java.util.List;

public class returnCommand extends RequestCommand {
    private String movieName;
    private Movie thisMovie;

    public returnCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
        movieName = args.get(0);
        thisMovie = data.getMovie(movieName);
    }

    public void execute() {
        if((!islogin) || (!data.findMovie(movieName,data.getAvaMovies().getMovies()) || (!data.findMovie(movieName,
                data.getLogUser(connectionId).getMoviesList())))){
            errorCommand();
        }
        else {
            data.returnMovie(connectionId,thisMovie);
            ackCommand("\"" + movieName + "\"");
            long price = data.getMovie(movieName).getPrice();
            String stringPrice = data.returnAsString(price);
            String stringCopies = data.returnAsString(data.getMovie(movieName).getAvailableAmount());
            connections.broadcast("BROADCAST movie " + "\"" + movieName + "\"" + " " + stringCopies + " " + stringPrice);
        }
    }

    public void ackCommand(String s) {
        try {
            connections.send(connectionId, "ACK return " + s + " success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void errorCommand() {
        try {
            connections.send(connectionId, "ERROR request return failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
