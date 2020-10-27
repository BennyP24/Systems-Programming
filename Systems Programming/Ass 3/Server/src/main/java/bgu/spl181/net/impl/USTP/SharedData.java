package bgu.spl181.net.impl.USTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedData {
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReadWriteJson readWriteJson;
    private HashMap<Integer, String> logInUsers;
    private Users regUsers;
    private Movies avaMovies;

    public SharedData() {
        readWriteJson = new ReadWriteJson();
        logInUsers = new HashMap<>();
        regUsers = readWriteJson.getUsers();
        avaMovies = readWriteJson.getMovies();
    }

    public boolean isUserLogin(int connectionid){
        return(getLogInUsers().containsKey(connectionid));
    }

    public HashMap<Integer, String> getLogInUsers() {
        return logInUsers;
    }

    public ReadWriteJson getReadWriteJson() {
        return readWriteJson;
    }

    public Users getRegUsers() {
        return regUsers;
    }

    public void addUser(String username, String password) {
        try{
            readWriteLock.writeLock().lock();
            if (containUserByName(username)) {
                return;
            } else {
                regUsers.getUsers().add(new User(username, password));
                readWriteJson.setUsers(regUsers);
                writeJson();
            }
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void addUser(String username, String password,String Country) {
        try{
            readWriteLock.writeLock().lock();
            if (containUserByName(username)) {
                return;
            } else {
                regUsers.getUsers().add(new User(username, password, Country));
                readWriteJson.setUsers(regUsers);
                writeJson();
            }
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void updateLogin(Integer connectionId, String userName){
        logInUsers.putIfAbsent(connectionId, userName);
    }

    public boolean checkAdmin(String userName){
        if(getRegUser(userName).getType().equals("admin")){
            return true;
        }else{
            return false;
        }
    }

    public Movies getAvaMovies() {
        return avaMovies;
    }

    public boolean containUserByName(String userName){
        for (User x: getRegUsers().getUsers()) {
            if( x.getUsername().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public boolean equalpass(String username,String password){
        for (User x: getRegUsers().getUsers()) {
            if( x.getPassword().equals(password) && x.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public User getRegUser(String userName){
        for (User x: getRegUsers().getUsers()) {
            if(x.getUsername().equals(userName)){
                return x;
            }
        }
        return null;
    }

    public User getLogUser(int connectionId){
        return getRegUser(getLogInUsers().get(connectionId));
    }

    public void writeJson(){
        try {
            getReadWriteJson().writeJsonFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean findMovie(String movieName,List<Movie> list) {
        for (Movie x : list) {
            if (x.getName().equals(movieName))
                return true;
        }
        return false;
    }

    public Movie getMovie(String movieName) {
        for (Movie x : getAvaMovies().getMovies()) {
            if (x.getName().equals(movieName))
                return x;
        }
        return null;
    }

    public long getMaxid(){
        long maxid = 0;
        for (Movie x: getAvaMovies().getMovies()){
            if(x.getId() > maxid)
                maxid = x.getId();
        }
        return maxid;
    }

    public void addMovie(long id ,String name , long totalAmount,long price, ArrayList<String> bannedCountries) {
        try{
            readWriteLock.writeLock().lock();
            if (findMovie(name, avaMovies.getMovies()))
                return;
            else {
                getAvaMovies().getMovies().add(new Movie(id, name, totalAmount, price, bannedCountries));
                readWriteJson.setMovies(avaMovies);
                writeJson();
            }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String returnAsString(Object change) {
        return Objects.toString(change, null);
    }

    public boolean checkDeleteMovie(String movieName,List<User> list){
        for (User x : list){
            if(findMovie(movieName,x.getMoviesList()))
                return true;
        }
        return false;
    }

    public void updateBalance(long amount, int connectionId){
        readWriteLock.writeLock().lock();
        getLogUser(connectionId).setBalance(getLogUser(connectionId).getBalance() + amount);
        readWriteJson.setUsers(regUsers);
        writeJson();
        readWriteLock.writeLock().unlock();
    }

    public void setPrice(String movieName, long newPrice){
        readWriteLock.writeLock().lock();
        getMovie(movieName).setPrice(newPrice);
        readWriteJson.setMovies(avaMovies);
        writeJson();
        readWriteLock.writeLock().unlock();
    }

    public void removeMovie(Movie movie) {
        String movieName = movie.getName();
        try {
            readWriteLock.writeLock().lock();
            if (findMovie(movieName, getAvaMovies().getMovies())) {
                return;
            }
            else {
                getAvaMovies().removeMovie(movie);
                readWriteJson.setMovies(avaMovies);
                writeJson();
            }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public void rentMovie(User user, Movie movie){
        try{
            readWriteLock.writeLock().lock();
            if(findMovie(movie.getName(), user.getMoviesList())){
                return;
            }
            else {
                user.getMoviesList().add(movie);
                user.setBalance(user.getBalance() - movie.getPrice());
                movie.setAvailableAmount(movie.getAvailableAmount() - 1);
                readWriteJson.setMovies(avaMovies);
                readWriteJson.setUsers(regUsers);
                writeJson();
            }
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void returnMovie(int connectionId, Movie thisMovie){
        try{
            readWriteLock.writeLock().lock();
            if(!findMovie(thisMovie.getName(), getLogUser(connectionId).getMoviesList())){
                return;
            }else {
                getLogUser(connectionId).getMoviesList().remove(thisMovie);
                thisMovie.setAvailableAmount(thisMovie.getAvailableAmount() + 1);
                readWriteJson.setUsers(regUsers);
                readWriteJson.setMovies(avaMovies);
                writeJson();
            }
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
