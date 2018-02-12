package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import thijszijdel.savesome.MainApp;


import java.net.URL;

import java.util.ResourceBundle;

public class Expenses implements Initializable {

    private static boolean isShowing = false;

    //Create one instance of this class
    private static Expenses instance = null;

    @FXML Label monthYear;
    @FXML Label label;
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

        MainApp.getInstance().openView("/FXML/Input.fxml",  MainApp.getInputStage() );

        initializeExpensesList();
    }

    /**
     * Setup for expenses list
     */
    private void initializeExpensesList() {
        expensesList.getItems().addAll(MainApp.getExpenseConnection().getExpenseDisplays());

        expensesList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<HBox>) (ov, old_val, new_val) -> {
                    label.setText(new_val.toString());
                });

        expensesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        expensesList.setExpanded(Boolean.TRUE);

        expensesList.getStyleClass().add("noBlueBorders");
        HBox.setHgrow(expensesList, Priority.ALWAYS);
    }


    public void prevMonth(ActionEvent actionEvent) {
    }

    public void nextMonth(ActionEvent actionEvent) {
    }
}
