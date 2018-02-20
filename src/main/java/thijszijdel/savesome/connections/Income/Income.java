package thijszijdel.savesome.connections.Income;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Balance.Balance;

import java.sql.Date;

public class Income {
    private int incomeId;
    private String name;
    private String description;
    private double amount;
    private int repeatingFk;
    private Date date;
    private boolean isNegative;

    private int balanceFk;

    private Balance balance;
    private int alreadyPaid;
    private boolean alreadyPaidBool;

    public Income(int incomeId, String name, String description, double amount, int repeatingFk, Date date, int balanceFk, int alreadyPaid) {
        this.incomeId = incomeId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.repeatingFk = repeatingFk;
        this.date = date;

        this.isNegative = (amount < 0);

        if (balanceFk != 0) {
            this.balanceFk = balanceFk;
            this.balance = MainApp.balanceConnection().get(balanceFk);
        }

        this.alreadyPaid = alreadyPaid;
        this.alreadyPaidBool = alreadyPaid == 1;

    }
    public String getDisplayAmount() {
        if (isNegative)
            return Double.toString(amount);
        else
            return "+ "+Double.toString(amount);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public int getAlreadyPaid() {
        return alreadyPaid;
    }

    public int getBalanceFk() {
        return balanceFk;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public int getRepeatingFk() {
        return repeatingFk;
    }

    public Balance getBalance() {
        return balance;
    }

    public boolean isNegative() {
        return isNegative;
    }
}
