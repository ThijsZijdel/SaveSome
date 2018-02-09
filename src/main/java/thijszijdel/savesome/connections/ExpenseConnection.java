package thijszijdel.savesome.connections;

import javafx.scene.layout.HBox;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.data.ExpensesData;
import thijszijdel.savesome.models.Expense;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class ExpenseConnection implements Connection {

    private final ExpensesData data = new ExpensesData();

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

    private ArrayList<Expense> convertToExpenses() throws SQLException{
        ArrayList<Expense> list = new ArrayList<>();

        ResultSet resultSet = data.getExpensesResultSet();

        while ( resultSet.next() ){
            String id = "1";
            //resultSet.getString("idCategory");
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

    public ArrayList<Expense> getExpensesList() {
        return expensesList;
    }

    @Override
    public void refreshConnection() {
        data.refreshData();

        try {
            expensesList = convertToExpenses();
        } catch (SQLException e){
            MainApp.log(e);
        }
    }

    public ArrayList<HBox> getExpenseBoxes() {


        //create the boxen
        //createHBoxExpenseDisplay(Expense);
        return null;
    }




            //with connection to categories
    //createHBoxExpenseDisplay
}
