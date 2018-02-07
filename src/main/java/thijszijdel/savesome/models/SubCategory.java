package thijszijdel.savesome.models;


import javafx.scene.image.Image;

public class SubCategory {

    private int subCategoryId;
    private String name;
    private String description;
    private String color;

    public SubCategory(int subCategoryId, String name, String description, String color){
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.description = description;
        this.color = color;

    }


    public int getSubCategoryId() {
        return subCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }
}
