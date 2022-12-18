package database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.User;
import statements.*;

/**
 * This class is used for managing the database. It
 * contains functions which will execute queries for data
 * retrieval, modification, and deletion.
 */
public class Database {

    /**
     * Private constructor of database.
     */
    private Database() {
    }

    /**
     * Deletes the database, creates a new file for
     * the database, and creates the tables on the
     * new file.
     */
    public static void resetDatabase() throws SQLException, IOException, ClassNotFoundException {
        deleteDatabase();
        DBConnection.getInstance().getConnection();
        createAllTables();
    }

    /**
     * Closes the connection to the database and
     * deletes the file storing the database.
     */
    public static void deleteDatabase() {
        DBConnection.getInstance().closeConnection();
        //boolean deleted = DBConnection.getInstance().deleteDBFile();
    }

    /**
     * This method checks if the table has been created
     * in the database.
     */
    public static void createAllTables() throws SQLException, IOException, ClassNotFoundException {
        Connection c = DBConnection.getInstance().getConnection(); // get connection
        try {
            // check if tables have been created
            PreparedStatement s = c.prepareStatement(Select.TABLE_NAME);
            s.setString(1, "table");
            s.setString(2, "user");
            ResultSet rs = s.executeQuery();
            // todo fix check. rs.next() currently always returns false
            //  due to a logical error in the Select.TABLE_NAME statement.
            //  in the meantime, a try/catch checks if the tables already exist.
            if (!rs.next()) { // create all tables if not already created
                s.close();

                // create user
                s = c.prepareStatement(Create.USER);
                s.execute();
                s.close();

                DBConnection.getInstance().commitChanges();
            }
        } catch (Exception e) {//todo replace catch with better method for checking table existence
            if (!e.getMessage().contains("table call already exists")) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Insert user into database
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void insertUser(User user) throws SQLException, ClassNotFoundException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Insert.User);
        s.setString(1, user.getID());
        s.setString(2, user.getName());
        s.setString(3, user.getPass());
        s.setString(4, user.getLatitude());
        s.setString(5, user.getLongitude());
        s.execute();
        s.close();
        DBConnection.getInstance().commitChanges();

        // File f = new File("src/main/resources/"+user.getID()+".txt");
        // f.createNewFile();
        // FileWriter w = new FileWriter(f);
        // w.write(user.getID()+"\n"+user.getName()+"\n"+user.getPass()+"\n\n"+user.getLatitude()+"\n"+user.getLongitude());
        // w.flush();
        // w.close();

        String path = "src/main/resources/"+user.getID()+".txt";

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), user);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Get a user from the database
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static User getUser(String id) throws SQLException, ClassNotFoundException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Select.User);
        s.setString(1, id);
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            String uid = rs.getString("user_id");
            String uname = rs.getString("user_name");
            String upass = rs.getString("password");
            String latitude = rs.getString("latitude");
            String longitude = rs.getString("longitude");
            s.close();
            return new User(uid, uname, upass, latitude, longitude);
        }
        throw new NoSuchElementException("No User with ID '" + id + "' could be found");
    }

    /**
     * Update username with given id
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateUname(String id, String name) throws ClassNotFoundException, SQLException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Update.User_name);
        s.setString(1, name);
        s.setString(2, id);
        s.execute();
        s.close();
        DBConnection.getInstance().commitChanges();

        String path = "src/main/resources/"+id+".txt";

        try{
            ObjectMapper mapper = new ObjectMapper();
            User temp = mapper.readValue(new File(path), User.class);
            temp.setName(name);
            mapper.writeValue(new File(path), temp);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Update user password with given id
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateUpass(String id, String pass) throws ClassNotFoundException, SQLException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Update.Password);
        s.setString(1, pass);
        s.setString(2, id);
        s.execute();
        s.close();
        DBConnection.getInstance().commitChanges();

        String path = "src/main/resources/"+id+".txt";

        try{
            ObjectMapper mapper = new ObjectMapper();
            User temp = mapper.readValue(new File(path), User.class);
            temp.setPass(pass);
            mapper.writeValue(new File(path), temp);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Update latitude with given id
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateUlatitude(String id, String latitude) throws ClassNotFoundException, SQLException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Update.latitude);
        s.setString(1, latitude);
        s.setString(2, id);
        s.execute();
        s.close();
        DBConnection.getInstance().commitChanges();

        String path = "src/main/resources/"+id+".txt";

        try{
            ObjectMapper mapper = new ObjectMapper();
            User temp = mapper.readValue(new File(path), User.class);
            temp.setLatitude(latitude);
            mapper.writeValue(new File(path), temp);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Update longitude with given id
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateUlongitude(String id, String longitude) throws ClassNotFoundException, SQLException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Update.longitude);
        s.setString(1, longitude);
        s.setString(2, id);
        s.execute();
        s.close();
        DBConnection.getInstance().commitChanges();

        String path = "src/main/resources/"+id+".txt";

        try{
            ObjectMapper mapper = new ObjectMapper();
            User temp = mapper.readValue(new File(path), User.class);
            temp.setLongtitude(longitude);
            mapper.writeValue(new File(path), temp);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * remove user with given id
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void removeUser(String id) throws ClassNotFoundException, SQLException, IOException{
        Connection c = DBConnection.getInstance().getConnection();
        PreparedStatement s = c.prepareStatement(Delete.User);
        s.setString(1, id);
        s.execute();
        s.close();
        DBConnection.getInstance().commitChanges();

        File f = new File("src/main/resources/"+id+".txt");
        boolean result = f.delete();
        if (result) {
            System.out.println("File is successfully deleted.");
        }
        else {
            System.out.println("File deletion failed.");
        }
    }
}
