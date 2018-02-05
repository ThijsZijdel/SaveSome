package thijszijdel.savesome.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML PieChart chart;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
       // XYChart.Series<String, Number> datalist = new XYChart.Series<>();

        String test = "jan";
        String test2 = "feb";
        datalist.add(new PieChart.Data(test, 99));
        datalist.add(new PieChart.Data(test, 20));
        datalist.add(new PieChart.Data(test2, 34));
        datalist.add(new PieChart.Data(test2, 11));


        //chart.setData(datalist);
        chart.getData().clear();

        //chart.setName("Retrieved Luggage 2017");
        //chart.setData(ObservableList);
        chart.setData( datalist);
        chart.setAnimated(true);
        chart.setTitle("Categories");
        //chart.setData(datalist.chartProperty());








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
