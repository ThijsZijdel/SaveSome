package thijszijdel.savesome.connections;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.data.BudgetData;
import thijszijdel.savesome.models.Budget;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BudgetConnection implements Connection {

    private final BudgetData data = new BudgetData();

    private ArrayList<Budget> budgetsList = new ArrayList<>();


    /**
     * Constructor
     */
    public BudgetConnection() {
        try {
            budgetsList = convertToExpenses();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Method for converting the resultSet to a usable arrayList on initialize
     *
     * @return ArrayList Expenses (this)
     * @throws SQLException resultSet
     */
    private ArrayList<Budget> convertToExpenses() throws SQLException {
        ArrayList<Budget> list = new ArrayList<>();

        ResultSet resultSet = data.getBudgetResultSet();

        while (resultSet.next()) {
            String id = "1";
//            // resultSet.getString("idCategory");
//            String name = resultSet.getString("name");
//            String description = resultSet.getString("description");
//            double amount = resultSet.getDouble("amount");
//            Date date = resultSet.getDate("date");
//            //Time time = resultSet.getTime("time");
//            int subCategoryFk = resultSet.getInt("subCategoryFk");
//            int balanceFk = resultSet.getInt("balanceFk");

            //list.add(new Budget(id, name, description, amount, date, subCategoryFk , balanceFk));
        }

        return list;
    }

    /**
     * Getter for the expenses list
     *
     * @return ArrayList of expenses
     */
    public ArrayList<Budget> getBudgetList() {
        return budgetsList;
    }

    /**
     * Refreshing the connection and data
     */
    @Override
    public void refreshConnection() {
        data.refreshData();
        try {
            budgetsList = convertToExpenses();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }
}