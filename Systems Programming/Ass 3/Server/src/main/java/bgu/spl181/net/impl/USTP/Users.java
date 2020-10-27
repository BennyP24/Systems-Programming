package bgu.spl181.net.impl.USTP;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Users {

    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User x){
        users.add(x);
    }

    public void removeUser(User x){
        users.remove(x);
    }



}

