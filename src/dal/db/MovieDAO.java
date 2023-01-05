package dal.db;

import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

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

                        Movie movie = new Movie(id,name,rating);
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

}
