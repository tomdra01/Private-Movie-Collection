package dal.dao;

import be.Category;
import dal.db.DatabaseConnector;
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
     * @param category
     * @throws SQLException
     */
    public Category createCategory(Category category) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO Category (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, category.getName());
            pst.execute();

            if (pst.getGeneratedKeys().next()) {
                int id = pst.getGeneratedKeys().getInt(1);
                category.setId(id);
                return category;
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