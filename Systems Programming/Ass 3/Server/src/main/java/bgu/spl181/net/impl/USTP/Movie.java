package bgu.spl181.net.impl.USTP;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Long price;
    @SerializedName("bannedCountries")
    @Expose
    private ArrayList<String> bannedCountries;
    @SerializedName("availableAmount")
    @Expose
    private Long availableAmount;
    @SerializedName("totalAmount")
    @Expose
    private Long totalAmount;

    public Movie(long id ,String name , long totalAmount,long price, ArrayList<String> bannedCountries){
        this.id = id;
        this.name = name;
        this.totalAmount = totalAmount;
        this.availableAmount =totalAmount;
        this.price = price;
        this.bannedCountries = bannedCountries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ArrayList<String> getBannedCountries() {
        return bannedCountries;
    }

    public void setBannedCountries(ArrayList<String> bannedCountries) {
        this.bannedCountries = bannedCountries;
    }

    public long getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(long availableAmount) {
        this.availableAmount = availableAmount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  boolean containCountry(String bannedcountry){
        for (String s: getBannedCountries()){
            if(s.equals(bannedcountry))
                return true;
        }
        return false;
    }
}
