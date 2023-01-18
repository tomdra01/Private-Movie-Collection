package gui.model;

import be.Category;
import be.Movie;
import bll.LogicManager;
import bll.util.Filter;
import bll.util.Search;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.MovieCollectionException;

import java.sql.SQLException;
import java.time.LocalDate;

public class MainModel {
    LogicManager bll = new LogicManager();
    Search utilSearch = new Search();
    Filter utilFilter = new Filter();
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
    private ObservableList<Movie> badMovies = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() {
        return movies;
    }

    public ObservableList<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    public ObservableList<Movie> getBadMovies() {
        return badMovies;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public void fetchAllMovies() throws SQLException {
        movies.clear();
        movies.addAll(bll.getAllMovies());
    }

    public void fetchBadMovies() throws SQLException {
        badMovies.clear();
        badMovies.addAll(bll.getBadMovies());
    }

    public void fetchAllCategories() throws SQLException {
        categories.clear();
        categories.addAll(bll.getAllCategories());
    }

    public Movie createMovie(String name, double rating, String fileLink, int release, LocalDate lastView) throws MovieCollectionException {
        Movie movie = bll.createMovie(name, rating, fileLink, release, lastView);
        movies.add(movie);
        return movie;
    }

    public void editRating(Movie movie) throws SQLException {
        bll.editRating(movie);
    }

    public void deleteMovie(Movie movie) throws SQLException {
        bll.deleteMovie(movie);
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
        movies.addAll(utilSearch.searchMovie(query));
    }

    public void fetchFilteredMovies(int id) throws SQLException {
        filteredMovies.clear();
        filteredMovies.addAll(utilFilter.filter(id));
    }

    public void deleteCategory(Category category) throws SQLException {
        bll.deleteCategory(category);
        categories.remove(category);
    }

    public void addCategory(Movie movie, Category category) throws MovieCollectionException {
        bll.addCategory(movie, category);
        categories.add(category);
    }
}