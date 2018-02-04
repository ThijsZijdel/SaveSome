package thijszijdel.savesome.connections;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import thijszijdel.savesome.models.Balance;

import java.util.ArrayList;

public class BalanceConnection implements Connection{

    private ArrayList<VBox> balanceDisplays = new ArrayList<>();
    private ArrayList<Balance> balances = new ArrayList<>();


    public BalanceConnection(){
        this.balances = calculateBalances();

        if (this.balances != null && !this.balances.isEmpty())
            this.balanceDisplays = generateBalanceDisplays();
    }

    private ArrayList<Balance> calculateBalances() {

        return null;
    }

    private ArrayList<VBox> generateBalanceDisplays() {
        ArrayList<VBox> displays = new ArrayList<>();

        VBox display = new VBox();
        display.alignmentProperty().set(Pos.CENTER);

        Label amount = new Label();
        Label name = new Label();
        amount.setTextFill(Color.web("#FFF"));
        name.setTextFill(Color.web("#FFF"));
        amount.setStyle("-fx-font-size: 15px");
        name.setStyle("-fx-font-size: 10px");


        for (Balance balance : balances){

            name.setText(balance.getName());
            amount.setText(balance.getDisplayAmount());

            display.getChildren().addAll(amount, name);

            displays.add(display);
        }


        return displays;
    }

    public ArrayList<VBox> getAllBalanceDisplays() {
        return balanceDisplays;
    }





}
