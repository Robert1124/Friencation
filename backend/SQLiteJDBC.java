import java.sql.*;
import java.util.NoSuchElementException;

import src.main.java.entity.User;
import src.main.java.statements.*;

public class SQLiteJDBC {

    private Connection connect;
    private PreparedStatement stmt;
    private volatile static SQLiteJDBC sqLiteJDBC;

    private SQLiteJDBC(){
        try{
            Class.forName("org.sqlite.JDBC");
            getConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SQLiteJDBC getInstance(){
        if(sqLiteJDBC == null){
            synchronized(SQLiteJDBC.class){
                if(sqLiteJDBC == null){
                    sqLiteJDBC = new SQLiteJDBC();
                }
            }
        }

        return sqLiteJDBC;
    }

    public Connection getConnection(){
        try{
            if(connect == null){
                connect = DriverManager.getConnection("jdbc:sqlite:friencation.db");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return connect;
    }

    public void create(){
        try {
            stmt = connect.prepareStatement(Create.USER);
            stmt.execute();
            stmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(User user){
        if(user == null) throw new NullPointerException("Parameter 'user' is null");

        try{
            stmt = connect.prepareStatement(Insert.User);
            stmt.setString(1,user.getID());
            stmt.setString(2,user.getName());
            stmt.setString(3,user.getPass());
            stmt.setString(4,user.getLatitude());
            stmt.setString(5, user.getLongitude());
            stmt.execute();
            close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(User user){
        if(user == null) throw new NullPointerException("Parameter 'user' is null");

        try{
            stmt = connect.prepareStatement(Delete.User);
            stmt.setString(1,user.getID());
            stmt.execute();
            close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void update(String element, String flag){
        try{
            if(flag.equals("name")){
                stmt = connect.prepareStatement(Update.User_name);
                stmt.setString(1,element);
                stmt.execute();
                close();
            }else if(flag.equals("password")){
                stmt = connect.prepareStatement(Update.Password);
                stmt.setString(1,element);
                stmt.execute();
                close();
            }else if(flag.equals("latitude")){
                stmt = connect.prepareStatement(Update.latitude);
                stmt.setString(1,element);
                stmt.execute();
                close();
            }else if(flag.equals("longitude")){
                stmt = connect.prepareStatement(Update.longitude);
                stmt.setString(1,element);
                stmt.execute();
                close();
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public User select(User user) throws SQLException{
        if(user == null) throw new NullPointerException("Parameter 'user' is null");

        stmt = connect.prepareStatement(Select.User);
        stmt.setString(1,user.getID());
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            String user_id = rs.getString("user_id");
            String user_name = rs.getString("user_name");
            String password = rs.getString("password");
            String latitude = rs.getString("latitude");
            String longitude = rs.getString("longitude");
            stmt.close();
            return new User(user_id, user_name, password, latitude, longitude);
        }
        throw new NoSuchElementException("No user with id '"+user.getID()+"' could be found");

    }

    private void close(){
        try{
            if(stmt != null){
                stmt.close();
            }

            if(connect != null){
                connect.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    
}