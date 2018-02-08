package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.transitions.JFXFillTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
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
            Label dateTime = new Label(expense.getDate().toString() + " " + expense.getTime().toString());

            String balanceN = "";
            if (expense.getBalance() != null)
                balanceN = expense.getBalance().getName();

            Label balance = new Label(" " + balanceN + " ");
            balance.setStyle("-fx-font-size: 10px");


            amount.setStyle(
                    "-fx-font-weight: 400;" +
                            "-fx-font-size : 20px;" +
                            "-fx-fill:  slategray;"
            );


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


            HBox box = new HBox(new VBox(amount, balance), new VBox(name, desc, dateTime), circle);

            box.setStyle("-fx-background-color: #dcdde1 ;");
            box.getStyleClass().add("CellPadding");
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


            HBox.setHgrow(expensesList, Priority.ALWAYS);

            expensesList.getItems().add(box);

//            cell.setOnMousePressed((MouseEvent event) -> {
//                if (event.isPrimaryButtonDown()){
//                    MainApp.setAppMessage("hi "+event.getTarget().toString());
//                }
//            });

        }
        // expensesList.getItems().addAll(cells);


//             expensesList.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<String>() {
//                    public void changed(ObservableValue<? extends String> ov,
//                                        String old_val, String new_val) {
//                        label.setText(new_val);
//                        label.setTextFill(Color.web(new_val));
//                    }
//              });
        expensesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        expensesList.setExpanded(Boolean.TRUE);


    }





}
