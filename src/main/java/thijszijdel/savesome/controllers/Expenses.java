package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.CategoryConnection;
import thijszijdel.savesome.interfaces.State;
import thijszijdel.savesome.models.SubCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Expenses implements Initializable, State {


    private static boolean isShowing = false;

    //Create one instance of this class
    private static Expenses instance = null;


    @FXML JFXListView expensesList;

    /**
     * Getter for the instance of this class
     *
     * @return this
     */
    public static Expenses getInstance() {
        //check if the instance already is setted
        if (instance == null) {
            synchronized(Expenses.class) {
                if (instance == null) {
                    instance = new Expenses();
                }
            }
        }
        return instance;
    }


    /**
     * Initialize method for the home controller class
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;


        isShowing = true;


        MainApp.openView("/FXML/Input.fxml");


        initializeExpensesList();

    }

    private void initializeExpensesList() {

        CategoryConnection categoryC = new CategoryConnection();

        ArrayList<SubCategory> subCategories = categoryC.getSubCategoryList();

        for (SubCategory category : subCategories) {

            Label name = new Label(category.getName());
            Label desc = new Label(category.getDescription());
            ImageView imgView = new ImageView(category.getIcon());

            imgView.maxHeight(25);
            imgView.setFitHeight(25);

            imgView.maxWidth(25);
            imgView.setFitWidth(25);



            HBox box = new HBox(imgView, new VBox(name, desc));
            HBox.setHgrow(expensesList, Priority.ALWAYS);

            expensesList.getItems().add(box);

        }




//             expensesList.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<String>() {
//                    public void changed(ObservableValue<? extends String> ov,
//                                        String old_val, String new_val) {
//                        label.setText(new_val);
//                        label.setTextFill(Color.web(new_val));
//                    }
//              });
        expensesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        expensesList.setExpanded(Boolean.TRUE);
    }




    /**
     * Check if the home view is showing
     *
     * @return state
     */
    @Override
    public boolean isShowing(){
        return isShowing;
    }




}
