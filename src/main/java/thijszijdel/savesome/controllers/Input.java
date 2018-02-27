package thijszijdel.savesome.controllers;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.State;


import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class Input implements Initializable, State {

    private boolean isShowing = false;

    @FXML Pane background;

    @FXML JFXComboBox mainCategory;
    @FXML JFXComboBox subCategory;

    @FXML JFXComboBox balance;

    @FXML JFXCheckBox paid, today, repeat;

    @FXML JFXTextField amount, name, description, sub1, date1;

    //@FXML JFXTimePicker time;
    @FXML JFXDatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO: CLEAN UP !!


        background.setStyle("-fx-background-color: "+ MainApp.config.getBackground());


        mainCategory.getItems().addAll(MainApp.getCategoryConnection().getMainCategoryNameList());

        mainCategory.setStyle("-fx-text-fill : #e4e4e4");
        mainCategory.setStyle("-fx-fill: #e4e4e4");




        setCategoryListner();

        balance.getItems().addAll(MainApp.balanceConnection().getBalanceComboBoxList());



    //  PREFERRED BALANCE

//        Balance preferredBalance = MainApp.config.getPreferredBalance();
//        if (preferredBalance != null)
//            System.out.println(preferredBalance.getName());
//        //balance.setValue(balances.balanceComboBoxItem(preferredBalance));

        MainApp.setAppMessage("Input screen is loaded.");


        //pref fered balance
        balance.setValue(MainApp.balanceConnection().getBalanceComboBoxList().get(0));

        setShowing(true);
    }


    // TODO: CAT LISTENER !!!!
    private void setCategoryListner() {
        mainCategory.valueProperty().addListener((ChangeListener<String>) (object, valBefore, valAfter) -> {
            System.out.println("object: "+object);
            System.out.println("valBefore: "+valBefore);
            System.out.println("valAfter: "+valAfter);
            subCategory.getItems().clear();
            subCategory.getItems().addAll(MainApp.getCategoryConnection().getSubCatNameList(mainCategory.getValue().toString()));
        });
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
       // Time timeValue = null;

//        if (time.getValue() != null)
//            timeValue = Time.valueOf(time.getValue());
//            System.out.println(timeValue);

    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setShowing(boolean state){
        this.isShowing = state;
    }


    /**
     * WILL BE MOVED
     */
    public void insertExpense(){




        // TODO: MOVE INSERT, IMPROVE IT & IMPLIMENT ALL METHODS AND FIELDS
        Time timeValue = null;

//        if (time.getValue() != null)
//            timeValue = Time.valueOf(time.getValue());

        try {
            MainApp.getConnection().executeUpdateQuery("INSERT INTO Expense " +
                    "(`name`, `description`, `amount`, `subcategoryFk`, `date`, `time`, `balanceFk`, `alreadyPaid`) VALUES " +
                    "('" + name.getText() + "', '" + description.getText() + "', '" + Integer.parseInt(amount.getText()) + "', ' "+sub1.getText()+ " ', '"+date1.getText()+"', '       11:00', '1', '0');");
        } catch (Exception e){
            MainApp.log(e);
        }

    }
}
