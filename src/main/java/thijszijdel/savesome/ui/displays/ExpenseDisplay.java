package thijszijdel.savesome.ui.displays;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import thijszijdel.savesome.connections.Expense.Expense;
import thijszijdel.savesome.connections.Settings;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ExpenseDisplay extends HBox{
    private static final String B_SEPERATOR = "        ";

    /**
     * Get an individual expense display
     *
     * @param expense where the display will be based on
     * @return HBox display of the expense
     */
    public HBox getExpenseDisplay(Expense expense) {
        VBox amountBalance = getBalanceAmount(expense);
        String dateTime = getFormattedDate(expense)+B_SEPERATOR; //+ expense.getTime().toString();

        VBox details = new VBox(
                getLabel(expense.getName(),"bold"),
                getLabel(expense.getDescription(),"lighter"),
                getLabel(dateTime,"lighter")
        );

        //add the final styling
        details.getStyleClass().add("detailsBox");
        amountBalance.getStyleClass().add("amountBalanceBox");

        return new HBox(amountBalance, details);
    }


    /**
     * Format the date
     * @param expense for the right date
     * @return formatted date
     */
    private String getFormattedDate(Expense expense) {
        Date date = expense.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, ''yy");

        return formatter.format(date);
    }

    /**
     * Get the balance and amount part of the display
     * @param expense
     * @return
     */
    private VBox getBalanceAmount(Expense expense) {
        Label amount = new Label(expense.getDisplayAmount());
        String balanceN = "";

        if (expense.getBalance() != null)
            balanceN = expense.getBalance().getName();

        Label balance = new Label(" " + balanceN + " ");


        balance.setStyle("-fx-font-size: 10px");
        amount.getStyleClass().add("amountDisplay");

        if (expense.isNegative())
            amount.setTextFill(Color.web(Settings.getAlertColorD()));

        return new VBox(amount, balance);
    }

    /**
     * Make and Set styling on a label
     * @param name          text of the label
     * @param styleClass    styling class
     * @return              label
     */
    private Label getLabel(String name, String styleClass) {
        Label lbl = new Label(name);
        lbl.getStyleClass().add(styleClass);
        return lbl;
    }
}
