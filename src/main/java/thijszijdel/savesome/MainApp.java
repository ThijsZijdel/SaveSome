package thijszijdel.savesome;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import thijszijdel.savesome.constants.Theme;
import thijszijdel.savesome.controllers.Expenses;
import thijszijdel.savesome.controllers.Home;
import thijszijdel.savesome.database.JDBC;
import thijszijdel.savesome.models.Settings;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Main App class
 */
public class MainApp extends Application {

    public static Settings config = new Settings(Theme.DARK);

    private static JDBC DB;

    public static String language = "english";

    public static Stage mainStage;

    private static final JDBC database = new JDBC();


    private Parent root;
    private Scene mainScene;


    /**
     * Start method for the entire application
     *
     * @param stage stage that starts
     * @throws Exception general exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        
        mainScene = new Scene(root);
        mainScene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Save Some");
        stage.setScene(mainScene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.setMinWidth(1000);
        stage.setMinHeight(700);

        stage.show();

//        Image logo = new Image("Images/logo.png");
//        //Image applicationIcon = new Image(getClass().getResourceAsStream("Images/Logo.png"));
//        stage.getIcons().add(logo);

    }


    /**
     * @return the connection to the declared database
     */
    public static JDBC getConnection(){
        return database;
    }

    /**
     * @return the current language
     */
    public static String getLanguage() {
        return language;
    }


    private Home controllerHome = Home.getInstance();
    private Expenses controllerExpenses = Expenses.getInstance();


    /**
     * For opening new stages / pop ups
     *
     * @param viewLink the link to the fxml file
     * @throws IOException opening new stages
     */
    public static void openView(String viewLink) throws IOException {

        if (isShowing(viewLink)) {
            Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(viewLink));

            Scene scene = new Scene(fxmlView);
            scene.getStylesheets().add("/styles/Styles.css");


            Stage stage = new Stage();


            stage.setTitle("Money Saver");
            stage.setScene(scene);
            stage.show();

        }
    }

    private static boolean isShowing(String viewLink) throws IOException {
            return true;
//        fxmlLoader.load(getClass().getResource(viewLink).openStream());
////
////        System.out.println(p.getClass()+" < classe ");
////        System.out.println(p.getChildren()+" < childs ");
////        System.out.println(p.getId()+" < id ");
////        System.out.println(fxmlLoader.getController()+" < controller !!");
//        //System.out.println(fxmlLoader.getController() instanceof Home );
//        //System.out.println(" < INSTANCE OF HOME  !!");
//        //FooController fooController = (FooController) fxmlLoader.getController();
//        if (fxmlLoader.getController() instanceof State){
//            System.out.println("checking state ........");
//            System.out.println(fxmlLoader.getController().toString());
//
//            System.out.println(((State) fxmlLoader.getController()).isShowing()+" is the state");
//            return ((State) fxmlLoader.getController()).isShowing();
//        } else {
//            return false;
//        }
//
//    }

    }


    /**
     * Method for logging all the exceptions
     * So there will be a option to log them al to a file/ view
     * @param e the exception that occur
     */
    public static void log(Exception e) {
        StringBuilder log = new StringBuilder();
        log.append("\n ------------ LOG ------------- \n");

        if (e instanceof IOException)
            log.append("IO Exception \n");

        if (e instanceof SQLException)
            log.append("SQL Exception \n");

        if (e instanceof NullPointerException)
            log.append("Null pointer Exception \n");

        if (e == null)
            log.append("Undefined Exception \n");

        log.append(e.getMessage());

        log.append("\n ------------ /LOG ------------ \n");

        System.out.println(log.toString());
        e.printStackTrace();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}