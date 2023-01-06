package gui.Model;

import be.Category;
import be.Movie;
import bll.LogicManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainModel {
    LogicManager bll = new LogicManager();
    private List<Movie> movies = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Category> getCategories() {
        return categories;
    }
    public void fetchAllMovies() throws SQLException {
        movies.addAll(bll.getAllMovies());
    }

    public void fetchAllCategories() throws SQLException {
        categories.addAll(bll.getAllCategories());
    }

    public Movie createMovie(int id, String name, double rating, String fileLink, double lastView) throws SQLException {
        Movie movie = bll.createMovie(id, name, rating, fileLink, lastView);
        movies.add(movie);
        return movie;
    }

    public Category createCategory(int id, String name) throws SQLException {
        Category category = bll.createCategory(id, name);
        categories.add(category);
        return category;
    }
}
