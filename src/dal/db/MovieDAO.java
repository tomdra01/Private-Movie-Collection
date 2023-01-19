package dal.db;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;
import util.MovieCollectionException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private DatabaseConnector databaseConnector;

    public MovieDAO() {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * Gets all movies from the database.
     * @return Returns a list of all movies.
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> allMovies = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Movie;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double rating = resultSet.getDouble("rating");
                    String fileLink = resultSet.getString("fileLink");
                    int release = resultSet.getInt("release");
                    LocalDate lastView = resultSet.getDate("lastView").toLocalDate();

                    Movie movie = new Movie(id, name, rating, fileLink, release, lastView);
                    allMovies.add(movie);
                }
            }
        }
        return allMovies;
    }


    public List<Movie> getBadMovies() throws SQLException {
        List<Movie> badMovies = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Movie WHERE DATEADD(year, -2, GETDATE()) > lastView AND rating < 6";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double rating = resultSet.getDouble("rating");
                    String fileLink = resultSet.getString("fileLink");
                    int release = resultSet.getInt("release");
                    LocalDate lastView = resultSet.getDate("lastView").toLocalDate();

                    Movie movie = new Movie(id, name, rating, fileLink, release, lastView);
                    badMovies.add(movie);
                }
            }
        }
        return badMovies;
    }

    /**
     * Method for creating a new movie.
     * @param name The name of the movie.
     * @param rating The rating of the movie.
     * @param fileLink The path to the file of the movie.
     * @param release The release year of the movie.
     * @param lastView The date when the movie was last seen.
     * @return The new movie that you just added to the database.
     * @throws SQLException
     */
    public Movie createMovie(Movie movie) throws MovieCollectionException {
        try (Connection con = databaseConnector.getConnection()) {
            String psql = "INSERT INTO Movie (name, rating, fileLink, release, lastView) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(psql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movie.getName());
            statement.setDouble(2, movie.getRating());
            statement.setString(3, movie.getFileLink());
            statement.setInt(4, movie.getRelease());
            statement.setDate(5, Date.valueOf(movie.getLastView()));

            statement.execute();
            if (statement.getGeneratedKeys().next()) {
                int id = statement.getGeneratedKeys().getInt(1);
                movie.setId(id);
                return movie;
            }
            throw new RuntimeException("Id not set");
        }
        catch(SQLException e){
            throw new MovieCollectionException("Database error trying to create a movie",e);
        }
    }

    /**
     * Method for deleting a movie.
     * @param movie The movie to delete.
     * @throws SQLException
     */
    public void deleteMovie(Movie movie) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement pstCatMovie = con.prepareStatement("DELETE FROM CatMovie WHERE MovieId = ?;");
            pstCatMovie.setInt(1, movie.getId());
            pstCatMovie.executeUpdate();

            PreparedStatement pstMovie = con.prepareStatement("DELETE FROM Movie WHERE id = ?;");
            pstMovie.setInt(1, movie.getId());
            pstMovie.executeUpdate();
        }
    }

    /**
     * Method for editing the rating of a specific movie.
     * @param movie The movie you want to edit rating for.
     * @throws SQLException
     */
    public void editRating(Movie movie) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "UPDATE Movie " + "SET rating=? " + "WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setDouble(1, movie.getRating());
            statement.setInt(2, movie.getId());
            statement.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for updating a date of the movie.
     * @param movie The movie you want to update date for.
     * @throws SQLException
     */
    public void updateDate(Movie movie) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement pst = con.prepareStatement("UPDATE Movie " + "SET lastView=? " + "WHERE id=?");
            pst.setDate(1, Date.valueOf(movie.getLastView()));
            pst.setInt(2, movie.getId());
            pst.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }
}
