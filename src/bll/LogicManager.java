package bll;

import be.Category;
import be.Movie;
import dal.db.CategoryDAO;
import dal.db.MovieDAO;
import java.sql.SQLException;
import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public Movie createMovie (int id, String name, double rating, String fileLink, double lastView) throws SQLException {
        return movieDAO.createMovie(id, name, rating, fileLink, lastView);
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public Category createCategory (int id, String name) throws SQLException {
        return categoryDAO.createCategory(id, name);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }
}
