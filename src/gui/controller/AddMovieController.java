package gui.controller;

import be.Category;
import be.Movie;
import gui.model.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

public class AddMovieController implements Initializable {
    @FXML
    private Button saveButton, cancelButton, openButton, moreButton;
    @FXML
    private CheckComboBox<Category> categoryField;
    @FXML
    private AnchorPane addMoviePane;
    @FXML
    private TextField titleField, ratingField, sourceField;
    @FXML
    private Spinner<Integer> yearSpinner = new Spinner<>(1900, 2100, 2020);
    @FXML
    private javafx.scene.control.Label categoryText;
    private TableView<Movie> movieTable;
    private MainModel model;
    private Movie movie;
    private double defaultRating = 0;
    private MainController mainController;

    public void setModel(MainModel model) {
        this.model = model;
    }

    public void setMovieTable(TableView<Movie> table) {
        this.movieTable = table;
    }

    public void buttonHandler() {
        if (moreButton!=null) { moreButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/view/addCategory.fxml"));
            try {
                Scene scene;
                scene = new Scene(loader.load());

                AddCategoryController addCategoryController = loader.getController();
                addCategoryController.setModel(model);

                Stage stageCategory = new Stage();
                stageCategory.setTitle("Categories");
                stageCategory.setScene(scene);
                stageCategory.setResizable(false);
                stageCategory.show();
            }
            catch (IOException e) {
                throw new RuntimeException(e);}});
        }

        //Open button
        if (openButton!=null) { openButton.setOnAction(event -> {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("File types", "*.mp4", "*.MPEG-4");
            fileChooser.getExtensionFilters().addAll(extensionFilter);
            File selectedMovie = fileChooser.showOpenDialog(stage);
            sourceField.setText(String.valueOf(selectedMovie));});
        }

        //Save button
        if (saveButton!=null) { saveButton.setOnAction(event -> {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            Alert a = new Alert(Alert.AlertType.NONE); // New alert

            Alert a2 = new Alert(Alert.AlertType.NONE);

            if (titleField.getText().trim().isEmpty() || ratingField.getText().trim().isEmpty() || sourceField.getText().trim().isEmpty()) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Please fill in all fields");
                a.show();
            }
            else if(Integer.parseInt(ratingField.getText()) > 10 || Integer.parseInt(ratingField.getText()) < 0) {
                a2.setAlertType(Alert.AlertType.ERROR);
                a2.setContentText("The rating should be 0 - 10");
                a2.show();
            }
            else {
                try {
                    model.createMovie(titleField.getText(), defaultRating, sourceField.getText(), yearSpinner.getValue(), getCurrentDate());

                    List<Category> selectedItems = categoryField.getCheckModel().getCheckedItems();
                    getMovie().setId(getMovie().getId()+1);

                    for (Category item : selectedItems) {
                        model.addGenre(getMovie(), new Category(item.getId(), item.getName()));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                stage.close();
            }});
        }

        //Cancel button
        if (cancelButton!=null) { cancelButton.setOnAction(event -> {
            Stage stage = (Stage) addMoviePane.getScene().getWindow();
            stage.close();});}
    }


    /**
     * Getting the current date
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public void assignCategory() {
    }

    public void getSelectedCategories() {
        categoryField.setOnMouseEntered(event -> {
            categoryText.setText(categoryField.getCheckModel().getCheckedItems().toString());
            if (categoryField.getCheckModel().isEmpty()) {
                categoryText.setText("None");
            }
        });
        ;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MainModel();
        buttonHandler();

        try {
            model.fetchAllCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        categoryField.getItems().addAll(model.getCategories());
        categoryField.setTitle("Select");
        getSelectedCategories();

        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2100, 2023, 1));
    }

    public void getLastMovie(Movie getMovie) {
        this.movie = getMovie;
    }

    public Movie getMovie() {
        return movie;
    }
}
