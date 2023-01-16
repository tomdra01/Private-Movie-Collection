package dal.db;

import be.Category;
import be.Movie;
import dal.DatabaseConnector;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatMovDAO {
    private DatabaseConnector databaseConnector;

    public CatMovDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLException {
        CatMovDAO catMovDAO = new CatMovDAO();
        catMovDAO.filter(1018);
    }

    public List<Movie> filter(int catMovId) throws SQLException {
        List<Movie> filterMovies = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM CatMovie JOIN Movie ON CatMovie.MovieId = Movie.id WHERE CategoryId = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, catMovId);
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

    /*
    public List<Movie> getAllCatmovies(int catMovId) throws SQLException {
        List<Movie> newMovieList = new ArrayList<>();
        try (Connection con = connectionPool.checkOut()) {
            String query = "SELECT * FROM CatMovie INNER JOIN Movie ON CatMovie.MovieId = Movie.MovieId WHERE CatMovie.CategoryId = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, catMovId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie(rs.getInt("CatMovId"), rs.getString("Name"), rs.getInt("Year"), rs.getString("Filelink"), rs.getInt("Duration"), rs.getString("Rating"), rs.getString("LastView"));
                movie.setCatMovId(rs.getInt("CatMovId"));
                newMovieList.add(movie);
            }
        }
        return newMovieList;
    }
     */

    public void addGenre(Movie movie, Category category) throws SQLException {
        String sql = "INSERT INTO CatMovie(CategoryId, MovieId) VALUES(?,?);";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(1, category.getId());
                 ps.setInt(2, movie.getId());
                 ps.executeUpdate();
        }
    }
}
