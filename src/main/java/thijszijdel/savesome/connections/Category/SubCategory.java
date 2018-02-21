package thijszijdel.savesome.connections.Category;


import javafx.scene.image.Image;

public class SubCategory {

    private int idSubcategory, idCategoryFk;
    private String name;
    private String description;
    private String color;

    public SubCategory(int idSubcategory, int idCategoryFk, String name, String description, String color){
        this.idSubcategory = idSubcategory;
        this.idCategoryFk = idCategoryFk;
        this.name = name;
        this.description = description;
        this.color = color;

    }


    public int getSubCategoryId() {
        return idSubcategory;
    }

    public int getIdCategoryFk() {
        return idCategoryFk;
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
