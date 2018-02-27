package thijszijdel.savesome.connections.Expense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IConnection;
import thijszijdel.savesome.ui.displays.ExpenseDisplay;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseConnection implements IConnection {

    private final ExpensesData data  = new ExpensesData();
    private final ExpenseDisplay display = new ExpenseDisplay();

    private ArrayList<Expense> expensesList = new ArrayList<>();


    /**
     * Constructor
     */
    public ExpenseConnection(){
        try {
            expensesList = convertToExpenses(data.getExpensesResultSet());
        } catch (SQLException e){
            MainApp.log(e);
        }
    }

    /**
     * Method for converting the resultSet to a usable arrayList on initialize
     * @return ArrayList Expenses (this)
     * @throws SQLException resultSet
     */
    private ArrayList<Expense> convertToExpenses(ResultSet resultSet) throws SQLException{
        ArrayList<Expense> list = new ArrayList<>();

        while ( resultSet.next() ){
            int id = resultSet.getInt("idExpense");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");
            Date date = resultSet.getDate("date");
            String month = resultSet.getString("month");
            int subCategoryFk = resultSet.getInt("subCategoryFk");
            int balanceFk = resultSet.getInt("balanceFk");

            list.add(new Expense(id, name, description, amount, date, month, subCategoryFk , balanceFk));
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
            //TODO fix expense connection
            //TODO implement month filtering on getting expenses
            expensesList = convertToExpenses(data.getExpensesResultSet());
        } catch (SQLException e){
            MainApp.log(e);
        }
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

    /**
     * Getter for a expense based on the id
     *
     * @param key for the wanted expense
     * @return Expense
     */
    @Override
    public Expense get(int key) {
        for (Expense expense : this.expensesList)
            if (expense.getExpenseId() == key)
                return expense;
        MainApp.log(new Exception("no expense matched key"));
        return null;
    }

    /**
     * Get the expenses displays of a specific month
     *
     * @param monthKey
     * @return array of expense displays
     */
    public ArrayList<HBox> getExpenseDisplays(int monthKey) {
        ArrayList<HBox> boxes = new ArrayList<>();

        try {
            //loop trough all the expenses for the given month
            for (Expense expense : convertToExpenses(data.getExpensesResultSet(monthKey)) ) {

                //Get the almost completed expense display
                HBox box = display.getExpenseDisplay(expense);
                box.getStyleClass().add("CellPadding");

                //Setup for the indicator dot
                Circle indicator = getIndicatorCircle(expense);

    //            box.setOnMouseEntered(t -> {
    //                if (expense.isNegative())
    //                    indicator.setStyle("-fx-fill:"+Settings.getAlertColorD());
    //                else
    //                    indicator.setStyle("-fx-fill:"+Settings.getSuccesColor());
    //            });

    //            box.setOnMouseExited(t -> {
    //                indicator.setStyle("-fx-fill:"+expense.getSubCategory().getColor());
    //            });

                box.getChildren().add(indicator);
                boxes.add(box);
            }
        } catch (SQLException e) {
            MainApp.log(e);
        }
        return boxes;

    }

    /**
     * Getter for getting the expenses in display format
     * Note: no sorting/ filtering is yet implemented
     * @return styled HBoxes of expenses
     */
//    public ArrayList<HBox> getExpenseDisplays(){
//        ArrayList<HBox> boxes = new ArrayList<>();
//
//        for (Expense expense : this.getList()) {
//
//            //Get the almost completed expense display
//            HBox box = display.getExpenseDisplay(expense);
//            box.getStyleClass().add("CellPadding");
//
//            //Setup for the indicator dot
//            Circle indicator = getIndicatorCircle(expense);
//
////            box.setOnMouseEntered(t -> {
////                if (expense.isNegative())
////                    indicator.setStyle("-fx-fill:"+Settings.getAlertColorD());
////                else
////                    indicator.setStyle("-fx-fill:"+Settings.getSuccesColor());
////            });
////
////            box.setOnMouseExited(t -> {
////                indicator.setStyle("-fx-fill:"+expense.getSubCategory().getColor());
////            });
//
//            box.getChildren().add(indicator);
//            boxes.add(box);
//        }
//        return boxes;
//    }


    /**
     *
     * @param month
     * @return
     */
    public ObservableList<PieChart.Data> getExpensesSumMonth(int month) {
        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
        int index = 1;
        try {

            ResultSet results = data.getResultSetMonth(month);


            //wheter monthFk = month;

            while (results.next()){

                double amount = results.getDouble("amount");
                String name = results.getString("SubCategory.name");
                String color = results.getString("SubCategory.color");

                if (amount < 0)
                    amount = amount * -1;

                datalist.add(new PieChart.Data(name, amount));
//                chart.setStyle("CHART_COLOR_"+index+" : "+color+";");
//
//                System.out.println("-fx-CHART_COLOR_"+index+" : "+color+";");

                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datalist;
    }
}
