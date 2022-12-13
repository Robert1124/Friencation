public class User {
    private final String user_id;

    private String user_name;

    private String password;
    
    private String latitude;

    private String longitude;

    public User(String user_id, String user_name, String password, String latitude, String longitude){
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getID(){
        return user_id;
    }

    public String getName(){
        return user_name;
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

    public void setName(String user_name){
        this.user_name = user_name;
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
}
