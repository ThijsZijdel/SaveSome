package thijszijdel.savesome.connections.Balance;

import com.jfoenix.controls.JFXRippler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IConnection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BalanceConnection implements IConnection {

    private final BalanceData data = new BalanceData();
    private ArrayList<JFXRippler> balanceDisplays = new ArrayList<>();
    private ArrayList<Balance> balances = new ArrayList<>();

    private final String SPACING = " | ";
    /**
     * Constructor for the balance connection
     */
    public BalanceConnection(){
        try {
            this.balances = convertToBalance();
        } catch (SQLException e) {
            MainApp.log(e);
        }

        if (this.balances != null && !this.balances.isEmpty())
            this.balanceDisplays = generateBalanceDisplays();
    }

    @Override
    public ArrayList<Balance> getList() {
        return this.balances;
    }
    private ArrayList<Balance> convertToBalance() throws SQLException {
        ArrayList<Balance> list = new ArrayList<>();

        ResultSet resultSet = data.getBalanceResultSet();

        while ( resultSet.next() ){
            int id = resultSet.getInt("idBalance");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String type = resultSet.getString("type");
            double amount = resultSet.getDouble("amount");
            int bankFk = resultSet.getInt("bankFk");

            list.add(new Balance(id, name, description, type, amount, bankFk));
        }


        return list;
    }

//    public ArrayList<String> getBalanceNameList() {
//        ArrayList<String> balanceNameList = new ArrayList<>();
//
//        for (Balance balance : this.balances){
//            balanceNameList.add(balance.getName());
//        }
//        return balanceNameList;
//    }

    public ArrayList<String> getBalanceComboBoxList() {
        ArrayList<String> balanceComboBoxList = new ArrayList<>();

        for (Balance balance : this.balances){
            balanceComboBoxList.add(balanceComboBoxItem(balance));
        }
        return balanceComboBoxList;
    }

    public String balanceComboBoxItem(Balance balance){
        return balance.getName()+SPACING+balance.getDisplayAmount();
    }



    /**
     * Generate the balance displays
     *
     * @return array of balance vBoxes
     */
    private ArrayList<JFXRippler> generateBalanceDisplays() {
        ArrayList<JFXRippler> displays = new ArrayList<>();

        for (Balance balance : balances){
            displays.add(new JFXRippler(display(balance)));
        }

        return displays;
    }

    /**
     * method for generating a vBox balance display
     *
     * @param balance that will be converted and set
     * @return VBox of the right balance display
     */
    private VBox display(Balance balance) {
        final String ID = String.valueOf(balance.getId());
        VBox display = new VBox();
        display.setId(ID);
        display.alignmentProperty().set(Pos.CENTER);
        display.setPadding( new Insets(2, 12, 2 , 12));

        Label amount = new Label();
        Label name = new Label();
        name.setId(ID);
        amount.setId(ID);
        amount.setStyle("-fx-font-size: 15px");
        name.setStyle("-fx-font-size: 10px");
        name.setTextFill(Color.web(MainApp.config.getTextColorW()));

        if (balance.isNegative())
            amount.setTextFill(Color.web(MainApp.config.getAlertColor()));
        else
            amount.setTextFill(Color.web(MainApp.config.getTextColorW()));


        name.setText(balance.getName());
        amount.setText(balance.getDisplayAmount());

        display.setOnMousePressed((MouseEvent event) -> {

             if (event.isPrimaryButtonDown()) {
                 Node item = ((Node) event.getTarget() ).getParent();


                     System.out.println(item.getId());

                     if (!item.getId().equals("balance"))
                         MainApp.setAppMessage(balance.toString());
             }
         });

        display.getChildren().addAll(amount, name);
        return display;
    }

    public ArrayList<JFXRippler> getAllBalanceDisplays() {
        return balanceDisplays;
    }


    @Override
    public void refreshConnection() {
        data.refreshData();
        
        try {
            this.balances = convertToBalance();
        } catch (SQLException e) {
            MainApp.log(e);
        }

        if (this.balances != null && !this.balances.isEmpty())
            this.balanceDisplays = generateBalanceDisplays();
    }

    @Override
    public Balance get(int key) {
        for (Balance balance : this.balances)
            if (balance.getId() == key)
                return balance;
        MainApp.log(new Exception("no balance matched key"));
        return null;
    }
}
