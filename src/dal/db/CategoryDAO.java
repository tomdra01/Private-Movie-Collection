package dal.db;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DatabaseConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        //categoryDAO.createCategory(11, "Action");
        List<Category> allCategories = categoryDAO.getAllCategories();
        System.out.println(allCategories);
    }

    public Category createCategory(int id, String name) throws SQLException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" +id + "'" + "," + "'" +name + "'";
            String sql = "INSERT INTO Category (id, name) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            return new Category(id, name);
        }
    }
}
