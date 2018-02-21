package thijszijdel.savesome.ui.tables;

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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Category.Category;

import java.util.ArrayList;

public class MainCategoryTreeTable {

    /**
     * List of all the recursive categories
     */
    private ObservableList<RecursiveCat> categoryList = FXCollections.observableArrayList();

    /**
     * List of normal categories that will be converted
     */
    private final ArrayList<Category> categoriesData = MainApp.getCategoryConnection().getMainCategoryList();


    /**
     * Columns
     */
    private JFXTreeTableColumn<RecursiveCat, String> idCol = new JFXTreeTableColumn<>("Id");
    private JFXTreeTableColumn<RecursiveCat, String> nameCol = new JFXTreeTableColumn<>("Category name");
    private JFXTreeTableColumn<RecursiveCat, String> descCol = new JFXTreeTableColumn<>("Description");


    /**
     * Table itself
     */
    private final TreeItem<RecursiveCat> root = new RecursiveTreeItem<>(categoryList, RecursiveTreeObject::getChildren);
    private JFXTreeTableView<RecursiveCat> categoriesTable = new JFXTreeTableView<>(root);


    /**
     * Main constructor, based on the attributes is the category tree table setup
     *
     * @param search    field for searching
     * @param hits      label for amount of hits
     */
    public MainCategoryTreeTable(JFXTextField search, Label hits){
        setupColumns();
        setupEdit();
        getData();
        setupTree();

        //if the params are passed, set them up
        if (search != null) setupSearch(search);
        if (hits != null) setupHits(hits);

        setupSelection();
    }
    private SubCategoryTreeTable linkedTable;
    public void setRelatedSubCatTable(SubCategoryTreeTable table){
        this.linkedTable = table;
    }

    private void setupSelection() {
//        categoriesTable.addEventHandler(Event.MOUSE_DOWN, (EventHandler<Event>) event -> {
//
//        });

        categoriesTable.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown()) {
                Node item = ((Node) event.getTarget()).getParent();
                System.out.println(item.toString()+ "<<<<<<<<");
                MainApp.setAppMessage("Expense selected, value: "+categoriesTable.getSelectionModel().getSelectedItem().getValue().idCategory+" <<<");
                System.out.println("Filtered on: +"+categoriesTable.getSelectionModel().getSelectedItem().getValue().idCategory.getValue());
                linkedTable.filter(categoriesTable.getSelectionModel().getSelectedItem().getValue().idCategory.getValue());
            }
        });


                // categoriesTable.getSelectionModel().getSelectedItem().addEventHandler(

       // });

//        categoriesTable.getSelectionModel().selectedItemProperty().addListener(
//                (ChangeListener<RecursiveCat>) (ov, old_val, new_val) -> {
//                    //label.setText(new_val.toString());
//                    MainApp.setAppMessage("Expense selected, value: "+new_val.toString());
//                });

    }

    /**
     * Setup all the columns for the table
     */
    private void setupColumns() {

        idCol.setMinWidth(100);
        idCol.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<RecursiveCat, String> param)-> {
            if (idCol.validateValue(param))
                return param.getValue().getValue().idCategory;
            else
                return idCol.getComputedValue(param);
        });

        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<RecursiveCat, String> param)->{
            if (nameCol.validateValue(param))
                return param.getValue().getValue().nameCategory;
            else
                return nameCol.getComputedValue(param);
        });


        descCol.setMinWidth(250);
        descCol.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<RecursiveCat, String> param)->{
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
        descCol.setCellFactory((TreeTableColumn<RecursiveCat, String> param)->
                new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

        descCol.setOnEditCommit((
                TreeTableColumn.CellEditEvent<RecursiveCat, String> t)->{
            //TODO :: Update this record in db
//            System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
//            System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
//            ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).descCategory.set(t.getNewValue());
        });



        nameCol.setCellFactory((TreeTableColumn<RecursiveCat, String> param)->
                new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

        nameCol.setOnEditCommit((
                TreeTableColumn.CellEditEvent<RecursiveCat, String> t)->{
            //TODO :: Update this record in db
//            System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
//            System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
//            ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).nameCategory.set(t.getNewValue());
        });



        idCol.setCellFactory((TreeTableColumn<RecursiveCat, String> param)->
                new GenericEditableTreeTableCell(new TextFieldEditorBuilder()));

        idCol.setOnEditCommit((
                TreeTableColumn.CellEditEvent<RecursiveCat, String> t)->{
            //TODO :: Update this record in db
//            System.out.println(( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory+" is the id");
//            System.out.println(t.getOldValue()+" <OLD   |   NEW>"+t.getNewValue());
//            ( t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue() ).idCategory.set(t.getNewValue());
        });



    }



    /**
     * Get all the data for the table
     * Note: data is converted to a recursive variant
     */
    private void getData(){
        for (Category cat : categoriesData)
            categoryList.add(new RecursiveCat(
                    String.valueOf(cat.getId()),
                    cat.getName().toLowerCase(),
                    cat.getDescription().toLowerCase()
            ));
    }

    /**
     * Setup for the table (all the pieces are put together)
     */
    private void setupTree(){
        categoriesTable.setShowRoot(false);
        categoriesTable.setEditable(true);

        categoriesTable.getColumns().setAll(idCol, nameCol, descCol);

        categoriesTable.setRoot(root);
        categoriesTable.setMinWidth(500);
        categoriesTable.setPrefWidth(650);

    }

    public JFXTreeTableView<RecursiveCat> getTable() {
        return categoriesTable;
    }

    /**
     * Setup the search for the table
     * @param search text field that will be used for the searching
     */
    private void setupSearch(JFXTextField search){
        search.textProperty().addListener((o, oldVal, newVal)-> {
            categoriesTable.setPredicate(RecursiveCat ->
                               RecursiveCat.getValue().descCategory.get().contains(newVal.toLowerCase())
                            || RecursiveCat.getValue().idCategory.get().contains(newVal.toLowerCase())
                            || RecursiveCat.getValue().nameCategory.get().contains(newVal.toLowerCase()));
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
    private class RecursiveCat extends RecursiveTreeObject<RecursiveCat> {
        StringProperty nameCategory;
        StringProperty descCategory;
        StringProperty idCategory;

        /**
         * basic constructor
         */
        private RecursiveCat(String idCategory, String name, String desc) {
            this.idCategory = new SimpleStringProperty(idCategory) ;
            this.nameCategory = new SimpleStringProperty(name);
            this.descCategory = new SimpleStringProperty(desc);
        }
    }
}
