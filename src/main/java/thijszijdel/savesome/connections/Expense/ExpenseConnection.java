package thijszijdel.savesome.connections.Expense;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Income.Income;
import thijszijdel.savesome.interfaces.Connection;
import thijszijdel.savesome.connections.Settings;
import thijszijdel.savesome.ui.displays.ExpenseDisplay;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class ExpenseConnection implements Connection {

    private final ExpensesData data  = new ExpensesData();
    private final ExpenseDisplay dsp = new ExpenseDisplay();

    private ArrayList<Expense> expensesList = new ArrayList<>();


    /**
     * Constructor
     */
    public ExpenseConnection(){
        try {
            expensesList = convertToExpenses();
        } catch (SQLException e){
            MainApp.log(e);
        }
    }

    /**
     * Method for converting the resultSet to a usable arrayList on initialize
     * @return ArrayList Expenses (this)
     * @throws SQLException resultSet
     */
    private ArrayList<Expense> convertToExpenses() throws SQLException{
        ArrayList<Expense> list = new ArrayList<>();

        ResultSet resultSet = data.getExpensesResultSet();

        while ( resultSet.next() ){
            String id = "1";
           // resultSet.getString("idCategory");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");
            Date date = resultSet.getDate("date");
            Time time = resultSet.getTime("time");
            int subCategoryFk = resultSet.getInt("subCategoryFk");
            int balanceFk = resultSet.getInt("balanceFk");

            list.add(new Expense(id, name, description, amount, date, time, subCategoryFk , balanceFk));
        }

        return list;
    }

    /**
     * Getter for the expenses list
     * @return ArrayList of expenses
     */
    @Override
    public ArrayList<Expense> getList() {
        return expensesList;
    }

    /**
     * Refreshing the connection and data
     */
    @Override
    public void refreshConnection() {
        data.refreshData();
        try {
            expensesList = convertToExpenses();
        } catch (SQLException e){
            MainApp.log(e);
        }
    }


    /**
     * Getter for getting the expenses in display format
     * Note: no sorting/ filtering is yet implemented
     * @return styled HBoxes of expenses
     */
    public ArrayList<HBox> getExpenseDisplays(){
        ArrayList<HBox> boxes = new ArrayList<>();

        for (Expense expense : this.getList()) {

            //Get the almost completed expense display
            HBox box = dsp.getExpenseDisplay(expense);
            box.getStyleClass().add("CellPadding");

            //Setup for the indicator dot
            Circle indicator = getIndicatorCircle(expense);

            box.setOnMouseEntered(t -> {
                if (expense.isNegative())
                    indicator.setFill(Color.web(Settings.getAlertColorD()));
                else
                    indicator.setFill(Color.web(Settings.getSuccesColor()));
            });

            box.setOnMouseExited(t -> {
                indicator.setFill(Color.web(expense.getSubCategory().getColor()));
            });

            box.getChildren().add(indicator);
            boxes.add(box);
        }
        return boxes;
    }

    // [Optional]
    //            ImageView imgView = new ImageView( new Image("/images/SaveSome.png") );
    //
    //            imgView.maxHeight(25);
    //            imgView.setFitHeight(25);
    //
    //            imgView.maxWidth(25);
    //            imgView.setFitWidth(25);

    /**
     * Get the indicator dot (circle)
     * @param expense for the right color
     * @return Circle indicator
     */
    private Circle getIndicatorCircle(Expense expense) {
        Circle circle = new Circle(5, 5,5);
        circle.setFill(Color.web(expense.getSubCategory().getColor()));

        circle.setStyle("-fx-stroke: darkgrey");

        return circle;
    }

    @Override
    public Object get(int key) {
        for (Expense expense : this.expensesList)
            if (Integer.parseInt(expense.getExpenseId()) == key)
                return expense;
        MainApp.log(new Exception("no expense matched key"));
        return null;
    }
}
