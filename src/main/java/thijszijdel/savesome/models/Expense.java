package thijszijdel.savesome.models;

import thijszijdel.savesome.MainApp;

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
    private int subCategoryFk;
    private SubCategory subCategory;


    public Expense(String expenseId, String name, String description, double amount, Date date, Time time, int subCategoryFk) {
        this.expenseId = expenseId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.time = time;

        this.isNegative = (amount < 0);

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

    public int getSubCategoryFk() {
        return subCategoryFk;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }
}
