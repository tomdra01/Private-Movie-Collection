package gui.controller;

import be.Movie;
import gui.model.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
    private Text title, text;
    @FXML
    private Button removeButton, skipButton;
    private MainModel model;

    public void buttonHandler() {
        //Skip button
        skipButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/view/mainView.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                MainController mainController = loader.getController();
                mainController.setModel(model);

                Stage stage = new Stage();
                stage.setTitle("▶ PMC");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) warningPane.getScene().getWindow();
            stage.close();
            }
        );

        //Remove button
        removeButton.setOnAction(event -> {
            if (badMoviesTable.getSelectionModel().isEmpty()){
                System.out.println("no selected item");
            }
            else {
                Movie selectedItem = badMoviesTable.getSelectionModel().getSelectedItem();
                try {
                    model.deleteMovie(selectedItem);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                badMoviesTable.getItems().remove(selectedItem);
            }
        });
    }


    public void setTable() {
        movieColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
        lastSeenColumn.setCellValueFactory((new PropertyValueFactory<>("lastView")));
    }

    public void initialize(URL location, ResourceBundle resources) {
        model = new MainModel();
        buttonHandler();

        try {model.fetchBadMovies();} catch (SQLException e) {throw new RuntimeException(e);}

        badMoviesTable.setItems(model.getBadMovies());
        setTable();
    }
}
