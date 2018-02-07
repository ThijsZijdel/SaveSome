package thijszijdel.savesome.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.database.data.Data;
import thijszijdel.savesome.database.data.ExpensesData;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML PieChart chart;

    @FXML Label chartLabel;

    @FXML AnchorPane topLeft;

    /**
     * Initializing method for the Home view
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
       // XYChart.Series<String, Number> datalist = new XYChart.Series<>();

        Data data = new ExpensesData();

        try {
            ResultSet results = data.connection.executeResultSetQuery("" +
                    "SELECT sum(amount) AS amount, " +
                    "SubCategory.name " +
                        "FROM Expense " +
                            "LEFT JOIN SubCategory " +
                            "ON Expense.subCategoryFk = SubCategory.idSubCategory "+
                        "GROUP BY SubCategory.name");



            while (results.next()){
                double amount = results.getDouble("amount");
                String name = results.getString("SubCategory.name");


                if (amount < 0)
                    amount = amount * -1;

                datalist.add(new PieChart.Data(name, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        datalist.add(new PieChart.Data("jan", 30));
//        datalist.add(new PieChart.Data("feb", 20));
//        datalist.add(new PieChart.Data("mar", 34));
//        datalist.add(new PieChart.Data("apr", 11));


        //chart.setData(datalist);
        chart.getData().clear();

        //chart.setName("Retrieved Luggage 2017");
        //chart.setData(ObservableList);
        chart.setData( datalist);
        chart.setAnimated(true);
        chart.setTitle("Categories");
        //chart.setData(datalist.chartProperty());

        MainApp.setAppMessage("Home screen is loaded.");


        //MainApp.openView("/FXML/Input.fxml");


        setView("/FXML/Input.fxml");
        //MainApp.getInstance().openView("/FXML/Input.fxml", MainApp.getInputStage());

    }

    private void setView(String viewLink) {
        try {

                Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(viewLink));

                Scene scene = new Scene(fxmlView);
                scene.getStylesheets().add("/styles/Styles.css");


               // Stage stage = new Stage();


               // stage.setTitle("TEST");
                //stage.setScene(scene);
               // stage.show();

            Node node;
            node = (Node)fxmlView;

            topLeft.getChildren().add(node);
        } catch (IOException e) {
            MainApp.log(e);
        }
    }


    /**
     * Get the selection of the chart
     *
     * @param event when click chart piece
     */
    @FXML
    private void getChartSelection(MouseEvent event ) {
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> chartLabel.setText(String.valueOf(data.getPieValue()) + "%"));
        }
    }




//    @FXML
//    public void sevteen(ActionEvent event) throws IOException {
//        XYChart.Series<String, Number> seriesfound = new XYChart.Series<>();
//        XYChart.Series<String, Number> serieslost = new XYChart.Series<>();
//        XYChart.Series<String, Number> seriesmatched = new XYChart.Series<>();
//        lineChart.getData().clear();
//        try {
//
//            ResultSet resultSet;
//            ResultSet lostSet;
//            ResultSet retrievedSet;
//
//            resultSet = db.executeResultSetQuery("SELECT count(dateFound) as quantityfound, extract(month FROM dateFound) as months from foundluggage   WHERE extract(year from dateFound) = '2017' GROUP BY extract(month from dateFound)");
//            lostSet = db.executeResultSetQuery("SELECT count(dateLost) as quantitylost, extract(month FROM dateLost) as months from lostluggage WHERE extract(year from dateLost) = '2017'  GROUP BY extract(month from dateLost)");
//            retrievedSet = db.executeResultSetQuery("SELECT count(delivery) as quantityretrieved, extract(month FROM dateMatched) as months from matched   WHERE extract(year from dateMatched) = '2017' GROUP BY extract(month from dateMatched)");
//
//            while (resultSet.next()) {
//                int countfound = resultSet.getInt("quantityfound");
//                int month = resultSet.getInt("months");
//
//                cases(month);
//                seriesfound.getData().add(new XYChart.Data<>(mon, countfound));
//
//            }
//
//            while (lostSet.next()) {
//
//                int month = lostSet.getInt("months");
//                int countlost = lostSet.getInt("quantitylost");
//
//                cases(month);
//
//                serieslost.getData().add(new XYChart.Data<>(mon, countlost));
//            }
//
//            while (retrievedSet.next()) {
//                int month = retrievedSet.getInt("months");
//                int countmatched = retrievedSet.getInt("quantityretrieved");
//
//                cases(month);
//
//                seriesmatched.getData().add(new XYChart.Data<>(mon, countmatched));
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        seriesfound.setName("Found Luggage 2017");
//        serieslost.setName("Lost Luggage 2017");
//        seriesmatched.setName("Retrieved Luggage 2017");
//        lineChart.getData().add(seriesfound);
//        lineChart.getData().add(serieslost);
//        lineChart.getData().add(seriesmatched);
//
//    }



//    public String cases(int month) {
//
//        switch (month) {
//            case 1:
//                mon = "Jan";
//                break;
//            case 2:
//                mon = "Feb";
//                break;
//            case 3:
//                mon = "Mar";
//                break;
//            case 4:
//                mon = "Apr";
//                break;
//            case 5:
//                mon = "Mei";
//                break;
//            case 6:
//                mon = "Jun";
//                break;
//            case 7:
//                mon = "Jul";
//                break;
//            case 8:
//                mon = "Aug";
//                break;
//            case 9:
//                mon = "Sep";
//                break;
//            case 10:
//                mon = "Okt";
//                break;
//            case 11:
//                mon = "Nov";
//                break;
//            case 12:
//                mon = "Dec";
//
//        }
//        return mon;
//    }

}
