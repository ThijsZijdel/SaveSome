package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import thijszijdel.savesome.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    private String currentView;

    //Create one instance of this class
    private static Main instance = null;

    @FXML BorderPane mainView;
    @FXML HBox balance;

    @FXML TextField appInfo;


    /**
     * Initialize method for the home controller class
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

        try {
            setMainScene("/fxml/Home.fxml");
        } catch (IOException e) {
            MainApp.log(e);
        }




        initializeBalance();
    }


    /**
     * Getter for the instance of this class
     *
     * @return this
     */
    public static Main getInstance() {
        //check if the instance already is set
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
     * Set the app information alert message
     * @param message
     */
    @FXML
    public void setAppInfo(String message){
        appInfo.clear();
        appInfo.setText(message);
    }


    /**
     * Set the balance displays on the header
     */
    public void initializeBalance() {
        balance.getChildren().clear();
        balance.getChildren().addAll(MainApp.getBalanceConnection().getAllBalanceDisplays());
    }



    /**
     * Method for opening views based on the buttons name      (Navigation Bar!)
     * The name will be converted to the normal (/fxml/..) director path.
     *
     * @param e event location
     */
    @FXML
    private void headerMainLink(Event e){
        //get the assets
        String button = ((JFXButton)e.getSource()).getText();
        String fileName = button.substring(0, 1).toUpperCase() + button.substring(1).toLowerCase();

        //generate the link to the fxml view
        StringBuilder linkBuilder = new StringBuilder("/fxml/"); //source
        linkBuilder.append( fileName + ".fxml" ); //file

        if (!fileName.equals(currentView)){

            try {
                setMainScene(linkBuilder.toString());
                currentView = fileName;
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
    public void setMainScene(String view) throws IOException {
        Parent fxmlView = FXMLLoader.load(Main.class.getResource(view));
        mainView.setCenter(fxmlView);
    }


    @FXML
    private void refresh(Event e){
        MainApp.refresh();
        MainApp.setAppMessage("Balances are reloaded.");
    }

}
