package bll;

import be.Category;
import be.Movie;
import bll.util.Filter;
import bll.util.Search;
import dal.dao.CatMovDAO;
import dal.dao.CategoryDAO;
import dal.dao.MovieDAO;
import util.MovieCollectionException;

import java.sql.SQLException;
import java.util.*;

public class LogicManager {
    private MovieDAO movieDAO = new MovieDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private CatMovDAO catMovDAO = new CatMovDAO();
    private Filter filter = new Filter();
    private Search search = new Search();

    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public List<Movie> getBadMovies() throws SQLException {
        return movieDAO.getBadMovies();
    }

    public Movie createMovie (Movie movie) throws MovieCollectionException {
        return movieDAO.createMovie(movie);
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

    public Category createCategory (Category category) throws SQLException {
        return categoryDAO.createCategory(category);
    }

    public void deleteCategory(Category category) throws SQLException {
        categoryDAO.deleteCategory(category);
    }

    public void addCategory(Movie movie, Category category) throws MovieCollectionException {
        catMovDAO.addCategory(movie, category);
    }

    public List<Movie> filterSearch(int id, String query) throws SQLException {
        List<Movie> searchedList = search.searchMovie(query);
        List<Movie> filterList = filter.filter(id);
        if (filterList.isEmpty())
            return searchedList;

        List<Movie> out = searchedList.stream().filter((movie)->
            filterList.stream().anyMatch((movie2)-> movie.getId()==movie2.getId())
        ).toList();
        return out;

    }
}
