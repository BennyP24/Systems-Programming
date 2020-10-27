package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.Command;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.Commands.baseCommand;
import bgu.spl181.net.impl.USTP.Movie;
import bgu.spl181.net.impl.USTP.SharedData;

import java.io.IOException;
import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class infoCommand extends RequestCommand {
    private Movie movie;

    public infoCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
    }

    public void execute() {
        if (!islogin) {
            errorCommand("failed");
        }
        else if (args.size() == 0) {
            ackCommand(unitemovielist(data.getAvaMovies().getMovies()));
        } else if (!data.findMovie(args.get(0),data.getAvaMovies().getMovies())) {
            errorCommand("failed");
        } else {
            String movieName = args.get(0);
            movie = data.getMovie(movieName);
            String copiesLeft = data.returnAsString(movie.getAvailableAmount());
            String price = data.returnAsString(movie.getPrice());
            ackCommand((" " + "\"" + movieName + "\"" + " " +copiesLeft +" "+ price + unitCountry(movie.getBannedCountries())));
        }

    }


    public void ackCommand(String s) {
        try {
            connections.send(connectionId, "ACK info" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void errorCommand(String s) {
        try {
            connections.send(connectionId, "ERROR request info failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String unitemovielist(List<Movie> movieList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Movie x : movieList) {
            stringBuilder.append(" " +"\"" + x.getName() + "\"");
        }
        String finalString = stringBuilder.toString();
        return finalString;
    }

    public String unitCountry(List<String> countryList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String x : countryList) {
            stringBuilder.append(" " + "\"" + x + "\"");
        }
        String finalString = stringBuilder.toString();
        return finalString;
    }
}

