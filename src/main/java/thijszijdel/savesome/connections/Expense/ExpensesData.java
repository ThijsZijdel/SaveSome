package thijszijdel.savesome.connections.Expense;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpensesData implements Data {
    private ResultSet expensesResultSet = null;


    /**
     * Constructor for the database connection for expenses data
     */
    public ExpensesData(){
        try {
            this.expensesResultSet = getExpensesData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Get expenses data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private final ResultSet getExpensesData() throws SQLException{
        return connection.executeResultSetQuery("SELECT * FROM Expense ORDER BY date DESC;");
    }

    public ResultSet getExpensesResultSet() {
        return expensesResultSet;
    }

    @Override
    public void refreshData() {
        try {
            this.expensesResultSet = getExpensesData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }
}