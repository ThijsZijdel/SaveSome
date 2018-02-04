package thijszijdel.savesome.connections;

import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.data.CategoryData;
import thijszijdel.savesome.models.Category;
import thijszijdel.savesome.models.SubCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryConnection implements Connection{

    private final CategoryData data = new CategoryData();

    private ArrayList<Category> mainCategoryList = new ArrayList<>();
    private ArrayList<SubCategory> subCategoryList = new ArrayList<>();

    /**
     * Constructor
     */
    public CategoryConnection(){
        try {
            mainCategoryList = convertToMainCatList();
            subCategoryList = convertToSubCatList();
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
}
