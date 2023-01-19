package gui.controller;

import be.Category;
import be.Movie;
import gui.model.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCategoryController implements Initializable {
    @FXML
    private AnchorPane categoryPane;
    @FXML
    private Button closeButton, newButton, deleteButton;
    @FXML
    private ListView<Category> categoryList;
    @FXML
    private TextField nameField;
    private MainModel model;

    /**
     * Setting the model.
     */
    public void setModel(MainModel model) {
        this.model = model;
        try { model.fetchAllCategories(); } catch (SQLException e) {throw new RuntimeException(e);}
        categoryList.setItems(model.getCategories());
    }

    /**
     * Handles all the buttons in the current window.
     */
    public void buttonHandler() {
        //New button
        if (newButton!=null) { newButton.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                System.out.println("Empty field");
            } else {
                try {
                    Category c = new Category(nameField.getText());
                    model.createCategory(c);
                    nameField.clear();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }}});
        }

        //Delete button
        if (deleteButton!=null) { deleteButton.setOnAction(event -> {
            if (categoryList.getSelectionModel().isEmpty()){
                System.out.println("No selected category to delete");
            } else {
            Category selectedItem = categoryList.getSelectionModel().getSelectedItem();
            try { model.deleteCategory(selectedItem); } catch (SQLException e) { throw new RuntimeException(e);}
            }});
        }

        //Close button
        if (closeButton!=null) { closeButton.setOnAction(event -> {
            Stage stage = (Stage) categoryPane.getScene().getWindow();
            stage.close();});
        }
    }

    /**
     * Initialize method for the AddCategoryController.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonHandler();
    }
}