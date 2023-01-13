package dal.db;

import be.Category;
import be.Movie;
import dal.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatMovDAO {
    private DatabaseConnector databaseConnector;

    public CatMovDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLException {
        CatMovDAO catMovDAO = new CatMovDAO();
    }

    public void addGenre(Movie movie, Category category) throws SQLException {
        String sql = "INSERT INTO CatMovie(CategoryId, MovieId) VALUES(?,?);";
        try (Connection con = databaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, category.getId());
            ps.setInt(2, movie.getId());
            ps.executeUpdate();
        }
    }


    /*
    public List<Movie> getAllCatmovies(int catMovId) throws SQLException {
        List<Movie> newMovieList = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()) {
            String query = "SELECT * FROM CatMovie JOIN Movie ON CatMovie.MovieId = Movie.id WHERE CatMovie.CategoryId = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, catMovId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setCatMovId(rs.getInt("CatMovId"));
                newMovieList.add(movie);
            }
        }
        return newMovieList;
    }

     */
}
