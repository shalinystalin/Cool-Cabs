package Maps;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vivek
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javaQuery.importClass.javaQueryBundle;
import javaQuery.j2ee.GeoLocation;

public class MyLocator {
    
    private String lat = null;
    private String lng = null;
    private String country = null;
    private String city = null;
    private String state = null;
    
    
    public MyLocator() {
        
        GeoLocation geo = javaQueryBundle.createGeoLocation();
        geo.MAPTargetByIP(getPublicIp(), "Localhost");
        this.lat = GeoLocation.Latitude;
        this.lng = GeoLocation.Longitude;
        this.city = GeoLocation.City;
        this.state = GeoLocation.State;
        this.country = GeoLocation.Country;
        
    }
    
    private static String getPublicIp(){
        URL amazonServiceURL = null;
        BufferedReader publicIpReader = null;
        String publicIpString = null;
        try{
                amazonServiceURL = new URL("http://checkip.amazonaws.com");
                publicIpReader = new BufferedReader(new InputStreamReader(amazonServiceURL.openStream()));
                publicIpString = publicIpReader.readLine();
                return publicIpString;
        }
        catch(IOException ioe){

        }
        return null;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}

