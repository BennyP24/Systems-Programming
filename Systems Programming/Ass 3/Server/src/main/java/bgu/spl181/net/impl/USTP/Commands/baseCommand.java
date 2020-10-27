package bgu.spl181.net.impl.USTP.Commands;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.impl.USTP.SharedData;

import java.util.List;

public abstract class baseCommand implements Command {
    protected List<String> args;
    protected SharedData data;
    protected Integer connectionId;
    protected Connections connections;
    protected boolean islogin;

    public baseCommand(List<String> args, SharedData data, Connections connections, Integer connectionId){
        this.args = args;
        this.data = data;
        this.connections = connections;
        this.connectionId = connectionId;
        islogin = data.isUserLogin(connectionId);
    }

    public abstract void execute();

}
