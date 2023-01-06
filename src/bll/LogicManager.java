package bll;

import be.Movie;
import dal.db.CategoryDAO;
import dal.db.MovieDAO;
import java.sql.SQLException;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public Movie createMovie (int id, String name, double rating, String fileLink, double lastView) throws SQLException {
        return movieDAO.createMovie(id, name, rating, fileLink, lastView);
    }
}
