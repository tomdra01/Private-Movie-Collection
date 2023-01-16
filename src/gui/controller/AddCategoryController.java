package gui.controller;

import be.Category;
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
    private ListView<Category> categoryList;
    @FXML
    private TextField nameField;
    @FXML
    private Button closeButton, newButton, deleteButton;
    @FXML
    private AnchorPane categoryPane;
    private MainModel model;
    public void setModel(MainModel model) {
        this.model = model;
    }

    public void buttonHandler() {
        //New button
        if (newButton!=null) { newButton.setOnAction(event -> {
            try {
                model.createCategory(nameField.getText());
                categoryList.getItems().clear();
                categoryList.getItems().addAll(model.getCategories());
                nameField.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }});}

        //Delete button
        if (deleteButton!=null) { deleteButton.setOnAction(event -> {
            int selectedCategory = categoryList.getSelectionModel().getSelectedItem().getId();
            Category selectedItem = categoryList.getSelectionModel().getSelectedItem();
            try {model.deleteCategory(selectedCategory);} catch (SQLException e) {throw new RuntimeException(e);}
            categoryList.getItems().remove(selectedItem);});
        }

        //Close button
        if (closeButton!=null) { closeButton.setOnAction(event -> {
            Stage stage = (Stage) categoryPane.getScene().getWindow();
            stage.close();});}
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
        categoryList.getItems().addAll(model.getCategories());
    }
}
