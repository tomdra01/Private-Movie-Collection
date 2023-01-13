package dal.db;

import be.Movie;
import dal.DatabaseConnector;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private DatabaseConnector databaseConnector;

    public MovieDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public List<Movie> getAllMovies() throws SQLException {
        ArrayList<Movie> allMovies = new  ArrayList<>();
        try (Connection con = databaseConnector.getConnection())
            {
                String sql = "SELECT * FROM Movie;";
                Statement statement = con.createStatement();

                if (statement.execute(sql))
                {
                    ResultSet resultSet = statement.getResultSet();
                    while(resultSet.next())
                    {
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

    public static void main(String[] args) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> allMovies = movieDAO.getAllMovies();
        System.out.println(allMovies);
    }

    public Movie createMovie(String name, double rating, String fileLink, int release, LocalDate lastView) throws SQLException{
        try(Connection con = databaseConnector.getConnection()) {
            String psql = "INSERT INTO Movie (name, rating, fileLink, release, lastView) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(psql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,name);
            statement.setDouble(2,rating);
            statement.setString(3,fileLink);
            statement.setInt(4,release);
            statement.setDate(5, Date.valueOf(lastView));

            statement.execute();
            if (statement.getGeneratedKeys().next()){
                int id = statement.getGeneratedKeys().getInt(1);
                return new Movie(id, name, rating, fileLink, release, lastView);
            }
            throw new RuntimeException("Id not set");
        }
    }

    public void deleteMovie(int id) {
        String sql = "DELETE FROM Movie WHERE id = ?";

        try (Connection con = databaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
