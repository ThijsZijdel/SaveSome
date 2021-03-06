package thijszijdel.savesome.connections.Income;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IConnection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeConnection implements IConnection {

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
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            int alreadyPaid = resultSet.getInt("alreadyPaid");
            int repeatingFk = resultSet.getInt("repeatingFk");
            double amount = resultSet.getDouble("amount");

            int balanceFk = resultSet.getInt("balanceFk");
            Date date = resultSet.getDate("date");
            int monthFk = resultSet.getInt("monthFk");
            int compnayFk = resultSet.getInt("companyFk");



            list.add(new Income(incomeId, name, description, amount, repeatingFk, date, balanceFk, alreadyPaid, monthFk, compnayFk));
        }

        return list;
    }

    /**
     * Getter for the Incomes list
     *
     * @return ArrayList of incomes
     */
    @Override
    public ArrayList<Income> getList() {
        return incomesList;
    }

    @Override
    public Object get(int key) {
        for (Income income : this.incomesList)
            if (income.getIncomeId() == key)
                return income;
        MainApp.log(new Exception("no income matched key"));
        return null;
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
