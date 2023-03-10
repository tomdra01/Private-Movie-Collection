import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //start
        Parent root = FXMLLoader.load(Main.class.getResource("/gui/view/warning.fxml"));
        primaryStage.setTitle(" ");
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
