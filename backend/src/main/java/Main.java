import java.io.IOException;
import java.sql.SQLException;


import database.DBConnection;
import database.Database;
import entity.User;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
        System.out.println("Preparing backend server...");
        // get database
        DBConnection.getInstance(false); // program runs as main (not as a test)
        try {
            Database.resetDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Get a fully-configured connection to the database, or exit immediately
        java.sql.Connection c;
        try {
            c = DBConnection.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return;
        }

        User test = new User("1234", "Yiwen Wu", "testpass", "testla", "testlo");
        // Database.insertUser(test);
        // Database.removeUser("1234");
        // Database.updateUpass("1234", "yang");

        // User result = Database.getUser("1234");
        // System.out.println(result.toString());
    }

    
}
