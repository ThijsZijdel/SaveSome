package thijszijdel.savesome;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import thijszijdel.savesome.connections.*;
import thijszijdel.savesome.constants.Theme;
import thijszijdel.savesome.controllers.Expenses;
import thijszijdel.savesome.controllers.Main;
import thijszijdel.savesome.database.JDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Main App class
 */
public class MainApp extends Application {

    public static Settings config = new Settings(Theme.DARK);

    public static String language = "english";

    public static Stage mainStage;

    private static final JDBC database = new JDBC();

    private static final Stage inputStage = new Stage();

    public static Stage getInputStage() {
        return inputStage;
    }


    private Parent root;
    private Scene mainScene;

    private static Main controllerHome = Main.getInstance();
    private static Expenses controllerExpenses = Expenses.getInstance();


    public static long timeRate = 100000000; // ms

    public final static String APP_NAME = "Save Some";
    public final static Image logo = new Image("Images/Logo.png");


    /**
     * Start method for the entire application
     *
     * @param stage stage that starts
     * @throws Exception general exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));

        mainScene = new Scene(root);
        mainScene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle(APP_NAME);
        stage.setScene(mainScene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.setMinWidth(1000);
        stage.setMinHeight(700);

        stage.show();


        //Image applicationIcon = new Image(getClass().getResourceAsStream("Images/Logo.png"));
        stage.getIcons().add(logo);


        startRefreshTimer();
        instance = this;
    }


    /**
     * @return the connection to the declared database
     */
    public static JDBC getConnection(){ return database; }

    /**
     * @return the current language
     */
    public static String getLanguage() {
        return language;
    }

    /**
     * Refresh timer setup
     */
    private static void startRefreshTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                refresh();
            }
        }, 0, MainApp.timeRate);
    }

    /**
     * Actual refresh method
     */
    public static void refresh() {
        System.out.println("- Automatic Refresh -");
        getBalanceConnection().refreshConnection();
        getCategoryConnection().refreshConnection();
        getExpenseConnection().refreshConnection();
        System.out.println("-                   -");
    }

    /**
     * Method for setting the application message in the footer
     *
     * @param message the content /alert
     */
    public static void setAppMessage(String message){
        Main.getInstance().setAppInfo(message);
    }


    /**
     * Connection methods
     */
    private static BalanceConnection balanceConnection = new BalanceConnection();
    public static BalanceConnection getBalanceConnection() {
        return balanceConnection;
    }

    private static final CategoryConnection categoryConnection = new CategoryConnection();
    public static CategoryConnection getCategoryConnection() {
        return categoryConnection;
    }

    private static final ExpenseConnection expenseConnection = new ExpenseConnection();
    public static ExpenseConnection getExpenseConnection() {
        return expenseConnection;
    }

    /**
     * For opening new stages / pop ups
     *
     * @param viewLink the link to the fxml file
     */
    public void openView(String viewLink, Stage stage) {
        try {
            Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(viewLink));

            Scene scene = new Scene(fxmlView);
            scene.getStylesheets().add("/styles/Styles.css");



            stage.getIcons().add(logo);
            stage.setTitle(APP_NAME);
            stage.setAlwaysOnTop(true);
            stage.setScene(scene);
            stage.show();



        } catch (IOException e) {
            log(e);
        }
    }

//    /**
//     * Possible implementation for checking if a view is showing.
//     * @param viewLink
//     * @return
//     * @throws IOException
//     */
//    private boolean isShowing(String viewLink) throws IOException {
//
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewLink));
//        Parent root = loader.load();
//
//        //Expense d = loader.getController();
//        Input c = loader.getController();
//        //System.out.println(d +" < D");
//        System.out.println(c +" < C");
//
//        //FXMLLoader loader = FXMLLoader
//        //.load(MainApp.class.getResource(viewLink));
//
//        if (loader.getController() instanceof State)
//            return ((State) loader.getController()).isShowing();
//
//
//        System.out.println("not a instance of state!");
//        return false;
////        fxmlLoader.load(getClass().getResource(viewLink).openStream());
//////
//////        System.out.println(p.getClass()+" < classe ");
//////        System.out.println(p.getChildren()+" < childs ");
//////        System.out.println(p.getId()+" < id ");
//////        System.out.println(fxmlLoader.getController()+" < controller !!");
////        //System.out.println(fxmlLoader.getController() instanceof Home );
////        //System.out.println(" < INSTANCE OF HOME  !!");
////        //FooController fooController = (FooController) fxmlLoader.getController();
////        if (fxmlLoader.getController() instanceof State){
////            System.out.println("checking state ........");
////            System.out.println(fxmlLoader.getController().toString());
////
////            System.out.println(((State) fxmlLoader.getController()).isShowing()+" is the state");
////            return ((State) fxmlLoader.getController()).isShowing();
////        } else {
////            return false;
////        }
////
////    }
//
//    }


    /**
     * Method for logging all the exceptions
     * So there will be a option to log them al to a file/ view
     * @param e the exception that occur
     */
    public static void log(Exception e) {
        StringBuilder log = new StringBuilder("\n\n");

        if (e instanceof IOException)
            log.append("IO Exception \n");
            setAppMessage("IO Exception");

        if (e instanceof SQLException)
            log.append("SQL Exception \n");
            setAppMessage("SQL Exception");

        if (e instanceof NullPointerException)
            log.append("Null pointer Exception \n");
            setAppMessage("Null pointer Exception");

        if (e == null)
            log.append("Undefined Exception \n");
            setAppMessage("Undefined Exception");

        log.append(e.getMessage());

        log.append("\n ------------ /LOG ------------ \n");

        System.out.println(log.toString());
        e.printStackTrace();

    }


    //Create one instance of this class
    private static MainApp instance = null;

    /**
     * Getter for the instance of this class
     *
     * @return this
     */
    public static MainApp getInstance() {
        //check if the instance already is set
        if (instance == null) {
            synchronized(MainApp.class) {
                if (instance == null) {
                    instance = new MainApp();
                }
            }
        }
        return instance;
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
