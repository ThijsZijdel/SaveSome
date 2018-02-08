package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.JFXFillTransition;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.CategoryConnection;
import thijszijdel.savesome.connections.ExpenseConnection;
import thijszijdel.savesome.connections.Settings;
import thijszijdel.savesome.interfaces.State;
import thijszijdel.savesome.models.Expense;
import thijszijdel.savesome.models.SubCategory;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.ResourceBundle;

public class Expenses implements Initializable {


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


        MainApp.getInstance().openView("/FXML/Input.fxml",  MainApp.getInputStage() );


        initializeExpensesList();

    }

    private void initializeExpensesList() {

        ExpenseConnection expensesConnection = new ExpenseConnection();

        ArrayList<Expense> expenses = expensesConnection.getExpensesList();

        for (Expense expense : expenses) {

            Label name = new Label(expense.getName());
            Label desc = new Label(expense.getDescription());
            Label amount = new Label(expense.getDisplayAmount());



            Date date = expense.getDate();
            String result;
            SimpleDateFormat formatter;
                                                        //EEE d MMM yy
            formatter = new SimpleDateFormat("EEE, MMM d, ''yy");

            result = formatter.format(date);
            //System.out.println("Locale: " + currentLocale.toString());
            //System.out.println("Result: " + result);





            Label dateTime = new Label(result + "        " + expense.getTime().toString());

            String balanceN = "";
            if (expense.getBalance() != null)
                balanceN = expense.getBalance().getName();

            Label balance = new Label(" " + balanceN + " ");

            balance.setStyle("-fx-font-size: 10px");


            name.getStyleClass().add("bold");
            desc.getStyleClass().add("lighter");
            dateTime.getStyleClass().add("lighter");
            amount.getStyleClass().add("amountDisplay");

            if (expense.isNegative())
                amount.setTextFill(Color.web(Settings.getAlertColorD()));


//            ImageView imgView = new ImageView( new Image("/images/SaveSome.png") );
//
//            imgView.maxHeight(25);
//            imgView.setFitHeight(25);
//
//            imgView.maxWidth(25);
//            imgView.setFitWidth(25);
            String color = expense.getSubCategory().getColor();
            Circle circle = new Circle(5, 5, 5);
            circle.setFill(Color.web(color));
            circle.setStyle("-fx-stroke: darkgrey");

            VBox amountBalance = new VBox(amount, balance);
            amountBalance.getStyleClass().add("amountBalanceBox");


            VBox details = new VBox(name, desc, dateTime);


            details.getStyleClass().add("detailsBox");
            HBox box = new HBox(amountBalance, details, circle);

            box.setStyle("-fx-background-color: #dcdde1 ;");

            box.getStyleClass().add("CellPadding");


//            DropShadow e = new DropShadow();
//            e.setWidth(5);
//            e.setHeight(5);
////            e.setOffsetX(2);
////            e.setOffsetY(2);
//            e.setRadius(10);
//            box.setEffect(e);


            JFXFillTransition ts = new JFXFillTransition();

            ts.setRegion(box);

            box.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    ts.setFromValue(Color.web("#f5f6fa", 0.7));

                    ts.setToValue(Color.web("#dcdde1", 0.7));

                    if (expense.isNegative())
                        circle.setFill(Color.web(Settings.getAlertColorD()));
                    else
                        circle.setFill(Color.web(Settings.getAccentColor()));

                    ts.play();
                }
            });

            box.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    circle.setFill(Color.web(color));


                    ts.setFromValue(Color.web("#dcdde1", 0.7));
                    ts.setToValue(Color.web("#f5f6fa", 0.7));

                    ts.play();
                }
            });


            expensesList.getStyleClass().add("noBlueBorders");
            HBox.setHgrow(expensesList, Priority.ALWAYS);

            expensesList.getItems().add(box);

//            cell.setOnMousePressed((MouseEvent event) -> {
//                if (event.isPrimaryButtonDown()){
//                    MainApp.setAppMessage("hi "+event.getTarget().toString());
//                }
//            });




        }
        // expensesList.getItems().addAll(cells);

        expensesList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<HBox>) (ov, old_val, new_val) -> {
                    label.setText(new_val.toString());
                    //label.setTextFill(Color.web(new_val.toString()));
                });

        expensesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        expensesList.setExpanded(Boolean.TRUE);


        setStyleClasses();

    }
    @FXML Label label;
    private void setStyleClasses(){

    }





}
