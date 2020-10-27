package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.RequestCommand;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.impl.USTP.User;

import java.util.List;

public class remMovieCommand extends RequestCommand {
    private String movieName;
    private String userName;
    private User thisUser;

    public remMovieCommand(List<String> args, SharedData data, Connections connections, Integer connectionId) {
        super(args, data, connections, connectionId);
        movieName = args.get(0);
    }

    public void execute() {
        if(!islogin || !data.checkAdmin(data.getLogUser(connectionId).getUsername())
                || !data.findMovie(movieName,data.getAvaMovies().getMovies())
                || !data.checkDeleteMovie(movieName,data.getRegUsers().getUsers()))
        {
            errorCommand("remmovie");
        }
        else{
            data.removeMovie(data.getMovie(movieName));
            String movieNewName = ("\"" + movieName + "\"");
            ackCommand("remmovie " + movieNewName);
            connections.broadcast("BROADCAST movie " + movieNewName + " removed");
        }
    }

    public void errorCommand(String comm) {
        super.errorCommand(comm);
    }

    public void ackCommand(String s) {
        super.ackCommand(s);
    }
}
