package thijszijdel.savesome.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Income implements Initializable {
   final CategoryAxis xAxis = new CategoryAxis();
   final NumberAxis yAxis = new NumberAxis();
    //final StackedBarChart<String, Number> sbc = new StackedBarChart<>(xAxis, yAxis);
    final XYChart.Series<String, Number> incoming = new XYChart.Series<>();
    final XYChart.Series<String, Number> outgoing = new XYChart.Series<>();

    @FXML StackedBarChart incomeChart;
    /**
     * Initializing method for the Income view
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        incomeChart = new StackedBarChart(xAxis, yAxis);

        xAxis.setLabel("Month");

        xAxis.setCategories(FXCollections.observableArrayList(
                Arrays.asList("Jan", "Feb", "Mar")));
        yAxis.setLabel("Value");
        incoming.setName("Incoming");
        incoming.getData().add(new XYChart.Data("Jan", 25601.34));
        incoming.getData().add(new XYChart.Data("Feb2", 20148.82));
        incoming.getData().add(new XYChart.Data("Mar2", 10000));
        outgoing.setName("Outgoing");
        outgoing.getData().add(new XYChart.Data("Jan", -7401.85));
        outgoing.getData().add(new XYChart.Data("Feb2", -1941.19));
        outgoing.getData().add(new XYChart.Data("Mar2", -5263.37));
        //Scene scene = new Scene(sbc, 800, 600);
        //incomeChart.getXAxis().setLabel("Month");
        //incomeChart.getYAxis().setLabel("Amount");

        incomeChart.getData().addAll(incoming, outgoing);
    }
}
