package bgu.spl181.net.impl.USTP;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("password")
    @Expose
    private String password ;
    @SerializedName("country")
    @Expose
    private String country ;
    @SerializedName("movies")
    @Expose
    private List<Movie> moviesListList = null;
    @SerializedName("balance")
    @Expose
    private long balance;

    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.type = "normal";
        moviesListList = new ArrayList<>();
        this.balance = 0 ;
    }
    public User(String username,String password,String country){
        this.username = username;
        this.password = password;
        this.type = "normal";
        this.country = country;
        moviesListList = new ArrayList<>();
        this.balance = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getMoviesList() {
        return moviesListList;
    }

    public void setMoviesList(List<Movie> moviesListList) {
        this.moviesListList = moviesListList;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}


