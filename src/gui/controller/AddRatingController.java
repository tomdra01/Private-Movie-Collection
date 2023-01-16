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
    private Rating stars;
    @FXML
    private Spinner<Double> ratingSpinner = new Spinner<>(0, 10, 5);
    @FXML
    private Button okButton;

    private MainModel model;
    private Movie selectedMovie;

    public void setModel(MainModel model) {
        this.model = model;
    }

    public void setSelectedMovie(Movie selectedItem) {
        this.selectedMovie = selectedItem;
    }

    public void buttonHandler() {
        if (okButton!=null) {
            okButton.setOnAction(event -> {
                selectedMovie.setRating(ratingSpinner.getValue());
                try {
                    model.editRating(selectedMovie);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) ratingPane.getScene().getWindow();
                stage.close();
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MainModel();
        buttonHandler();

        stars.setDisable(true);
        ratingSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 5, 0.1));
    }
}
