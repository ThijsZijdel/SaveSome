package thijszijdel.savesome.connections.Budget;

import java.sql.Date;

public class Budget {
    private int id;
    private String typeFk;
    private int repeatingFk;
    private double amountLeft;
    private double amountStart;
    private String description;
    private String displayName;
    private Date startDate;
    private Date endDate;
    private String name;
    public Budget(int id, String typeFk, int repeatingFk, double amountLeft, double amountStart, String description, String displayName, Date startDate, Date endDate, String name) {
        this.id=id;
        this.typeFk=typeFk;
        this.repeatingFk=repeatingFk;
        this.amountLeft=amountLeft;
        this.amountStart=amountStart;
        this.description=description;
        this.displayName=displayName;
        this.startDate=startDate;
        this.endDate=endDate;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public double getAmountLeft() {
        return amountLeft;
    }

    public double getAmountStart() {
        return amountStart;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
