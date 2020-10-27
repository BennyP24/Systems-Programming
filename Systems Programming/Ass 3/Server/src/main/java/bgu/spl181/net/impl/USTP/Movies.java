package bgu.spl181.net.impl.USTP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movies {

    @SerializedName("movies")
    @Expose
    private ArrayList<Movie> movies = null;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setUsers(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie x) {
        movies.add(x);
    }

    public void removeMovie(Movie x) {
        movies.remove(x);
    }


}


