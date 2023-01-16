package gui.model;

import be.Category;
import be.Movie;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public void createMovie(String name, double rating, String fileLink, int release, LocalDate lastView) throws SQLException {
        Movie movie = bll.createMovie(name, rating, fileLink, release, lastView);
        movies.add(movie);
    }

    public void editRating(Movie movie) throws SQLException {
        bll.editRating(movie);
    }

    public void deleteMovie(int id) throws SQLException {
        bll.deleteMovie(id);
    }

    public void updateDate(Movie movie) throws SQLException {
        bll.updateDate(movie);
    }

    public void createCategory(String name) throws SQLException {
        Category category = bll.createCategory(name);
        categories.add(category);
    }
    public void search(String query) throws SQLException {
        movies.clear();
        movies.addAll(bll.searchMovie(query));
    }

    public void deleteCategory(int id) throws SQLException {
        bll.deleteCategory(id);
    }

    public void addGenre(Movie movie, Category category) throws SQLException {
        bll.addGenre(movie, category);
    }
}
