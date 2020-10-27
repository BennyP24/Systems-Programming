package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.Movie;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.impl.USTP.User;

import java.util.List;
import java.util.Objects;

public class rentCommand extends RequestCommand {
    private Movie movie;
    private User user;
    public rentCommand(List<String> args, SharedData data, Connections connections, int connectionId) {
        super(args, data, connections, connectionId);
        this.movie= data.getMovie(args.get(0));
        this.user = data.getLogUser(connectionId);
    }
    @Override
    public void execute() {
        if (!islogin || !data.findMovie(movie.getName(), data.getAvaMovies().getMovies()) ||
                movie.getAvailableAmount() == 0 ||
                movie.containCountry(user.getCountry()) ||
                data.findMovie(movie.getName(), user.getMoviesList()) || user.getBalance() < movie.getPrice()) {
            errorCommand("rent");
        } else {
            data.rentMovie(user, movie);
            String copiesLeft = Objects.toString(movie.getAvailableAmount(), null);
            String price = Objects.toString(movie.getPrice(), null);
            ackCommand("rent" + "\"" + movie.getName() + "\"");
            connections.broadcast("BROADCAST " + "\"" + movie.getName() + "\"" +  " " + copiesLeft + " " + price);
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
