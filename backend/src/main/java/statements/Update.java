package statements;

public class Update {
    public static final String User_name =
            "UPDATE user " +
                    "SET user_name=? " +
                    "WHERE user_id=?";

    public static final String Password =
            "UPDATE user " +
                    "SET password=? " +
                    "WHERE user_id=?";

    public static final String latitude =
            "UPDATE user " +
                    "SET latitude=? " +
                    "WHERE user_id=?";

    public static final String longitude =
            "UPDATE user " +
                    "SET longitude=? " +
                    "WHERE user_id=?";
}
