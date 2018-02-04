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

public class Main implements Initializable, State {


    private boolean isShowing = false;

    //Create one instance of this class
    private static Main instance = null;

    @FXML BorderPane mainView;
    @FXML HBox balance;
    /**
     * Getter for the instance of this class
     *
     * @return this
     */
    public static Main getInstance() {
        //check if the instance already is setted
        if (instance == null) {
            synchronized(Main.class) {
                if (instance == null) {
                    instance = new Main();
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

        try {
            setScene("/fxml/Home.fxml");
        } catch (IOException e) {
            MainApp.log(e);
        }

        // Test the categories              //temp.
        CategoryConnection category = new CategoryConnection();
        ArrayList<Category> mainCategoryList = category.getMainCategoryList();
        for (Category cat : mainCategoryList){
            System.out.println(cat.getName()+" "+cat.getDescription());
        }




        initializeBalance();
    }

    /**
     * Set the balance displays on the header
     */
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
     * Sets the home screen and sets the isShowing state
     */
    public void setHomeState(boolean state){
        this.isShowing = state;
    }


    private String currentView;
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

        //generate the link to the fxml view
        StringBuilder linkBuilder = new StringBuilder("/fxml/"); //source
        linkBuilder.append( fileName + ".fxml" ); //file

        if (!fileName.equals(currentView)){

            try {
                setScene(linkBuilder.toString());
                currentView = fileName;
                //MainApp.openView(linkBuilder.toString());
            } catch (Exception exception) {
                MainApp.log(exception);
            }
        }
    }

    /**
     * Set the center view on the home page
     *
     * @param view that will be set on it
     * @throws IOException handling fxml files
     */
    public void setScene(String view) throws IOException {
        Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(view));
        mainView.setCenter(fxmlView);
    }




}
