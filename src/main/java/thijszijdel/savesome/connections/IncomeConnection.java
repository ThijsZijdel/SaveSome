package thijszijdel.savesome.connections;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.data.IncomeData;
import thijszijdel.savesome.models.Income;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeConnection implements Connection {

    private final IncomeData data = new IncomeData();

    private ArrayList<Income> incomesList = new ArrayList<>();


    /**
     * Constructor
     */
    public IncomeConnection() {
        try {
            this.incomesList = convertToIncomes();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Method for converting the resultSet to a usable arrayList on initialize
     *
     * @return ArrayList Incomes (this)
     * @throws SQLException resultSet
     */
    private ArrayList<Income> convertToIncomes() throws SQLException {
        ArrayList<Income> list = new ArrayList<>();

        ResultSet resultSet = data.getIncomesResultSet();

        while (resultSet.next()) {
            int incomeId =  resultSet.getInt("incomeId");
            int alreadyPaid = resultSet.getInt("alreadyPaid");
            int repeatingFk = resultSet.getInt("repeatingFk");
            double amount = resultSet.getDouble("amount");
            String description = resultSet.getString("description");
            int balanceFk = resultSet.getInt("balanceFk");
            Date date = resultSet.getDate("date");
            String name = resultSet.getString("name");


            list.add(new Income(incomeId, name, description, amount, repeatingFk, date, balanceFk, alreadyPaid));
        }

        return list;
    }

    /**
     * Getter for the Incomes list
     *
     * @return ArrayList of expenses
     */
    public ArrayList<Income> getIncomesList() {
        return incomesList;
    }

    /**
     * Refreshing the connection and data
     */
    @Override
    public void refreshConnection() {
        data.refreshData();
        try {
            incomesList = convertToIncomes();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }
}
