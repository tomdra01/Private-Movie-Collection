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
import java.util.List;

public class MainModel {
    LogicManager bll = new LogicManager(); //Logic manager

    //Private lists
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private ObservableList<Movie> badMovies = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    public ObservableList<Movie> getMovies() {
        return movies;
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

    public Movie createMovie(Movie movie) throws MovieCollectionException {
        Movie m = bll.createMovie(movie);
        movies.add(m);
        return m;
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

    public Category createCategory(Category category) throws SQLException {
        Category c = bll.createCategory(category);
        categories.add(c);
        return c;
    }

    public void searchFilter(int id, String query) throws SQLException {
        List<Movie> movieList = bll.filterSearch(id, query);
        movies.clear();
        movies.addAll(movieList);
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