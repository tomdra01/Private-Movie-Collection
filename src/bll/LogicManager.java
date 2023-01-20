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

    /**
     * Gets all movies from the database.
     * @return Returns a list of all movies.
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    /**
     * Gets all bad movies from the database.
     * @return Returns a list of badMovies.
     * @throws SQLException
     */
    public List<Movie> getBadMovies() throws SQLException {
        return movieDAO.getBadMovies();
    }

    /**
     * Method for creating a new movie.
     * @param movie
     * @return The new movie that you just added to the database.
     * @throws MovieCollectionException
     */
    public Movie createMovie (Movie movie) throws MovieCollectionException {
        return movieDAO.createMovie(movie);
    }

    /**
     * Method for deleting a movie.
     * @param movie The movie to delete.
     * @throws SQLException
     */
    public void deleteMovie(Movie movie) throws SQLException {
        movieDAO.deleteMovie(movie);
    }

    /**
     * Method for editing the rating of a specific movie.
     * @param movie The movie you want to edit rating for.
     * @throws SQLException
     */
    public void editRating(Movie movie) throws SQLException {
        movieDAO.editRating(movie);
    }

    /**
     * Method for updating a date of the movie.
     * @param movie The movie you want to update date for.
     * @throws SQLException
     */
    public void updateDate(Movie movie) throws SQLException {
        movieDAO.updateDate(movie);
    }

    /**
     * Gets all categories from the database.
     * @return Returns a list of all categories.
     * @throws SQLException
     */
    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }
    /**
     * Method for creating a new category.
     * @param category
     * @throws SQLException
     */
    public Category createCategory (Category category) throws SQLException {
        return categoryDAO.createCategory(category);
    }

    /**
     * Method for deleting a category.
     * @param category The category to delete.
     * @throws SQLException
     */
    public void deleteCategory(Category category) throws SQLException {
        categoryDAO.deleteCategory(category);
    }

    /**
     * Lets you add a category to a movie.
     * @param movie The movie that you want to add a category to.
     * @param category The category that you want for the movie.
     * @throws SQLException
     */
    public void addCategory(Movie movie, Category category) throws MovieCollectionException {
        catMovDAO.addCategory(movie, category);
    }

    /**
     * Method for combining search with filter active.
     * @param id The id of the category.
     * @param query The word we are searching for.
     * @throws SQLException
     */
    public List<Movie> filterSearch(int id, String query) throws SQLException {
        List<Movie> searchedList = search.searchMovie(query);
        List<Movie> filterList = filter.filter(id);
        if (filterList.isEmpty())
            return searchedList;

        //New list
        List<Movie> out = searchedList.stream().filter((movie)->
            filterList.stream().anyMatch((movie2)-> movie.getId()==movie2.getId())
        ).toList();
        return out;
    }
}
