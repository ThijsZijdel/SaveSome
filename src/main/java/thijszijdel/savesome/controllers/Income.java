package thijszijdel.savesome.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.ui.charts.OverviewChart;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Income implements Initializable {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    OverviewChart chart = new OverviewChart(xAxis, yAxis, this);

    XYChart.Series<String, Number> incoming = new XYChart.Series<>();
    XYChart.Series<String, Number> outgoing = new XYChart.Series<>();
    XYChart.Series<String, Number> ended = new XYChart.Series<>();
    @FXML
    Pane pane;

    @FXML
    Label income, spent;

    ArrayList<String> dates = new ArrayList<>();
    /**
     * Initializing method for the Income view
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dates.addAll(Arrays.asList("Jan", "Feb", "Mar", "Apr", "May"));

        // TODO: make data dynamic
        // TODO: set the entire chart in the overviewChart class
        // TODO: move stacked bar chart to separate gui class
        //yAxis.setLabel("Amount");
        //xAxis.setLabel("Month");


        setIncomingData();

        outgoing.setName("Outgoing");
        outgoing.getData().add(new XYChart.Data("Jan", -500));
        outgoing.getData().add(new XYChart.Data("Feb", -750));
        outgoing.getData().add(new XYChart.Data("Mar", -500));
        outgoing.getData().add(new XYChart.Data("Apr", -700));
        outgoing.getData().add(new XYChart.Data("May", -800));

        ended.setName("Ended up with");
        ended.getData().add(new XYChart.Data<>("Jan",250));
        ended.getData().add(new XYChart.Data<>("Feb",-250));
        ended.getData().add(new XYChart.Data<>("Mar",500));

        chart.getData().addAll(incoming, outgoing, ended);
        chart.setLegendVisible(false);

        chart.setMaxHeight(300);

        pane.getChildren().clear();


        // keep this
        pane.getChildren().setAll(chart);


        xAxis.setCategories(FXCollections.observableArrayList(dates));

    }

    private void setIncomingData() {
        incoming.setName("Incoming");
//        incoming.getData().add(new XYChart.Data("Jan", 750));
//        incoming.getData().add(new XYChart.Data("Feb", 500));
//        incoming.getData().add(new XYChart.Data("Mar", 1000));
//        incoming.getData().add(new XYChart.Data("Apr", 3000));
//        incoming.getData().add(new XYChart.Data("May", 1750));

        try {
            ResultSet results = MainApp.getConnection().executeResultSetQuery("" +
                    " SELECT sum(amount) AS amount, " +
                    " monthFK " +
                    " FROM Income  " +
                    " GROUP BY monthFk "+
                    " ORDER BY monthFk;");

            while (results.next()){

                double amount = results.getDouble("amount");
                int monthFk = results.getInt("monthFk");


//                cal.setTime(date);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                int year = cal.get(Calendar.YEAR);

                String month = switchOver(monthFk);


                boolean unique = true;
                for (String dateN: dates) {
                    if (dateN.equals(month))
                        unique = false;
                }
                if (unique)
                    dates.add(month);

//                if (amount < 0)
//                    amount = amount * -1;

                incoming.getData().add(new XYChart.Data(month, amount));
//                chart.setStyle("CHART_COLOR_"+index+" : "+color+";");
//                System.out.println("-fx-CHART_COLOR_"+index+" : "+color+";");

                //index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String switchOver(int month) {
        String monthString;
        switch (month) {
            case 1:  monthString = "Jan";
                break;
            case 2:  monthString = "Feb";
                break;
            case 3:  monthString = "Mar";
                break;
            case 4:  monthString = "Apr";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "Jun";
                break;
            case 7:  monthString = "Jul";
                break;
            case 8:  monthString = "Aug";
                break;
            case 9:  monthString = "Sep";
                break;
            case 10: monthString = "Oct";
                break;
            case 11: monthString = "Nov";
                break;
            case 12: monthString = "Dec";
                break;
            default: monthString = "Invalid month";
                break;
        }
        System.out.println("MONTH IS:" +month +" stirng>"+monthString);
        return monthString;
    }

    public void setIncome(String income) {
        this.income.setText(income);
    }

    public void setSpent(String spent) {
        this.spent.setText(spent);
    }
}
