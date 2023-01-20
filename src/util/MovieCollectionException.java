package util;

import java.sql.SQLException;

public class MovieCollectionException extends Exception{

    //Constructor
    public MovieCollectionException(SQLException e) {
        super(e);
    }

    /**
     * MovieCollectionException
     * @param message Gets the message.
     * @param e Exception.
     */
    public MovieCollectionException(String message, SQLException e) {
        super(message, e);
    }
}