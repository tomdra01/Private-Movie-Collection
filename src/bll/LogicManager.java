package bll;

import be.Category;
import be.Movie;
import dal.db.CategoryDAO;
import dal.db.MovieDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public Movie createMovie (String name, double rating, String fileLink, int release, LocalDate lastView) throws SQLException {
        return movieDAO.createMovie(name, rating, fileLink, release, lastView);
    }

    public void deleteMovie(int id) {
        movieDAO.deleteMovie(id);
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
