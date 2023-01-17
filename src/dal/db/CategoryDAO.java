package dal.db;

import be.Category;
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

    /**
     * Gets all categories from the database.
     * @return Returns a list of all categories.
     * @throws SQLException
     */
    public List<Category> getAllCategories() throws SQLException {
        List<Category> allCategories = new  ArrayList<>();
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

    /**
     * Method for creating a new category.
     * @param name The name of the category.
     * @throws SQLException
     */
    public Category createCategory(String name) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement pstCategory = con.prepareStatement("INSERT INTO Category (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            pstCategory.setString(1, name);
            pstCategory.execute();

            if (pstCategory.getGeneratedKeys().next()) {
                int id = pstCategory.getGeneratedKeys().getInt(1);
                return new Category(id, name);
            }
        }
        throw new RuntimeException("Id not set");
    }

    /**
     * Method for deleting a category.
     * @param category The category to delete.
     * @throws SQLException
     */
    public void deleteCategory(Category category) throws SQLException{
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement pstCatMovie = con.prepareStatement("DELETE FROM CatMovie WHERE CategoryId = ?;");
            pstCatMovie.setInt(1, category.getId());
            pstCatMovie.executeUpdate();

            PreparedStatement pstCategory = con.prepareStatement("DELETE FROM Category WHERE id = ?;");
            pstCategory.setInt(1, category.getId());
            pstCategory.executeUpdate();
        }
    }
}
