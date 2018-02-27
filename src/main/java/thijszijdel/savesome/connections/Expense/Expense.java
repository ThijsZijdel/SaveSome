package thijszijdel.savesome.connections.Expense;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Balance.Balance;
import thijszijdel.savesome.connections.Category.SubCategory;

import java.sql.Date;
import java.sql.Time;

public class Expense {
    private int expenseId;
    private String name;
    private String description;
    private double amount;
    private Date date;
    private boolean isNegative;
    private int subCategoryFk;
    private SubCategory subCategory;
    private String month;

    private int balanceFk;
    private Balance balance;

    public Expense(int expenseId, String name, String description, double amount, Date date, String month, int subCategoryFk, int balanceFk) {
        this.expenseId = expenseId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.month = month;

        this.isNegative = (amount < 0);

        if (balanceFk != 0) {
            this.balanceFk = balanceFk;
            this.balance = MainApp.balanceConnection().get(balanceFk);
        }

        if (subCategoryFk != 0) {
            this.subCategoryFk = subCategoryFk;
            this.subCategory = MainApp.getCategoryConnection().getSubCat(subCategoryFk);
        }

    }
    public String getDisplayAmount() {
        if (isNegative)
            return Double.toString(amount);
        else
            return "+ "+Double.toString(amount);
    }

    public int getExpenseId() {
        return expenseId;
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

    public int getSubCategoryFk() {
        return subCategoryFk;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public Balance getBalance() {
        return balance;
    }

    public boolean isNegative() {
        return isNegative;
    }
}
