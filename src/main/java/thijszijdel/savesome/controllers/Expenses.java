package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.State;

import java.net.URL;
import java.util.ResourceBundle;

public class Expenses implements Initializable, State {


    private static boolean isShowing = false;

    //Create one instance of this class
    private static Expenses instance = null;

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
