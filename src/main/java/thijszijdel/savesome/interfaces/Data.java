package thijszijdel.savesome.interfaces;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.JDBC;

public interface Data {

    JDBC connection = MainApp.getConnection();

    void refreshData();


    // TODO : DATA INTERFACE
    // more specific methods
    //      Like: data query
    //      ResultSet setup etc etc.
    // TODO: Maybe specifc searches??
}
