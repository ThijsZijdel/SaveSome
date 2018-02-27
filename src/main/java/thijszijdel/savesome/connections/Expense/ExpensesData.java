package thijszijdel.savesome.connections.Expense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpensesData implements IData {
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

    /**
     * Get expenses for a specifc month
     *
     * @param monthKey for the correct month
     * @return the resultSet
     */
    public ResultSet getExpensesResultSet(int monthKey) {
        try {
            //TODO impliment where statement
            return connection.executeResultSetQuery("SELECT * FROM Expense ORDER BY date DESC ;");
        } catch (SQLException e) {
            MainApp.log(e);
            return null;
        }
    }

    /**
     * Refreshing the data connection
     */
    @Override
    public void refreshData() {
        try {
            this.expensesResultSet = getExpensesData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    @Override
    public void update(int key) {

    }


    /**
     * Get the resultSet for a specific month
     *
     * @param month key
     * @return resultSet for a specific month
     */
    protected ResultSet getResultSetMonth(int month) {

        try {
            //TODO add where monthfk = month!!
            return  connection.executeResultSetQuery("" +
                    "SELECT sum(amount) AS amount, " +
                    "SubCategory.name, SubCategory.color " +
                    "FROM Expense " +
                    "LEFT JOIN SubCategory " +
                    "ON Expense.subCategoryFk = SubCategory.idSubCategory "+
                    "GROUP BY SubCategory.name, SubCategory.color;");


        } catch (SQLException e) {
            MainApp.log(e);
            return null;
        }

    }
}
