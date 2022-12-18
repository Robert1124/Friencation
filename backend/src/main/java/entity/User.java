package entity;

public class User {
    private String id;

    private String name;

    private String password;
    
    private String latitude;

    private String longitude;

    public User(){
        id = null;
        name = null;
        password = null;
        latitude = null;
        longitude = null;
    }

    public User(String id, String name, String password, String latitude, String longitude){
        this.id = id;
        this.name = name;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPass(){
        return password;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPass(String password){
        this.password = password;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public void setLongtitude(String longitude){
        this.longitude = longitude;
    }

    public String toString(){
        return "id:"+getID()+"\nname:"+getName()+"\npassword:"+getPass()+"\nla:"+getLatitude()+"\nlo:"+getLongitude();
    }
}
