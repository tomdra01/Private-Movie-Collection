package gui.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button exit;
    @FXML
    private AnchorPane addMoviePane;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField ratingField;
    @FXML
    private DatePicker releaseField;
    @FXML
    private ChoiceBox<String> categoryField;
    @FXML
    private Label categoryText;

    private String[] categories = {"Horror", "Action", "Romantic", "Comedy", "Thriller", "Drama", "Detective", "Adventure"};

    public void clickAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/View/AddWindow.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stageAddMovie = new Stage();
        stageAddMovie.setTitle("Add Movie");
        stageAddMovie.setScene(scene);
        stageAddMovie.setResizable(false);
        stageAddMovie.show();
    }

    public void clickCancel()  {
        Stage stage = (Stage) addMoviePane.getScene().getWindow();
        stage.close();
    }

    public void clickSave()  {
        LocalDate dateOfRelease = releaseField.getValue();
        System.out.println(titleField.getText()+ratingField.getText()+dateOfRelease+categoryField.getValue());
        Stage stage = (Stage) addMoviePane.getScene().getWindow();
        stage.close();
    }

    public void getCategory(ActionEvent event){
        String selectedCategory = categoryField.getValue();
        System.out.println("category set: "+selectedCategory);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.exit!=null) exit.setOnAction(event -> Platform.exit());
    }
}
