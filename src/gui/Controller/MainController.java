package gui.Controller;

import be.Movie;
import gui.Model.MainModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableView<Movie> movieTable;
    @FXML
    private TableColumn<Movie, String> titleColumn, ratingColumn, releaseColumn, lastViewColumn;

    @FXML
    private Button addButton, watchButton, removeButton, exit, saveButton, cancelButton, openButton;
    @FXML
    private AnchorPane addMoviePane;

    @FXML
    private TextField titleField, ratingField, sourceField;
    @FXML
    private Spinner<Integer> yearSpinner = new Spinner<>(1900, 2100, 2020);
    @FXML
    private ChoiceBox<String> categoryField;
    @FXML
    private Label categoryText;
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
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/View/AddWindow.fxml"));
                try {
                    Scene scene;
                    scene = new Scene(loader.load());
                    MainController mainController = loader.getController();
                    mainController.setModel(model);

                    Stage stageAddMovie = new Stage();
                    stageAddMovie.setTitle("Add Movie");
                    stageAddMovie.setScene(scene);
                    stageAddMovie.setResizable(false);
                    stageAddMovie.show();
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

            if (titleField.getText().trim().isEmpty() || ratingField.getText().trim().isEmpty() || sourceField.getText().trim().isEmpty()) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Please fill in all fields");
                a.show();
            }
            else {
                try {
                    model.createMovie(titleField.getText(), Double.parseDouble(ratingField.getText()), sourceField.getText(), yearSpinner.getValue(), getCurrentDate());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                stage.close();
            }});
        }

        //Remove button
        if (removeButton!=null) { removeButton.setOnAction(event -> {
                int selectedMovieId = movieTable.getSelectionModel().getSelectedItem().getId();
                Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();
                model.deleteMovie(selectedMovieId);
                movieTable.getItems().remove(selectedItem);
            });
        }

        //Cancel button
        if (cancelButton!=null) { cancelButton.setOnAction(event -> {
                Stage stage = (Stage) addMoviePane.getScene().getWindow();
                stage.close();});}

        //Watch button
        if (watchButton!=null) { watchButton.setOnAction(event -> {
            try {
                Movie selectedItem = movieTable.getSelectionModel().getSelectedItem();
                File movieFile = new File(selectedItem.getFileLink());
                Desktop.getDesktop().open(movieFile);
            } catch (IOException ex) {
                ex.printStackTrace();}});}
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MainModel();
        buttonHandler();

        yearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2100, 2023, 1));

        try {model.fetchAllMovies();} catch (SQLException e) {throw new RuntimeException(e);}

        if (movieTable != null) {
            movieTable.setItems(model.getMovies());
            titleColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
            ratingColumn.setCellValueFactory((new PropertyValueFactory<>("rating")));
            releaseColumn.setCellValueFactory(new  PropertyValueFactory<>("release"));
            lastViewColumn.setCellValueFactory((new PropertyValueFactory<>("lastView")));
        }

        if(this.exit!=null) exit.setOnAction(event -> Platform.exit());
    }
}
