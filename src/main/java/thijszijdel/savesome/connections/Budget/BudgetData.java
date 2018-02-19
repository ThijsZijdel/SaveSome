package thijszijdel.savesome.database.data;

import thijszijdel.savesome.MainApp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetData implements Data{
    private ResultSet budgetResultSet = null;


    /**
     * Constructor for the database connection for budget data
     */
    public BudgetData(){
        try {
            this.budgetResultSet = getBudgetData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Get Budget data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private final ResultSet getBudgetData() throws SQLException{
        return connection.executeResultSetQuery("SELECT * FROM Budget;");
    }

    public ResultSet getBudgetResultSet() {
        return budgetResultSet;
    }

    @Override
    public void refreshData() {
        try {
            this.budgetResultSet = getBudgetData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }
}

