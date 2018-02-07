package thijszijdel.savesome.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.BalanceConnection;
import thijszijdel.savesome.connections.CategoryConnection;


import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class Input implements Initializable {

    @FXML Pane background;

    @FXML JFXComboBox mainCategory;
    @FXML JFXComboBox subCategory;

    @FXML JFXComboBox balance;

    @FXML JFXCheckBox paid, today, repeat;

    @FXML JFXTextField name, description;

    @FXML JFXTimePicker time;
    @FXML JFXDatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryConnection categories = new CategoryConnection();
        BalanceConnection balances = new BalanceConnection();



        System.out.println("input");

        background.setStyle("-fx-background-color: "+ MainApp.config.getBackground());

        time._24HourViewProperty().setValue(true);
        time.setIs24HourView(true);




        mainCategory.getItems().addAll(categories.getMainCategoryNameList());

        mainCategory.setStyle("-fx-text-fill : #e4e4e4");
        mainCategory.setStyle("-fx-fill: #e4e4e4");


        subCategory.getItems().addAll(categories.getSubCategoryNameList());



        balance.getItems().addAll(balances.getBalanceComboBoxList());



    //  PREFERRED BALANCE

//        Balance preferredBalance = MainApp.config.getPreferredBalance();
//        if (preferredBalance != null)
//            System.out.println(preferredBalance.getName());
//        //balance.setValue(balances.balanceComboBoxItem(preferredBalance));

        MainApp.setAppMessage("Input screen is loaded.");
    }

    /**
     * Event listener for the paid toggle box
     *
     * @param e event
     */
    @FXML
    public void setPaid(ActionEvent e){
        System.out.println("paid");
    }

    /**
     * Event listener for the set today toggle box
     * Will toggle able the date field and effect insert query
     *
     * @param e event
     */
    @FXML
    public void setToday(ActionEvent e){
        //set date editable
        date.setDisable(!date.isDisabled());

    }

    /**
     * Event listener for the repeat toggle box
     *
     * @param e event
     */
    @FXML
    public void setRepeat(ActionEvent e){
        System.out.println("repeat");



        //time value test
        Time timeValue = null;

        if (time.getValue() != null)
            timeValue = Time.valueOf(time.getValue());
            System.out.println(timeValue);

    }
}
