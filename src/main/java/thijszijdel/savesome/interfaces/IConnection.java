package thijszijdel.savesome.interfaces;

import java.util.ArrayList;

public interface IConnection {

    // TODO: Cleanup all the implementation classes
    void refreshConnection();

    ArrayList getList();

    Object get(int key);
}
