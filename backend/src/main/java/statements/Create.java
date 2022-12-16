package statements;

public class Create {
    
    public static final String USER = 
                "CREATE TABLE user ( " +
                    "user_id VARCHAR(50), " +
                    "user_name VARCHAR(50), " +
                    "password VARCHAR(50), " +
                    "latitude VARCHAR(50), " +
                    "longitude VARCHAR(50), " +
                    "PRIMARY KEY (user_id) " +
                    ")";
}
