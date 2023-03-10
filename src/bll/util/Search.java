package bll.util;

import be.Movie;
import dal.dao.MovieDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {
    private MovieDAO movieDAO = new MovieDAO();

    /**
     * Search function.
     * @param query
     * @return Returns all searched movies.
     * @throws SQLException
     */
    public List<Movie> searchMovie(String query) throws SQLException {
        List<Movie> movies = movieDAO.getAllMovies();
        List<Movie> filtered = new ArrayList<>();

        for (Movie m : movies){
            if((""+m.getName().toLowerCase()).contains(query.toLowerCase())){
                filtered.add(m);
            }
        }
        return filtered;
    }
}