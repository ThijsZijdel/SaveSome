package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Input implements Initializable {

    @FXML Pane background;

    @FXML JFXComboBox mainCategory;
    @FXML JFXComboBox subCategory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("input");

        background.setStyle("-fx-background-color: "+ MainApp.config.getBackground());


        ArrayList<String> mainCategoryList = new ArrayList<>();
        mainCategoryList.add("Household");
        mainCategoryList.add("Abanonment");
        mainCategoryList.add("Food");
//
//        HashMap<String , Integer> mainCategories = new HashMap<>();
//        mainCategories.put("Food", 1);
//        mainCategories.put("Abo", 1);
//        mainCategories.put("Telecom", 1);
//        mainCategories.put("Study", 1);

        mainCategory.getItems().addAll(mainCategoryList);
        mainCategory.setStyle("-fx-text-fill : #e4e4e4");
        mainCategory.setStyle("-fx-fill: #e4e4e4");

        ArrayList<String> subCategoryList = new ArrayList<>();
        subCategoryList.add("Spotify");
        subCategoryList.add("Netflix");
        subCategoryList.add("Tele2");

        subCategory.getItems().addAll(subCategoryList);
    }
}
