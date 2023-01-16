package gui.controller;

import be.Movie;
import dal.db.MovieDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class outDatedController implements Initializable {

    @FXML
    public AnchorPane pane;
    @FXML
    public Text txt0, txt1;
    @FXML
    private Button removeBtn, skipBtn,goToPMCBtn;
    @FXML
    private TableView<Movie> oldMoviesTable;
    @FXML
    private TableColumn<Movie, String> title, lastViewed;







    public void initialize(URL location, ResourceBundle resources){
        populateTableView();


    }
    private void populateTableView() {

        title.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastViewed.setCellValueFactory(new PropertyValueFactory<>("lastWatched"));

    }}
