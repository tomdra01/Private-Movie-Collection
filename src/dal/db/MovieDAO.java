package dal.db;

import be.Movie;
import dal.DatabaseConnector;

import java.sql.*;
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
                        double lastView = resultSet.getDouble("lastView");

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

    public Movie createMovie(int id, String name, double rating, String fileLink, int release, double lastView) throws SQLException{
        try(Connection con = databaseConnector.getConnection()) {
            String insert = "'" +id +"'" +"," +"'" +name +"'" +"," +"'" +rating +"'" +"," +fileLink +"," +"'" +release +"'" +"," +"'" +lastView +"'";
            String sql = "INSERT INTO Movie (id, name, rating, fileLink, release, lastView) VALUES (" + insert + ")";

            Statement statement = con.createStatement();
            statement.execute(sql,Statement.RETURN_GENERATED_KEYS);

            return new Movie(id, name, rating, fileLink, release, lastView);
        }
    }

    public void deleteMovie(int id) {
        String sql = "DELETE FROM Movie WHERE id= ?";

        try (Connection con = databaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
