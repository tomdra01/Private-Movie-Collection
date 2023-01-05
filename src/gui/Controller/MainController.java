package gui.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button exit;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnAction(event -> Platform.exit());
    }
}
