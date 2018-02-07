package thijszijdel.savesome.database.data;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.JDBC;

public interface Data {

    JDBC connection = MainApp.getConnection();

    void refreshData();
}
