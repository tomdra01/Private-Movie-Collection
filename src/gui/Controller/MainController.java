package gui.Controller;

import be.Movie;
import gui.Model.MainModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private TableColumn<Movie, String> titleColumn;
    @FXML
    private TableColumn<Movie, String> ratingColumn;
    @FXML
    private TableColumn<Movie, String> releaseColumn;
    @FXML
    private TableColumn<Movie, String> lastViewColumn;

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button exit;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button openButton;
    @FXML
    private AnchorPane addMoviePane;

    @FXML
    private TextField titleField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField sourceField;
    @FXML
    private Spinner<Integer> yearSpinner = new Spinner<>(1900, 2100, 2020);
    private LocalDate releaseDate;
    @FXML
    private ChoiceBox<String> categoryField;
    @FXML
    private Label categoryText;
    private File selectedMovie;
    private MainModel model;

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
                selectedMovie = fileChooser.showOpenDialog(stage);
                sourceField.setText(String.valueOf(selectedMovie));});
        }

        //Save button
        if (saveButton!=null) {
            saveButton.setOnAction(event -> {
                System.out.println(titleField.getText() + "\n" + ratingField.getText() + "\n" + sourceField.getText() + "\n" + yearSpinner.getValue() + "\n" + categoryField.getValue() + "\n");
                try {
                    model.createMovie(titleField.getText(), Double.parseDouble(ratingField.getText()), null, yearSpinner.getValue(), 0);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) addMoviePane.getScene().getWindow();
                stage.close();
            });
        }

        //Cancel button
        if (cancelButton!=null) { cancelButton.setOnAction(event -> {
                Stage stage = (Stage) addMoviePane.getScene().getWindow();
                stage.close();});}
    }

    public void getCategory(ActionEvent event){
        String selectedCategory = categoryField.getValue();
        System.out.println("category set: "+selectedCategory);
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
            lastViewColumn.setCellValueFactory((new PropertyValueFactory<>("lastView")));
        }

        if(this.exit!=null) exit.setOnAction(event -> Platform.exit());
    }
}
