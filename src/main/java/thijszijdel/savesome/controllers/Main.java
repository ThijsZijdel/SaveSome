package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.interfaces.IRefresh;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Main implements Initializable, IRefresh {

    /**
     * link of the current view
     */
    private String currentView;

    /**
     * Create one instance of this class
     */
    private static Main instance = null;

    /**
     * main application view
     * Location: between header & footer
     */
    @FXML BorderPane mainView;

    /**
     * Balance displays
     */
    @FXML HBox balance;

    /**
     * application information
     * Location: footer
     */
    @FXML TextField appInfo;

    /**
     * Main tool/ nav bar
     * Location: header / top
     */
    @FXML ToolBar toolbar;

    /**
     * Array list of all the buttons in the toolbar
     */
    private ArrayList<JFXButton> buttons = new ArrayList<>();

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


        for (Object obj : toolbar.getItems())
            if (obj instanceof JFXButton)
                buttons.add( (JFXButton) obj );


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
        if (appInfo.getText().trim().length() > 0 && appInfo.getText() != null)
            appInfo.clear();

        appInfo.setText(message);
    }


    /**
     * Set the balance displays on the header
     */
    public void initializeBalance() {
        balance.getChildren().clear();
        balance.getChildren().addAll(MainApp.balanceConnection().getAllBalanceDisplays());
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
                //set the scene
                setMainScene(linkBuilder.toString());
                currentView = fileName;

                //remove the active class from all the buttons
                for (JFXButton butt : buttons)
                    butt.getStyleClass().remove("active-nav");

                //add it to the current button
                ((JFXButton) e.getSource()).getStyleClass().add("active-nav");
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
        try {
            Parent fxmlView = FXMLLoader.load(Main.class.getResource(view));
            mainView.setCenter(fxmlView);
        } catch (LoadException e){
            MainApp.log(e);
        }
//            URL url = getClass().getResource(viewLink);
//            FXMLLoader loader = new FXMLLoader(url);
//            if (loader == null) {
//                throw new RuntimeException("Could not find " + url.toString());
//            }
    }


    /**
     * Main call for the refresh methods
     * Called by: refresh button in the footer
     * @param e on click
     */
    @FXML
    private void refresh(Event e){
        refresh();
    }

    @Override
    public void refresh() {
        MainApp.refresh();
        MainApp.setAppMessage("Application data is reloaded.");
    }
}
