package gui.controller;

import be.Category;
import be.Movie;
import gui.model.MainModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> titleColumn, ratingColumn, releaseColumn, lastViewColumn;

    @FXML
    private TextField searchField;
    @FXML
    private CheckComboBox<Category> filterBox;

    @FXML
    private Button addButton, watchButton, removeButton, filterButton, exit;
    private MainModel model;


    public void setModel(MainModel model) {
        this.model = model;
    }

    /**
     * Handles all the buttons in the Application
     */
    public void buttonHandler() {
        //Add button
        if (addButton!=null) { addButton.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/view/AddMovieWindow.fxml"));
                try {
                    Scene scene;
                    scene = new Scene(loader.load());
                    AddMovieController movieController = loader.getController();
                    movieController.setModel(model);

                    Stage stageAddMovie = new Stage();
                    stageAddMovie.setTitle("Add Movie");
                    stageAddMovie.setScene(scene);
                    stageAddMovie.setResizable(false);
                    stageAddMovie.show();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);}});
        }

        //Remove button
        if (removeButton!=null) { removeButton.setOnAction(event -> {
                int selectedMovieId = movieTable.getSelectionModel().getSelectedItem().getId();
                Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();
                model.deleteMovie(selectedMovieId);
                movieTable.getItems().remove(selectedItem);
            });
        }

        //Watch button
        if (watchButton!=null) { watchButton.setOnAction(event -> {
            try {
                Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();
                File movieFile = new File(selectedItem.getFileLink());
                Desktop.getDesktop().open(movieFile);
            } catch (IOException ex) {ex.printStackTrace();}});
        }

        //Filter button
        if (filterButton!=null) { filterButton.setOnAction(event -> {
            if (filterBox.getCheckModel().isEmpty()){
                System.out.println("No categories selected");
            }
            else {
                System.out.println(filterBox.getCheckModel().getCheckedItems().toString());}});
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MainModel();
        buttonHandler();

        try {model.fetchAllMovies(); model.fetchAllCategories();} catch (SQLException e) {throw new RuntimeException(e);}

        filterBox.getItems().addAll(model.getCategories());

        if (movieTable != null) {
            movieTable.setItems(model.getMovies());
            titleColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
            ratingColumn.setCellValueFactory((new PropertyValueFactory<>("rating")));
            releaseColumn.setCellValueFactory(new  PropertyValueFactory<>("release"));
            lastViewColumn.setCellValueFactory((new PropertyValueFactory<>("lastView")));
        }

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    model.search(newValue);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        if(this.exit!=null) exit.setOnAction(event -> Platform.exit());
    }
}
