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

    private final int mainBudgetId = 1;
    private Budget mainBudget = null;

    /**
     * Constructor
     */
    public BudgetConnection() {
        try {
            this.budgetsList = convertToBudgets();
            this.mainBudget = getMainBudgetFromList();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    private Budget getMainBudgetFromList() {
        for (Budget budget : this.budgetsList)
            if (budget.getId() == mainBudgetId)
                return budget;
        MainApp.setAppMessage("No main budget found");
        return null;
    }

    /**
     * Method for converting the resultSet to a usable arrayList on initialize
     *
     * @return ArrayList Expenses (this)
     * @throws SQLException resultSet
     */
    private ArrayList<Budget> convertToBudgets() throws SQLException {
        ArrayList<Budget> list = new ArrayList<>();

        ResultSet resultSet = data.getBudgetResultSet();

        while (resultSet.next()) {
            int id =  resultSet.getInt("idBudget");
            String typeFk = resultSet.getString("typeFk");
            int repeatingFk = resultSet.getInt("repeatingFk");
            double amountLeft = resultSet.getDouble("amountLeft");
            double amountStart = resultSet.getDouble("amountStart");
            String description = resultSet.getString("description");
            String displayName = resultSet.getString("displayName");
            Date startDate = resultSet.getDate("startDate");
            Date endDate = resultSet.getDate("endDate");
            String name = resultSet.getString("name");




            list.add(new Budget(id, typeFk, repeatingFk, amountLeft, amountStart, description , displayName, startDate, endDate, name));
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
            budgetsList = convertToBudgets();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    public Budget getMainBudget() {
        return mainBudget;
    }
}