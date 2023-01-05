package gui.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button exit;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;

    public void clickAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/View/AddWindow.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stageAddMovie = new Stage();
        stageAddMovie.setTitle("Add Movie");
        stageAddMovie.setScene(scene);
        stageAddMovie.setResizable(false);
        stageAddMovie.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(this.exit!=null) exit.setOnAction(event -> Platform.exit());
    }
}
