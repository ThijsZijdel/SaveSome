package thijszijdel.savesome.connections.Category;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Expense.Expense;
import thijszijdel.savesome.interfaces.IData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryData implements IData {

    private ResultSet mainCategoryResultSet = null;
    private ResultSet subCategoryResultSet = null;


    /**
     * Constructor for the database connection for category data
     */
    public CategoryData(){
        try {
            this.mainCategoryResultSet = getMainCategoryData();
            this.subCategoryResultSet = getSubCategoryData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    /**
     * Get main category data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private final ResultSet getMainCategoryData() throws SQLException{
        return connection.executeResultSetQuery("SELECT * FROM Category;");
    }

    /**
     * Get sub category data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private final ResultSet getSubCategoryData() throws SQLException{
        return connection.executeResultSetQuery("SELECT * FROM SubCategory;");
    }

    /**
     * @return initialized main category resultSet
     */
    protected ResultSet getMainCategoryResultSet() {
        return this.mainCategoryResultSet;
    }

    /**
     * @return initialized sub category resultSet
     */
    protected ResultSet getSubCategoryResultSet() {
        return this.subCategoryResultSet;
    }

    /**
     * Refreshing the catergory data
     */
    @Override
    public void refreshData() {
        try {
            this.mainCategoryResultSet = getMainCategoryData();
            this.subCategoryResultSet = getSubCategoryData();
        } catch (SQLException e) {
            MainApp.log(e);
        }
    }

    @Override
    public void update(int key, String col, String val) {
        try {
            connection.executeUpdateQuery("UPDATE "+col+" = '"+val+"' WHERE idBudget = '"+key+"'");
            MainApp.setAppMessage("Category "+key+" is updated.");
        } catch (Exception e){
            MainApp.log(e);
            MainApp.setAppMessage("Category "+key+" failed to update.");
        }
    }


}
