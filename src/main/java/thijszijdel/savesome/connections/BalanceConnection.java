package thijszijdel.savesome.connections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.models.Balance;

import java.util.ArrayList;

public class BalanceConnection implements Connection{

    private ArrayList<VBox> balanceDisplays = new ArrayList<>();
    private ArrayList<Balance> balances = new ArrayList<>();


    /**
     * Constructor for the balance connection
     */
    public BalanceConnection(){
        this.balances = getBalances();

        if (this.balances != null && !this.balances.isEmpty())
            this.balanceDisplays = generateBalanceDisplays();
    }

    /**
     * Get all the balances based on the expenses / tabel
     *
     * @return array of balances
     */
    private ArrayList<Balance> getBalances() {
        ArrayList<Balance> balances = new ArrayList<>();


        //dummy data, this will be calculated (or/and stored)
        Balance ing = new Balance("B32", "ING", 2344.50, "Main account");
        Balance abn = new Balance("AB3", "ABN", -145.70, "Sub account");
        Balance lok = new Balance("LO1", "LOK", 2245.70, "Savings account");

        balances.add(ing);
        balances.add(abn);
        balances.add(lok);

        return balances;
    }

    /**
     * Generate the balance displays
     *
     * @return array of balance vBoxes
     */
    private ArrayList<VBox> generateBalanceDisplays() {
        ArrayList<VBox> displays = new ArrayList<>();

        for (Balance balance : balances){
            displays.add(display(balance));
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
        VBox display = new VBox();
        display.alignmentProperty().set(Pos.CENTER);
        display.setPadding( new Insets(2, 12, 2 , 12));

        Label amount = new Label();
        Label name = new Label();
        amount.setStyle("-fx-font-size: 15px");
        name.setStyle("-fx-font-size: 10px");
        name.setTextFill(Color.web(MainApp.config.getTextColor()));

        if (balance.isNegative())
            amount.setTextFill(Color.web(MainApp.config.getAlertColor()));
        else
            amount.setTextFill(Color.web(MainApp.config.getTextColor()));


        name.setText(balance.getName());
        amount.setText(balance.getDisplayAmount());


        display.getChildren().addAll(amount, name);

        return display;
    }

    public ArrayList<VBox> getAllBalanceDisplays() {
        return balanceDisplays;
    }





}
