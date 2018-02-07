package thijszijdel.savesome.models;

import java.sql.Date;
import java.sql.Time;

public class Expense {
    private String expenseId;
    private String name;
    private String description;
    private double amount;
    private Date date;
    private Time time;
    private boolean isNegative;


    public Expense(String expenseId, String name, String description, double amount, Date date, Time time) {
        this.expenseId = expenseId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.isNegative = (amount < 0);

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

    public Time getTime() {
        return time;
    }
}
