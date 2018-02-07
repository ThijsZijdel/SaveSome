package thijszijdel.savesome.database.data;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.JDBC;
import thijszijdel.savesome.models.Balance;
import thijszijdel.savesome.models.Setting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class SettingsData implements Data{

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
}
