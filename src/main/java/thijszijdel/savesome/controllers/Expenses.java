package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.State;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Expenses implements Initializable, State {


    private static boolean isShowing = false;

    //Create one instance of this class
    private static Expenses instance = null;


    @FXML JFXListView expensesList;

    /**
     * Getter for the instance of this class
     *
     * @return this
     */
    public static Expenses getInstance() {
        //check if the instance already is setted
        if (instance == null) {
            synchronized(Expenses.class) {
                if (instance == null) {
                    instance = new Expenses();
                }
            }
        }
        return instance;
    }


    /**
     * Initialize method for the home controller class
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;


        isShowing = true;


        MainApp.openView("/FXML/Input.fxml");


        initializeExpensesList();
    }

    private void initializeExpensesList() {



        for (int i = 0; i < 100; i++) {
            Label lbl = new Label("dummy data "+i);
//            ListCell cell = new ListCell();
//            cell.autosize();
//            cell.

//            try {
//                lbl.setGraphic(new ImageView(new Image(new FileInputStream("/img/Icon.png"))));
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
//            }

            expensesList.getItems().add(lbl);
        }

        expensesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        expensesList.setExpanded(Boolean.TRUE);
    }

    /**
     * Check if the home view is showing
     *
     * @return state
     */
    @Override
    public boolean isShowing(){
        return isShowing;
    }




}
