package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.layout.Pane;
import thijszijdel.savesome.ui.tables.MainCategoryTreeTable;
import thijszijdel.savesome.ui.tables.SubCategoryTreeTable;


import java.net.URL;

import java.util.ResourceBundle;

public class Settings implements Initializable {

    @FXML Pane mainCatPane,subCatPane;


    /**
     * Initialize method for the home controller class
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeMainCategory();
    }


    //TODO :: Comment + Move + Split this way to big method
    public void initializeMainCategory(){


        SubCategoryTreeTable subCatTable = new SubCategoryTreeTable(search, hitsSub);

        MainCategoryTreeTable mainCatTable = new MainCategoryTreeTable(search, hitsMain);

        mainCatTable.setRelatedSubCatTable(subCatTable);
        mainCatPane.getChildren().add(mainCatTable.getTable());
        subCatPane.getChildren().add(subCatTable.getTable());
//        mainCatTable.setupHits(hits);
//        mainCatTable.setupSearch(search);

    }

//    public void setMainCatPane()


    @FXML JFXTextField search;
    @FXML Label hitsMain, hitsSub;
}
