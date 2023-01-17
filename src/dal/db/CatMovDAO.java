package dal.db;

import be.Category;
import be.Movie;
import dal.DatabaseConnector;
import util.MovieCollectionException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatMovDAO {
    private DatabaseConnector databaseConnector;

    public CatMovDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public List<Movie> filter(int id) throws SQLException {
        List<Movie> filterMovies = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM CatMovie JOIN Movie ON CatMovie.MovieId = Movie.id WHERE CategoryId = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("rating"),
                        rs.getString("fileLink"),
                        rs.getInt("release"),
                        rs.getDate("lastView").toLocalDate());
                filterMovies.add(movie);
            }
        }
        return filterMovies;
    }

    /**
     * Lets you add a category to a movie.
     * @param movie The movie that you want to add a category to.
     * @param category The category that you want for the movie.
     * @throws SQLException
     */
    public void addCategory(Movie movie, Category category) throws MovieCollectionException {
        String sql = "INSERT INTO CatMovie(CategoryId, MovieId) VALUES(?,?);";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                 ps.setInt(1, category.getId());
                 ps.setInt(2, movie.getId());
                 ps.executeUpdate();

        }
        catch(SQLException e){
            throw new MovieCollectionException("Database error trying to add a genre: "+ category + " to the movie: " + movie,e);
        }
    }
}
