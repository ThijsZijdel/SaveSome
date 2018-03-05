package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;
import thijszijdel.savesome.interfaces.IRefresh;
import thijszijdel.savesome.ui.tables.MainCategoryTreeTable;
import thijszijdel.savesome.ui.tables.SubCategoryTreeTable;


import java.net.URL;

import java.util.ResourceBundle;

public class Settings implements Initializable, IRefresh {

    @FXML Pane mainCatPane,subCatPane;

    //FXML config for category section
    @FXML JFXTextField search;
    @FXML Label hitsMain, hitsSub;

    /**
     * Initialize method for the home controller class
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize all the different sections
        initializeCategorySection();
    }


    /**
     * Initialize the category settings section
     * This is for main category and sub category
     */
    private void initializeCategorySection(){
        SubCategoryTreeTable subCatTable = new SubCategoryTreeTable(search, hitsSub);
        MainCategoryTreeTable mainCatTable = new MainCategoryTreeTable(search, hitsMain);

        mainCatTable.setRelatedSubCatTable(subCatTable);

        //clear the previous set up for refresh
        mainCatPane.getChildren().clear();
        subCatPane.getChildren().clear();

        mainCatPane.getChildren().add(mainCatTable.getTable());
        subCatPane.getChildren().add(subCatTable.getTable());
    }


    @Override
    public void refresh() {
        initializeCategorySection();
    }
}
