package thijszijdel.savesome.models;

import java.util.ArrayList;

public class Category {
    private int categoryId;
    private String name, description;

    public Category(int categoryId, String name, String description){
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

    public int getId() {
        return categoryId;
    }
}
