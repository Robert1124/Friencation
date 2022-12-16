package statements;

public class Select {
    public static final String TABLE_NAME =
            "SELECT name FROM sqlite_master WHERE type=? AND name=?";

    public static final String User =
            "SELECT * FROM user WHERE user_id=?";
}
