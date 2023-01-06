package dal.db;

import be.Movie;
import dal.DatabaseConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try (Connection connection = databaseConnector.getConnection())
            {
                String sql = "SELECT * FROM Movie;";
                Statement statement = connection.createStatement();

                if (statement.execute(sql))
                {
                    ResultSet resultSet = statement.getResultSet();
                    while(resultSet.next())
                    {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        double rating = resultSet.getDouble("rating");
                        String fileLink = resultSet.getString("fileLink");
                        double lastView = resultSet.getDouble("lastView");

                        Movie movie = new Movie(id, name, rating, fileLink, lastView);
                        allMovies.add(movie);
                    }
                }
            }
            return allMovies;
        }

    public static void main(String[] args) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.createMovie(20,"Spider man", 8.6, null, 10.5);
        List<Movie> allMovies = movieDAO.getAllMovies();
        System.out.println(allMovies);
    }

    public Movie createMovie(int id, String name, double rating, String fileLink, double lastView) throws SQLException{
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" +id + "'" + "," + "'" +name + "'" + "," + "'" +rating + "'" + "," +fileLink + "," + "'" +lastView + "'";
            String sql = "INSERT INTO Movie (id, name, rating, fileLink, lastView) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            return new Movie(id, name, rating, fileLink, lastView);
        }
    }
}
