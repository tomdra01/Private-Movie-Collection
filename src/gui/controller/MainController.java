package gui.controller;

import be.Category;
import be.Movie;
import gui.model.MainModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
import java.time.LocalDate;
import java.util.List;
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
    private Button watchButton, ratingButton, addButton, removeButton, filterButton, exit;
    private MainModel model;

    /**
     * Setting the model.
     */
    public void setModel(MainModel model) {
        this.model = model;

        try {model.fetchAllMovies(); model.fetchAllCategories();} catch (SQLException e) {throw new RuntimeException(e);}
        movieTable.setItems(model.getMovies());
        setTable();

        filterBox.setTitle("Filter");
        filterBox.getItems().addAll(model.getCategories());

        model.getCategories().addListener((ListChangeListener<? super Category>) obs->{
            filterBox.getItems().clear();
            filterBox.getItems().addAll(model.getCategories());
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchAndFilter();
            }
        });

        if(this.exit!=null) exit.setOnAction(event -> Platform.exit()); //Exit application
    }

    /**
     * Handles all buttons in the current window.
     */
    public void buttonHandler() {
        //Watch button
        if (watchButton!=null) watchButton.setOnAction(event -> {
            if (movieTable.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movie selected");

                alert.setTitle("Message");
                alert.setHeaderText("Something went wrong");
                alert.show();
            }
            else {
                try {
                    Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();

                    selectedItem.setLastView(LocalDate.now());
                    model.updateDate(selectedItem);

                    File movieFile = new File(selectedItem.getFileLink());
                    Desktop.getDesktop().open(movieFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Rating button
        if (ratingButton!=null) ratingButton.setOnAction(event -> {
            if (movieTable.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movie selected");

                alert.setTitle("Message");
                alert.setHeaderText("Something went wrong");
                alert.show();
            }
            else {
                Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/addRating.fxml"));
                try {
                    Scene scene = new Scene(loader.load());

                    AddRatingController addRatingController = loader.getController();
                    addRatingController.setSelectedMovie(selectedItem);
                    addRatingController.setModel(model);

                    Stage stage = new Stage();

                    stage.setTitle(selectedItem.getName());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Add button
        if (addButton!=null) { addButton.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/addMovie.fxml"));
                try {
                    Scene scene = new Scene(loader.load());

                    AddMovieController addMovieController = loader.getController();
                    addMovieController.setModel(model);

                    Stage stage = new Stage();

                    stage.setTitle("Add");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);}});
        }

        //Remove button
        if (removeButton!=null) removeButton.setOnAction(event -> {
            if (movieTable.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No movie selected");

                alert.setTitle("Message");
                alert.setHeaderText("Something went wrong");
                alert.show();
            }
            else {
                Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();
                try {
                    model.deleteMovie(selectedItem);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                movieTable.getItems().remove(selectedItem);
            }
        });

        //Filter button
        if (filterButton!=null) filterButton.setOnAction(event -> {
            if (filterBox.getCheckModel().isEmpty()) {
                try {
                    model.fetchAllMovies();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                movieTable.setItems(model.getMovies());
            }
            else {
                searchAndFilter();
            }
        });
    }

    /**
     * Setting the table.
     */
    private void setTable() {
        titleColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
        ratingColumn.setCellValueFactory((new PropertyValueFactory<>("rating")));
        releaseColumn.setCellValueFactory(new  PropertyValueFactory<>("release"));
        lastViewColumn.setCellValueFactory((new PropertyValueFactory<>("lastView")));
    }

    private void searchAndFilter() {
        if (filterBox.getCheckModel().getCheckedItems().isEmpty()) {
            try {
                model.searchFilter(-1, searchField.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
        List<Category> selectedItems = filterBox.getCheckModel().getCheckedItems();
            for (Category item : selectedItems) {
                try {
                    model.searchFilter(item.getId(), searchField.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Initialize method for MovieController.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonHandler();
    }
}