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

    @FXML JFXDatePicker date;

    String mainCategoryValue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setStyling();
        setCategories();
        setBalances();

        setShowing(true);
    }

    /**
     * Setup the balances
     */
    private void setBalances() {
        balance.getItems().addAll(MainApp.balanceConnection().getBalanceComboBoxList());

//        Balance preferredBalance = MainApp.config.getPreferredBalance();
//        if (preferredBalance != null)
//            System.out.println(preferredBalance.getName());
//        //balance.setValue(balances.balanceComboBoxItem(preferredBalance));
        balance.setValue(MainApp.balanceConnection().getBalanceComboBoxList().get(0));

    }

    /**
     * Setup the main category combo box
     */
    private void setCategories() {
        mainCategory.getItems().addAll(MainApp.getCategoryConnection().getMainCategoryNameList());

        setCategoryListener();
    }


    /**
     * Setup the category change listners
     */
    private void setCategoryListener() {
        mainCategory.valueProperty().addListener((ChangeListener<String>) (object, valBefore, valAfter) -> {
//            System.out.println("object: "+object);
//            System.out.println("valBefore: "+valBefore);
//            System.out.println("valAfter: "+valAfter);
            mainCategoryValue = mainCategory.getValue().toString();

            subCategory.getItems().clear();
            subCategory.getItems().addAll(MainApp.getCategoryConnection().getSubCatNameList(mainCategoryValue));
        });
    }

    private void setStyling() {
        background.setStyle("-fx-background-color: "+ MainApp.config.getBackground());

        mainCategory.setStyle("-fx-text-fill : #e4e4e4");
        mainCategory.setStyle("-fx-fill: #e4e4e4");
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
        // TODO: repeat object

    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setShowing(boolean state){
        this.isShowing = state;
    }

    public void insertExpense(){

        // TODO: MOVE INSERT, IMPROVE IT & IMPLEMENT ALL METHODS AND FIELDS

        try {
            MainApp.getConnection().executeUpdateQuery("INSERT INTO Expense " +
                    "(`name`, `description`, `amount`, `subcategoryFk`, `date`, `time`, `balanceFk`, `alreadyPaid`) VALUES " +
                    "('" + name.getText() + "', '" + description.getText() + "', '" + Integer.parseInt(amount.getText()) + "', ' "+sub1.getText()+ " ', '"+date1.getText()+"', '       11:00', '1', '0');");
        } catch (Exception e){
            MainApp.log(e);
        }

    }
}
