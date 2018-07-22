package thijszijdel.savesome.settings;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.JDBC;
import thijszijdel.savesome.interfaces.IData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SettingsData implements IData {

    private ResultSet settingResultSet;

    private HashMap<Integer, Setting> settings = new HashMap<>();

    private final int MAIN_BALANCE_KEY = 1;


    /**
     * Constructor for the database connection for settings data
     */
    public SettingsData(){
        try {
            this.settingResultSet = getSettingsData();
        } catch (SQLException e) {
            MainApp.log(e);
        }

        if (this.settingResultSet != null)
            settings = convertToSettingsMap();


    }

    private HashMap<Integer, Setting> convertToSettingsMap(){
        HashMap<Integer, Setting> map = new HashMap<>();

        try {
            while (settingResultSet.next()){
                int id = settingResultSet.getInt("idSettings");
                String name = settingResultSet.getString("name");
                String description = settingResultSet.getString("description");
                String value = settingResultSet.getString("value");

                map.put(id, new Setting(id, name, description, value));
            }
        } catch (SQLException e) {
            MainApp.log(e);
        }

        return map;
    }

    /**
     * Get settings data from the database
     *
     * @return the resultSet based on the sql query
     * @throws SQLException database connection
     */
    private ResultSet getSettingsData() throws SQLException{
        JDBC conn = new JDBC();
        return conn.executeResultSetQuery("SELECT * FROM Settings;");
    }


    @Override
    public void refreshData() {
        try {
            this.settingResultSet = getSettingsData();
        } catch (SQLException e) {
            MainApp.log(e);
        }

        if (this.settingResultSet != null)
            settings = convertToSettingsMap();

    }
    @Override
    public void update(int key, String col, String val) {
        try {
            connection.executeUpdateQuery("UPDATE "+col+" = '"+val+"' WHERE idBudget = '"+key+"'");
            MainApp.setAppMessage("Setting "+key+" is updated.");
        } catch (Exception e){
            MainApp.log(e);
            MainApp.setAppMessage("Setting "+key+" failed to update.");
        }
    }
}
