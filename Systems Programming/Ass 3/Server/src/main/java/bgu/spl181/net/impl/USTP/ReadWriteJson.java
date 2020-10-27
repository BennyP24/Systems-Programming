package bgu.spl181.net.impl.USTP;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ReadWriteJson {
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final  File homdir = new File(System.getProperty("user.dir"));
    private final  File Usersfile = new File(homdir, "/Database/Users.json");
    private final  File Moviesfile = new File(homdir, "/Database/Movies.json");
    private BufferedReader readerUsers = null;
    private BufferedReader readerMovies = null;
    private Gson gson;
    private Users users;
    private Movies movies;

    public ReadWriteJson() {
        gson  = new Gson();
        readJsonFiles();
    }

    public void readJsonFiles() {
        readWriteLock.readLock().lock();
        try (BufferedReader readerUsers = new BufferedReader(new FileReader(Usersfile));
             BufferedReader readerMovies = new BufferedReader(new FileReader(Moviesfile))
        ){
            users = gson.fromJson(readerUsers, Users.class);
            movies = gson.fromJson(readerMovies,Movies.class);
            readerUsers.close();
            readerMovies.close();
        } catch (IOException e) {
        }
    }

    public void writeJsonFiles() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        Gson gson = gsonBuilder.create();
        String usersString = gson.toJson(users);
        String movieString = gson.toJson(movies);
        FileWriter usersWrite = new FileWriter(Usersfile);
        FileWriter moviesWrite = new FileWriter(Moviesfile);
        usersWrite.write(usersString);
        moviesWrite.write(movieString);
        usersWrite.close();
        moviesWrite.close();
    }

    public Users getUsers() {
        return users;
    }

    public Movies getMovies() {
        return movies;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }
}



