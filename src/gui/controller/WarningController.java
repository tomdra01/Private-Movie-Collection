package gui.controller;

import be.Movie;
import gui.model.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WarningController implements Initializable {
    @FXML
    private AnchorPane warningPane;
    @FXML
    private TableView<Movie> badMoviesTable;
    @FXML
    private TableColumn movieColumn, lastSeenColumn;
    @FXML
    private Button removeButton, skipButton;
    private MainModel model;

    /**
     * Handles all buttons in the current window.
     */
    public void buttonHandler() {
        //Skip button
        if (skipButton != null) skipButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/mainView.fxml"));
            try {
                Scene scene = new Scene(loader.load());

                MainController mainController = loader.getController();
                mainController.setModel(model);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) warningPane.getScene().getWindow();
            stage.close();
        });

        //Remove button
        if (removeButton!=null) removeButton.setOnAction(event -> {
            if (badMoviesTable.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movie selected"); //Alert

                alert.setTitle("Message");
                alert.setHeaderText("Something went wrong");
                alert.show();
            }
            else {
                Movie selectedItem = badMoviesTable.getSelectionModel().getSelectedItem();
                try {
                    model.deleteMovie(selectedItem); //Deletes bad movie from the database
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                badMoviesTable.getItems().remove(selectedItem);
            }
        });
    }

    /**
     * Setting the table.
     */
    public void setTable() {
        movieColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
        lastSeenColumn.setCellValueFactory((new PropertyValueFactory<>("lastView")));
    }

    /**
     * Initialize method for the WarningController.
     */
    public void initialize(URL location, ResourceBundle resources) {
        model = new MainModel(); //New model
        buttonHandler();

        try {model.fetchBadMovies();} catch (SQLException e) {throw new RuntimeException(e);}

        badMoviesTable.setItems(model.getBadMovies());
        setTable();
    }
}