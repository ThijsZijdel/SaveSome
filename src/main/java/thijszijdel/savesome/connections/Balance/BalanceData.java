package thijszijdel.savesome.connections.Balance;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanceData implements IData {
    private ResultSet balanceResultSet = null;


    /**
     * Constructor for the database connection for balance data
     */
    public BalanceData(){
        try {
            this.balanceResultSet = getBalanceData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Get balance data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private final ResultSet getBalanceData() throws SQLException{
        return connection.executeResultSetQuery("SELECT * FROM Balance;");
    }

    protected ResultSet getBalanceResultSet() {
        return balanceResultSet;
    }

    @Override
    public void refreshData() {
        try {
            this.balanceResultSet = getBalanceData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }
}
