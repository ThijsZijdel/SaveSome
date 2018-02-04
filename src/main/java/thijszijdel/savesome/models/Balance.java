package thijszijdel.savesome.models;

public class Balance {
    private String id;
    private String name;
    private Float amount;
    private String description;
    private boolean isNegative;

    public Balance(String id, String name, Float amount, String description){
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

    public Float getAmount() {
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
            return "- "+Float.toString(amount);
        else
            return "+ "+Float.toString(amount);
    }

    @Override
    public String toString() {
        return name+"  "+getDisplayAmount()+" | "+description;
    }
}
