package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Budget.Budget;
import thijszijdel.savesome.connections.Expense.ExpenseConnection;


import java.net.URL;

import java.util.ResourceBundle;

public class Expenses implements Initializable {

    private static boolean isShowing = false;

    //Create one instance of this class
    private static Expenses instance = null;

    @FXML Label monthYear;
    @FXML Label label;
    @FXML Label spend, spend_budget, description;
    @FXML JFXListView expensesList;
    @FXML JFXProgressBar indicatorBar;

    private ExpenseConnection expenses = MainApp.getExpenseConnection();

    private int monthFk = 1;

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

    private void initializeBudget(){
        Budget budget = MainApp.getBudgetConnection().getMainBudget();
        double left = budget.getAmountLeft();
        double start = budget.getAmountStart();

        spend_budget.setText("$"+Double.toString(left)+" / $"+Double.toString(start));
        spend.setText("$"+Double.toString(left));

        monthYear.setText(budget.getDisplayName());
        description.setText(budget.getDescription());

        double perc =   ((left - start) / (double)start);
        indicatorBar.setProgress((1+perc));
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

        setupExpensesDisplays();
        initializeBudget();
    }

    /**
     * Setup for expenses list
     */
    private void setupExpensesDisplays() {

        setExpensesData();

        expensesList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<HBox>) (ov, old_val, new_val) -> {
                    if (new_val.toString().trim().length()>0)
                        label.setText(new_val.toString());
                        MainApp.setAppMessage("Expense selected, value: "+new_val.toString());
                });

        expensesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        expensesList.setExpanded(Boolean.TRUE);

       // expensesList.getStyleClass().add("noBlueBorders");
        HBox.setHgrow(expensesList, Priority.ALWAYS);
    }

    private void setExpensesData() {
        expensesList.getItems().clear();

        expensesList.getItems().addAll(expenses.getExpenseDisplays(monthFk));
    }


    public void prevMonth(ActionEvent actionEvent) {
        monthFk--;
        setExpensesData();
    }

    public void nextMonth(ActionEvent actionEvent) {
        monthFk++;
        setExpensesData();
    }
}
