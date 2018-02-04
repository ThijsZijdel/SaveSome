package thijszijdel.savesome.models;

public class Balance {
    private String id;
    private String name;
    private Double amount;
    private String description;
    private boolean isNegative;

    public Balance(String id, String name, Double amount, String description){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.isNegative = (amount < 0);
    }
    public String getId() {
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
