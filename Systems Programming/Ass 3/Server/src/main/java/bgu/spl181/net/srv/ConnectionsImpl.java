package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.BidiMessagingProtocol;
import bgu.spl181.net.api.bidi.Connections;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionsImpl<T> implements Connections<T> {
    private HashMap<Integer, ConnectionHandler<T>> clientsIds;
    private AtomicInteger numOfusers;

    public ConnectionsImpl() {
        this.clientsIds = new HashMap<>();
        numOfusers = new AtomicInteger(1);
    }

    public boolean send(int connectionId, T msg) throws IOException {
        if(clientsIds.get(connectionId) == null) {
            return false;
        }
        ConnectionHandler mission = clientsIds.get(connectionId);
        mission.send(msg);
        return true;
    }

    public void broadcast(T msg) {
        clientsIds.forEach( (k,v)-> {
            try {
                v.send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void disconnect(int connectionId){
        clientsIds.remove(connectionId);
    }

    public void addClient(ConnectionHandler<T> newhander){
        clientsIds.putIfAbsent(numOfusers.get(), newhander);
        numOfusers.incrementAndGet();
    }

    public int getNumOfUsers() {
        return numOfusers.get();
    }
}
