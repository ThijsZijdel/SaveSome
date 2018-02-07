package thijszijdel.savesome.models;


import javafx.scene.image.Image;

public class SubCategory {

    private String subCategoryId;
    private String name;
    private String description;
    private Image image;

    public SubCategory(String subCategoryId, String name, String description){
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.description = description;
        this.image = new Image("/images/icon.png");
    }


    public String getSubCategoryId() {
        return subCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getIcon() {
        return image;
    }
}
