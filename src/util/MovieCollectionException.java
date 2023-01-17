package util;

import java.sql.SQLException;

public class MovieCollectionException extends Exception{
    public MovieCollectionException(SQLException e) {
        super(e);
    }

    public MovieCollectionException(String message, SQLException e) {
        super(message, e);
    }
}
