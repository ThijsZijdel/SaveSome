package thijszijdel.savesome.connections;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.data.CategoryData;
import thijszijdel.savesome.models.Category;
import thijszijdel.savesome.models.SubCategory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryConnection implements Connection{

    private CategoryData data = new CategoryData();

    private ArrayList<Category> mainCategoryList = new ArrayList<>();
    private ArrayList<SubCategory> subCategoryList = new ArrayList<>();

    /**
     * Constructor
     */
    public CategoryConnection(){
        try {
            this.mainCategoryList = convertToMainCatList();
            this.subCategoryList = convertToSubCatList();
        } catch (SQLException e){
            MainApp.log(e);
        }
    }

    private ArrayList<SubCategory> convertToSubCatList() throws SQLException{
        ArrayList<SubCategory> list = new ArrayList<>();

        ResultSet resultSet = data.getSubCategoryResultSet();

        while ( resultSet.next() ){
            String id = "1";
            //resultSet.getString("idCategory");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            list.add(new SubCategory(id, name, description));
        }


        return list;
    }

    private ArrayList<Category> convertToMainCatList() throws SQLException{
        ArrayList<Category> list = new ArrayList<>();

        ResultSet resultSet = data.getMainCategoryResultSet();

        while ( resultSet.next() ){
            String id = resultSet.getString("idCategory");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            list.add(new Category(id, name, description));
        }


        return list;
    }


    public ArrayList<Category> getMainCategoryList() {
        return this.mainCategoryList;
    }

    public ArrayList<SubCategory> getSubCategoryList() {
        return this.subCategoryList;
    }

    public ArrayList<String> getSubCategoryNameList() {
        ArrayList<String> subCatNameList = new ArrayList<>();

        for (SubCategory subCat : this.subCategoryList){
            subCatNameList.add(subCat.getName());
        }
        return subCatNameList;
    }

    public ArrayList<String> getMainCategoryNameList() {
        ArrayList<String> mainCatNameList = new ArrayList<>();

        for (Category cat : this.mainCategoryList){
            mainCatNameList.add(cat.getName());
        }
        return mainCatNameList;
    }

    public void setImage(){


        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());

        Image img = new Image(file.getAbsolutePath());




//        Label name = new Label(category.getName());
//        Label desc = new Label(category.getDescription());
//        ImageView imgView = new ImageView(category.getIcon());
//
//        imgView.maxHeight(25);
//        imgView.setFitHeight(25);
//
//        imgView.maxWidth(25);
//        imgView.setFitWidth(25);
//
//
//
//        HBox box = new HBox(imgView, new VBox(name, desc));
//        HBox.setHgrow(expensesList, Priority.ALWAYS);
//
//        expensesList.getItems().add(box);



    }

    @Override
    public void refreshConnection() {
        data.refreshData();

        try {
            this.mainCategoryList = convertToMainCatList();
            this.subCategoryList = convertToSubCatList();
        } catch (SQLException e){
            MainApp.log(e);
        }
    }
}
