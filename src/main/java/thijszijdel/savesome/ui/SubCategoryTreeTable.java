package thijszijdel.savesome.ui;

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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;

import thijszijdel.savesome.models.SubCategory;

import java.util.ArrayList;

public class SubCategoryTreeTable {

        /**
         * List of all the recursive categories
         */
        private ObservableList<RecursiveSubCat> categoryList = FXCollections.observableArrayList();

        /**
         * List of normal categories that will be converted
         */
        private final ArrayList<SubCategory> categoriesData = MainApp.getCategoryConnection().getSubCategoryList();


        /**
         * Columns
         */
        private JFXTreeTableColumn<RecursiveSubCat, String> idCol = new JFXTreeTableColumn<>("Id");
        private JFXTreeTableColumn<RecursiveSubCat, String> fkCol = new JFXTreeTableColumn<>("Fk");
        private JFXTreeTableColumn<RecursiveSubCat, String> nameCol = new JFXTreeTableColumn<>("Category name");
        private JFXTreeTableColumn<RecursiveSubCat, String> descCol = new JFXTreeTableColumn<>("Description");


        /**
         * Table itself
         */
        private final TreeItem<RecursiveSubCat> root = new RecursiveTreeItem<>(categoryList, RecursiveTreeObject::getChildren);
        private JFXTreeTableView<RecursiveSubCat> categoriesTable = new JFXTreeTableView<>(root);


        /**
         * Main constructor, based on the attributes is the category tree table setup
         *
         * @param search    field for searching
         * @param hits      label for amount of hits
         * @param location  where the table will be set
         */
        public SubCategoryTreeTable(JFXTextField search, Label hits, Pane location){
            setupColumns();
            setupEdit();
            getData();
            setupTree(location);

            //if the params are passed, set them up
            if (search != null) setupSearch(search);
            if (hits != null) setupHits(hits);
        }

        /**
         * Setup all the columns for the table
         */
        private void setupColumns() {

            idCol.setMinWidth(100);
            idCol.setCellValueFactory((
                    TreeTableColumn.CellDataFeatures<RecursiveSubCat, String> param)-> {
                if (idCol.validateValue(param))
                    return param.getValue().getValue().idCategory;
                else
                    return idCol.getComputedValue(param);
            });

            fkCol.setMinWidth(100);
            fkCol.setCellValueFactory((
                    TreeTableColumn.CellDataFeatures<RecursiveSubCat, String> param)-> {
                if (fkCol.validateValue(param))
                    return param.getValue().getValue().fkCategory;
                else
                    return fkCol.getComputedValue(param);
            });

            nameCol.setMinWidth(150);
            nameCol.setCellValueFactory((
                    TreeTableColumn.CellDataFeatures<RecursiveSubCat, String> param)->{
                if (nameCol.validateValue(param))
                    return param.getValue().getValue().nameCategory;
                else
                    return nameCol.getComputedValue(param);
            });


            descCol.setMinWidth(250);
            descCol.setCellValueFactory((
                    TreeTableColumn.CellDataFeatures<RecursiveSubCat, String> param)->{
                if (descCol.validateValue(param))
                    return param.getValue().getValue().descCategory;
                else
                    return descCol.getComputedValue(param);
            });
        }

        /**
         * Setup the editable configurations for the columns
         */
        private void setupEdit(){
            descCol.setCellFactory((TreeTableColumn<RecursiveSubCat, String> param)->
                    new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

            descCol.setOnEditCommit((
                    TreeTableColumn.CellEditEvent<RecursiveSubCat, String> t)->{
                //TODO :: Update this record in db
                System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
                System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
                ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).descCategory.set(t.getNewValue());
            });



            nameCol.setCellFactory((TreeTableColumn<RecursiveSubCat, String> param)->
                    new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

            nameCol.setOnEditCommit((
                    TreeTableColumn.CellEditEvent<RecursiveSubCat, String> t)->{
                //TODO :: Update this record in db
                System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
                System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
                ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).nameCategory.set(t.getNewValue());
            });



            fkCol.setCellFactory((TreeTableColumn<RecursiveSubCat, String> param)->
                    new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

            fkCol.setOnEditCommit((
                    TreeTableColumn.CellEditEvent<RecursiveSubCat, String> t)->{
                //TODO :: Update this record in db
                System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).fkCategory+" is the id");
                System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
                ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).fkCategory.set(t.getNewValue());
            });


            idCol.setCellFactory((TreeTableColumn<RecursiveSubCat, String> param)->
                    new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

            idCol.setOnEditCommit((
                    TreeTableColumn.CellEditEvent<RecursiveSubCat, String> t)->{
                //TODO :: Update this record in db
                System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
                System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
                ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory.set(t.getNewValue());
            });
        }

        /**
         * Get all the data for the table
         * Note: data is converted to a recursive variant
         */
        private void getData(){
            for (SubCategory cat : categoriesData)
                categoryList.add(new RecursiveSubCat(String.valueOf(cat.getSubCategoryId()), String.valueOf(cat.getIdCategoryFk()),  cat.getName(),cat.getDescription()));
        }

        /**
         * Setup for the table (all the pieces are put together)
         * @param location      Pane where the table will be set
         */
        private void setupTree(Pane location){
            categoriesTable.setShowRoot(false);
            categoriesTable.setEditable(true);

            categoriesTable.getColumns().setAll(idCol, fkCol, nameCol, descCol);

            categoriesTable.setRoot(root);
            categoriesTable.setMinWidth(500);
            categoriesTable.setPrefWidth(650);

            location.getChildren().add(categoriesTable);
        }

        /**
         * Setup the search for the table
         * @param search text field that will be used for the searching
         */
        private void setupSearch(JFXTextField search){
            search.textProperty().addListener((o, oldVal, newVal)-> {
                categoriesTable.setPredicate(RecursiveSubCat ->
                        RecursiveSubCat.getValue().descCategory.get().contains(newVal)
                                || RecursiveSubCat.getValue().idCategory.get().contains(newVal)
                                || RecursiveSubCat.getValue().nameCategory.get().contains(newVal));
            });

        }

        /**
         * Setup the hit results
         * @param hits label where this will be displayed
         */
        private void setupHits(Label hits){
            hits.textProperty().bind(Bindings.createStringBinding(()->
                            categoriesTable.getCurrentItemsCount()+"",
                    categoriesTable.currentItemsCountProperty()
            ));
        }

        /**
         * RecursiveCategory class for the categories setup
         * Note: Strings are converted to StringProperties
         */
        private class RecursiveSubCat extends RecursiveTreeObject<thijszijdel.savesome.ui.SubCategoryTreeTable.RecursiveSubCat> {
            StringProperty nameCategory;
            StringProperty descCategory;
            StringProperty idCategory;
            StringProperty fkCategory;

            /**
             * basic constructor
             */
            private RecursiveSubCat(String idCategory, String fkCategory, String name, String desc) {
                this.idCategory = new SimpleStringProperty(idCategory) ;
                this.fkCategory = new SimpleStringProperty(fkCategory);
                this.nameCategory = new SimpleStringProperty(name);
                this.descCategory = new SimpleStringProperty(desc);
            }
        }


}
