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

    public Movie createMovie (String name, double rating, String fileLink, int release, LocalDate lastView) throws SQLException {
        return movieDAO.createMovie(name, rating, fileLink, release, lastView);
    }

    public void deleteMovie(int id) {
        movieDAO.deleteMovie(id);
    }

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public Category createCategory (String name) throws SQLException {
        return categoryDAO.createCategory(name);
    }

    public List<Movie> searchMovie(String query) throws SQLException {
        List<Movie> movies = movieDAO.getAllMovies();
        List<Movie> filtered = new ArrayList<>();

        for (Movie m : movies){

            if((""+m.getName().toLowerCase()).contains(query.toLowerCase())){
            filtered.add(m);
            }
        }
        return filtered;
    }

    public void deleteCategory(int id) {
        categoryDAO.deleteCategory(id);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    public void addGenre(Movie movie, Category category) throws SQLException {
        catMovDAO.addGenre(movie, category);
    }
}
