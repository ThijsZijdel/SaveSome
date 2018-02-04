package thijszijdel.savesome.models;

public class Category {
    private String categoryId, name, description;

    public Category(String categoryId, String name, String description){
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
