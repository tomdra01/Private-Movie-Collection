package dal.db;

import be.Category;
import be.Movie;
import dal.DatabaseConnector;
import java.sql.*;

public class CatMovDAO {
    private DatabaseConnector databaseConnector;

    public CatMovDAO()
    {
        databaseConnector = new DatabaseConnector();
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
}
