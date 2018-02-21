package thijszijdel.savesome.connections.Income;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeData implements IData {
    private ResultSet incomesResultSet = null;


    /**
     * Constructor for the database connection for incomes
     */
    public IncomeData(){
        try {
            this.incomesResultSet = getIncomesData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Get incomes data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private final ResultSet getIncomesData() throws SQLException{
        return connection.executeResultSetQuery("SELECT * FROM Income;");
    }

    public ResultSet getIncomesResultSet() {
        return incomesResultSet;
    }

    @Override
    public void refreshData() {
        try {
            this.incomesResultSet = getIncomesData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }
}
