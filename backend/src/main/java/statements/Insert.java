package statements;

public class Insert {
    public static final String User =
            "INSERT INTO user (user_id, user_name, password, latitude, longitude) " +
                    "VALUES (?, ?, ?, ?, ?)";
}
