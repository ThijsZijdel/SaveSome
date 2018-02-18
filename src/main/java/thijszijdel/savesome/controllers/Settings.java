package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.models.Category;


import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Settings implements Initializable {

   // @FXML
   // JFXTreeTableView categories;
    @FXML
   Pane mainCatPane;
    /**
     * Initialize method for the home controller class
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeMainCategory();
    }


    //TODO :: Comment + Move + Split this way to big method
    public void initializeMainCategory(){

        //initialize cols
        JFXTreeTableColumn<RecursiveCat, String> idCol = new JFXTreeTableColumn<>("Id");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory((
        TreeTableColumn.CellDataFeatures<RecursiveCat, String> param)-> {
            if (idCol.validateValue(param))
                return param.getValue().getValue().idCategory;
            else
                return idCol.getComputedValue(param);
        });

        JFXTreeTableColumn<RecursiveCat, String> nameCol = new JFXTreeTableColumn<>("Category name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory((
        TreeTableColumn.CellDataFeatures<RecursiveCat, String> param)->{
            if (nameCol.validateValue(param))
                return param.getValue().getValue().nameCategory;
            else
                return nameCol.getComputedValue(param);
        });

        JFXTreeTableColumn<RecursiveCat, String> descCol = new JFXTreeTableColumn<>("Description");
        descCol.setMinWidth(250);
        descCol.setCellValueFactory((
        TreeTableColumn.CellDataFeatures<RecursiveCat, String> param)->{
            if (descCol.validateValue(param))
                return param.getValue().getValue().descCategory;
            else
                return descCol.getComputedValue(param);
        });



        //Setup editable cells
        descCol.setCellFactory((TreeTableColumn<RecursiveCat, String> param)->
                new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

        descCol.setOnEditCommit((
            TreeTableColumn.CellEditEvent<RecursiveCat, String> t)->{
            //TODO :: Update this record in db
            System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
            System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
            ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).descCategory.set(t.getNewValue());
        });



        nameCol.setCellFactory((TreeTableColumn<RecursiveCat, String> param)->
                new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

        nameCol.setOnEditCommit((
            TreeTableColumn.CellEditEvent<RecursiveCat, String> t)->{
            //TODO :: Update this record in db
            System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
            System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
            ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).nameCategory.set(t.getNewValue());
        });



        idCol.setCellFactory((TreeTableColumn<RecursiveCat, String> param)->
                new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

        idCol.setOnEditCommit((
            TreeTableColumn.CellEditEvent<RecursiveCat, String> t)->{
            //TODO :: Update this record in db
            System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
            System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
            ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory.set(t.getNewValue());
        });




        // data
        ObservableList<RecursiveCat> categoryList = FXCollections.observableArrayList();

        ArrayList<Category> categoriesData = MainApp.getCategoryConnection().getMainCategoryList();
        for (Category cat : categoriesData)
            categoryList.add(new RecursiveCat(String.valueOf(cat.getId()),cat.getName(),cat.getDescription()));



        // build tree
        final TreeItem<RecursiveCat> root = new RecursiveTreeItem<>(categoryList, RecursiveTreeObject::getChildren);
        JFXTreeTableView<RecursiveCat> categories = new JFXTreeTableView<>(root);


        categories.setShowRoot(false);
        categories.setEditable(true);
        categories.getColumns().setAll(idCol, nameCol, descCol);
        categories.setRoot(root);
        categories.setMinWidth(500);
        categories.setPrefWidth(650);

        mainCatPane.getChildren().add(categories);



        //search
        search.textProperty().addListener((o, oldVal, newVal)-> {
            categories.setPredicate(RecursiveCat ->
                       RecursiveCat.getValue().descCategory.get().contains(newVal)
                    || RecursiveCat.getValue().idCategory.get().contains(newVal)
                    || RecursiveCat.getValue().nameCategory.get().contains(newVal));
        });

        //result count
        hits.textProperty().bind(Bindings.createStringBinding(()->
                categories.getCurrentItemsCount()+"", categories.currentItemsCountProperty()
        ));

    }

    class RecursiveCat extends RecursiveTreeObject<RecursiveCat>{
        StringProperty nameCategory;
        StringProperty descCategory;
        StringProperty idCategory;

        public RecursiveCat(String idCategory, String name, String desc) {
            this.idCategory = new SimpleStringProperty(idCategory) ;
            this.nameCategory = new SimpleStringProperty(name);
            this.descCategory = new SimpleStringProperty(desc);
        }
    }


    @FXML JFXTextField search;
    @FXML Label hits;
}
