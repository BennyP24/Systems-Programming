package bgu.spl181.net.impl.USTP;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.Commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServerTextBasedProtocol<T> implements BidiMessagingProtocol<T> {
        protected   int connectionId;
        protected Connections connections;
        protected SharedData data;
        protected AtomicBoolean shouldTerminate;

    public UserServerTextBasedProtocol(SharedData data) {
        this.data = data;
    }

    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connections = connections;
        this.shouldTerminate = new AtomicBoolean(false);
    }

    public void process(T message) {
        Command com = whichCommand(message);
        if(com != null)
        com.execute();

    }

    public boolean shouldTerminate() {
        return shouldTerminate.get();
    }

    public Command whichCommand(T messge){
        List<String> args = splitString(messge);
        String s = args.get(0);
        if(s.equals("LOGIN"))
            return  new loginCommand(getWithout(args), data, connections, connectionId);
        else if(s.equals("REGISTER"))
            return  new RegisterCommand(getWithout(args), data, connections, connectionId);
        else if(s.equals("SIGNOUT"))
            return  new signOutCommand(args, data, connections, connectionId);
        else if(s.equals("REQUEST"))
            return  new RequestCommand(getWithout(args), data, connections, connectionId);
            return null;
    }

    public List<String> splitString(T message){
        String msg = (String) message;
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("(-?\\d+)|(\\w+)|\"(.*?)\"").matcher(msg);
        while (m.find())
            list.add(m.group(0).replace("\"","" ));
        return list;
    }

    protected List<String> getWithout(List<String> list){
        list.remove(0);
        List<String> args = list;
        return  args;
    }
    public void Terminate(){
        shouldTerminate.set(true);
    }

}

