package gui.controller;

import be.Category;
import be.Movie;
import gui.model.MainModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.CheckComboBox;
import util.MovieCollectionException;

public class AddMovieController implements Initializable {
    @FXML
    private AnchorPane addMoviePane;
    @FXML
    private Button saveButton, cancelButton, openButton, moreButton;
    @FXML
    private TextField titleField, sourceField;
    @FXML
    private CheckComboBox<Category> categoryField;
    @FXML
    private Label categoryText;
    @FXML
    private Spinner<Integer> yearSpinner = new Spinner<>(1900, 2100, 2020);
    private double defaultRating = 0;
    private MainModel model;

    /**
     * Setting the model.
     */
    public void setModel(MainModel model) {
        this.model = model; //Model

        try {model.fetchAllCategories();} catch (SQLException e) {throw new RuntimeException(e);}

        categoryField.setTitle("Select");
        categoryField.getItems().addAll(model.getCategories());
        getSelectedCategories();

        model.getCategories().addListener((ListChangeListener<? super Category>) obs->{
            categoryField.getItems().clear();
            categoryField.getItems().addAll(model.getCategories());
            getSelectedCategories();
        });

        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2100, 2023, 1));
    }

    /**
     * Handles all buttons in the current window.
     */
    public void buttonHandler() {
        //Open button
        if (openButton!=null) openButton.setOnAction(event -> {
            Stage stage = new Stage();

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("filter","*.mp4", "*.MPEG-4");
            fileChooser.getExtensionFilters().addAll(extensionFilter);

            File selectedMovie = fileChooser.showOpenDialog(stage);
            sourceField.setText(String.valueOf(selectedMovie));
        });


        //More button
        if (moreButton!=null) moreButton.setOnAction(event -> {
            categoryText.setText("None");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/view/addCategory.fxml"));
            try {
                Scene scene = new Scene(loader.load());

                AddCategoryController addCategoryController = loader.getController();
                addCategoryController.setModel(model);

                Stage stageCategory = new Stage();

                stageCategory.setTitle("More");
                stageCategory.setScene(scene);
                stageCategory.setResizable(false);
                stageCategory.show();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Save button
        if (saveButton!=null) saveButton.setOnAction(event -> {
            Stage stage = (Stage) saveButton.getScene().getWindow();

            if (titleField.getText().trim().isEmpty() || sourceField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No movie selected"); //Alert

                alert.setContentText("Please fill in all fields");
                alert.show();
            }
            else {
                try {
                    Movie m = new Movie(titleField.getText(), defaultRating, sourceField.getText(), yearSpinner.getValue(), getCurrentDate());
                    model.createMovie(m); //Creating movie in the database

                    List<Category> selectedItems = categoryField.getCheckModel().getCheckedItems();

                    for (Category item : selectedItems) { //Loop
                        model.addCategory(m, new Category(item.getId(), item.getName())); //Adding categories to the movie
                    }
                } catch (MovieCollectionException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());

                    e.printStackTrace();
                    alert.show();
                }
                stage.close();
            }
        });

        //Cancel button
        if (cancelButton!=null) cancelButton.setOnAction(event -> {
            Stage stage = (Stage) addMoviePane.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * Getting the current date.
     * @return LocalDate.now
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public void getSelectedCategories() {
        categoryField.setOnMouseEntered(event -> {
            categoryText.setText(categoryField.getCheckModel().getCheckedItems().toString());
            if (categoryField.getCheckModel().isEmpty()) categoryText.setText("None");
            });
    }

    /**
     * Initialize method for AddMovieController
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonHandler();
    }
}