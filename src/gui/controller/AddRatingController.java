package gui.controller;

import be.Movie;
import gui.model.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddRatingController implements Initializable {
    @FXML
    private AnchorPane ratingPane;
    @FXML
    private Button okButton;
    @FXML
    private Rating stars;
    @FXML
    private Spinner<Double> ratingSpinner = new Spinner<>(0, 10, 5);
    private Movie selectedMovie;
    private MainModel model;

    /**
     * Setting the model.
     */
    public void setModel(MainModel model) {
        this.model = model; //Model

        stars.setDisable(true);

        ratingSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, selectedMovie.getRating(), 0.1));
    }

    /**
     * Setter for movie.
     * @param selectedItem
     */
    public void setSelectedMovie(Movie selectedItem) {
        this.selectedMovie = selectedItem;
    }

    /**
     * Handles all buttons in the current window.
     */
    public void buttonHandler() {
        //Ok button
        if (okButton!=null) okButton.setOnAction(event -> {
                selectedMovie.setRating(ratingSpinner.getValue());
                try {
                    model.editRating(selectedMovie); //Edit the rating of a movie in the database
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) ratingPane.getScene().getWindow();
                stage.close();
        });
    }

    /**
     * Initialize method for the AddRatingController.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonHandler();
    }
}