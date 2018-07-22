package thijszijdel.savesome.interfaces;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.JDBC;

public interface IData {

    JDBC connection = MainApp.getConnection();

    void refreshData();


    // TODO : DATA INTERFACE
    // more specific methods
    //      Like: data query
    //      ResultSet setup etc etc.
    // TODO: Maybe specifc searches??

    /**
     * @param key
     */
    void update(int key, String col, String val);
}
