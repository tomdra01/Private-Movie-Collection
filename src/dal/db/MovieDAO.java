package dal.db;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;

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
    public Movie createMovie(String name, double rating, String fileLink, int release, LocalDate lastView) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            String psql = "INSERT INTO Movie (name, rating, fileLink, release, lastView) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(psql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setDouble(2, rating);
            statement.setString(3, fileLink);
            statement.setInt(4, release);
            statement.setDate(5, Date.valueOf(lastView));

            statement.execute();
            if (statement.getGeneratedKeys().next()) {
                int id = statement.getGeneratedKeys().getInt(1);
                return new Movie(id, name, rating, fileLink, release, lastView);
            }
            throw new RuntimeException("Id not set");
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
