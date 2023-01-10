package gui.Model;

import be.Category;
import be.Movie;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MainModel {
    LogicManager bll = new LogicManager();
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public void fetchAllMovies() throws SQLException {
        movies.addAll(bll.getAllMovies());
    }

    public void fetchAllCategories() throws SQLException {
        categories.addAll(bll.getAllCategories());
    }

    public void createMovie(String name, double rating, String fileLink, int release, double lastView) throws SQLException {
        Movie movie = bll.createMovie(name, rating, fileLink, release, lastView);
        movies.add(movie);
    }

    public void deleteMovie(int id) {
        bll.deleteMovie(id);
    }

    public Category createCategory(int id, String name) throws SQLException {
        Category category = bll.createCategory(id, name);
        categories.add(category);
        return category;
    }
}
