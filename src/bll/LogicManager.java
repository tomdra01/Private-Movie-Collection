package bll;

import be.Category;
import be.Movie;
import dal.db.CatMovDAO;
import dal.db.CategoryDAO;
import dal.db.MovieDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private CatMovDAO catMovDAO = new CatMovDAO();

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public Movie createMovie (String name, double rating, String fileLink, int release, LocalDate lastView) throws SQLException {
        return movieDAO.createMovie(name, rating, fileLink, release, lastView);
    }

    public void deleteMovie(Movie movie) throws SQLException {
        movieDAO.deleteMovie(movie);
    }

    public void editRating(Movie movie) throws SQLException {
        movieDAO.editRating(movie);
    }

    public void updateDate(Movie movie) throws SQLException {
        movieDAO.updateDate(movie);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    public Category createCategory (String name) throws SQLException {
        return categoryDAO.createCategory(name);
    }

    public void deleteCategory(Category category) throws SQLException {
        categoryDAO.deleteCategory(category);
    }

    public void addGenre(Movie movie, Category category) throws SQLException {
        catMovDAO.addGenre(movie, category);
    }
}
