package bll.util;

import be.Movie;
import dal.dao.CatMovDAO;
import java.sql.SQLException;
import java.util.List;

public class Filter {
    private CatMovDAO catMovDAO = new CatMovDAO();

    /**
     * Filter function.
     * @param id Filtering movies by category id.
     * @return Returns all filtered movies.
     * @throws SQLException
     */
    public List<Movie> filter(int id) throws SQLException {
        return catMovDAO.filter(id);
    }
}