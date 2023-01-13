package dal.db;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DatabaseConnector databaseConnector;

    public CategoryDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public List<Category> getAllCategories() throws SQLException {
        ArrayList<Category> allCategories = new  ArrayList<>();
        try (Connection connection = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Category;";
            Statement statement = connection.createStatement();

            if (statement.execute(sql))
            {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    Category category = new Category(id, name);
                    allCategories.add(category);
                }
            }
        }
        return allCategories;
    }

    public static void main(String[] args) throws SQLException {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> allCategories = categoryDAO.getAllCategories();
        System.out.println(allCategories);
    }

    public Category createCategory(String name) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            String psql = "INSERT INTO Category (name) VALUES (?)";
            PreparedStatement statement = con.prepareStatement(psql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);

            statement.execute();
            if (statement.getGeneratedKeys().next()) {
                int id = statement.getGeneratedKeys().getInt(1);
                return new Category(id, name);
            }
        }
        throw new RuntimeException("Id not set");
    }

    /*
    public void deleteCategory(int id) {
        String sql = "DELETE FROM Category WHERE id = ?";

        try (Connection con = databaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

     */

    public void deleteCategory(int id) throws SQLException{
        try (Connection con = databaseConnector.getConnection()) {

            PreparedStatement st1 = con.prepareStatement("DELETE FROM CatMovie WHERE CategoryId = ?;");
            st1.setInt(1, id);

            PreparedStatement st2 = con.prepareStatement("DELETE FROM Category WHERE id = ?;");
            st2.setInt(1, id);

            st1.executeUpdate();
            st2.executeUpdate();
        }
    }
}
