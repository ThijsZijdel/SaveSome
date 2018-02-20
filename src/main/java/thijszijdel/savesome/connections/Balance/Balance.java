package thijszijdel.savesome.connections.Balance;

public class Balance {
    private int id, bankFk;
    private String name;
    private String type;
    private Double amount;
    private String description;
    private boolean isNegative;


    public Balance(int id, String name, String description, String type, double amount, int bankFk) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.isNegative = (amount < 0);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNegative() {
        return isNegative;
    }


    public String getDisplayAmount() {
        if (isNegative)
            return Double.toString(amount);
        else
            return "+ "+Double.toString(amount);
    }

    @Override
    public String toString() {
        return name+"  "+getDisplayAmount()+" | "+description;
    }
}
