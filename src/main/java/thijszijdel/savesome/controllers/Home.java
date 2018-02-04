package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.BalanceConnection;
import thijszijdel.savesome.connections.CategoryConnection;
import thijszijdel.savesome.interfaces.State;
import thijszijdel.savesome.models.Category;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable, State {


    private static boolean isShowing = false;

    //Create one instance of this class
    private static Home instance = null;

    @FXML BorderPane mainView;
    @FXML HBox balance;
    /**
     * Getter for the instance of this class
     *
     * @return this
     */
    public static Home getInstance() {
        //check if the instance already is setted
        if (instance == null) {
            synchronized(Home.class) {
                if (instance == null) {
                    instance = new Home();
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
        isShowing = true;
        instance = this;

        CategoryConnection category = new CategoryConnection();
        ArrayList<Category> mainCategoryList = category.getMainCategoryList();
        for (Category cat : mainCategoryList){
            System.out.println(cat.getName()+" "+cat.getDescription());
        }

        initializeBalance();
    }

    private void initializeBalance() {
        BalanceConnection balanceC = new BalanceConnection();
        balance.getChildren().addAll(balanceC.getAllBalanceDisplays());
    }

    /**
     * Check if the home view is shwoing
     *
     * @return state
     */
    @Override
    public boolean isShowing(){
        return isShowing;
    }

    /**
     * Method for opening views based on the buttons name
     * The name will be converted to the normal (/fxml/..) director path.
     *
     * @param e event location
     */
    @FXML
    private void openView(Event e){
        //get the assets
        String button = ((JFXButton)e.getSource()).getText();
        String fileName = button.substring(0, 1).toUpperCase() + button.substring(1).toLowerCase();

        //fileName

        //generate the link to the fxml view
        StringBuilder linkBuilder = new StringBuilder("/fxml/"); //source
        linkBuilder.append( fileName + ".fxml" ); //file



        try {
            setScene(linkBuilder.toString());
            MainApp.openView(linkBuilder.toString());
        } catch(Exception exception){
            MainApp.log(exception);
        }
    }


    public void setScene(String view) throws IOException {
        //parent from MainApp
        Parent fxmlView;

//        if (language.equals("dutch")) {
//            ResourceBundle bundle = ResourceBundle.getBundle("resources.Bundle", new Locale("nl"));
//            fxmlView = FXMLLoader.load(MainApp.class.getResource(view), bundle);
//
//        } else {
//            ResourceBundle bundle = ResourceBundle.getBundle("resources.Bundle");
        fxmlView = FXMLLoader.load(MainApp.class.getResource(view));
//
//        }
        //scene zetten ( in Center van BorderPane )
        //fxmlView.





        mainView.setCenter(fxmlView);




    }




}
