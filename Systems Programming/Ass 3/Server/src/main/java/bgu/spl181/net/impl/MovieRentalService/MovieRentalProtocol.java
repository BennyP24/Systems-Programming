package bgu.spl181.net.impl.MovieRentalService;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.Command;
import bgu.spl181.net.impl.USTP.SharedData;
import bgu.spl181.net.impl.USTP.UserServerTextBasedProtocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MovieRentalProtocol<T> extends UserServerTextBasedProtocol<T>{

    public MovieRentalProtocol(SharedData data) {
        super(data);
    }


    public void start(int connectionId, Connections connections) {
        super.start(connectionId, connections);
    }


    public Command whichCommand(T messge) {
        List<String> args = splitString(messge);
        if(args.size() == 0)
            return null;
        String s = args.get(0);
        if(s.equals("REGISTER")){
            return new registerMovieUser(getWithout(args), data, connections, connectionId);
        }
        if (s.equals("REQUEST")) {
            args = getWithout(args);
            if (args.get(0).equals("balance")) {
                if (args.get(1).equals("info")) {
                    return new balanceInfoCommand(getWithout(args), data, connections, connectionId);
                } else if (args.get(1).equals("add")) {
                    return new balanceAddCommand(getWithout(args), data, connections, connectionId);
                }
            }
            else if(args.get(0).equals("info")){
                args = getWithout(args);
                return new infoCommand(args, data, connections, connectionId);
            }else if(args.get(0).equals("rent")){
                args = getWithout(args);
                return new rentCommand(args, data, connections, connectionId);
            }else if(args.get(0).equals("return")){
                args = getWithout(args);
                return new returnCommand(args, data, connections, connectionId);
            }else if(args.get(0).equals("addmovie")){
                args = getWithout(args);
                return new addMovieCommand(args, data, connections, connectionId);
            }else if(args.get(0).equals("remmovie")){
                args = getWithout(args);
                return new remMovieCommand(args, data, connections, connectionId);
            }else if(args.get(0).equals("changeprice")){
                args = getWithout(args);
                return new changePriceCommand(args, data, connections, connectionId);
            }
        }
        else {
            return super.whichCommand(messge);
        }
        return null;
    }

    public List<String> splitString(T message) {
        return super.splitString(message);
    }

    protected List<String> getWithout(List<String> list) {
        return super.getWithout(list);
    }

    public void process(T message) {
        super.process(message);
    }

}
