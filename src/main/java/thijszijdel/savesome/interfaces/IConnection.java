package thijszijdel.savesome.interfaces;

import java.util.ArrayList;

public interface IConnection {

    void refreshConnection();

    ArrayList getList();

    Object get(int key);
}
