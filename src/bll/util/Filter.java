package bll.util;

import be.Movie;
import dal.db.CatMovDAO;
import java.sql.SQLException;
import java.util.List;

public class Filter {
    private CatMovDAO catMovDAO = new CatMovDAO();

    public List<Movie> filter(int id) throws SQLException {
        return catMovDAO.filter(id);
    }
}
