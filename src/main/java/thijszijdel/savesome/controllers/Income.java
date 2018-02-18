package thijszijdel.savesome.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.ui.OverviewChart;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

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

    /**
     * Initializing method for the Income view
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        yAxis.setLabel("Amount");
        xAxis.setLabel("Month");
        xAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("Jan", "Feb", "Mar")));


        incoming.setName("Incoming");
        incoming.getData().add(new XYChart.Data("Jan", 750));
        incoming.getData().add(new XYChart.Data("Feb", 500));
        incoming.getData().add(new XYChart.Data("Mar", 1000));

        outgoing.setName("Outgoing");
        outgoing.getData().add(new XYChart.Data("Jan", -500));
        outgoing.getData().add(new XYChart.Data("Feb", -750));
        outgoing.getData().add(new XYChart.Data("Mar", -500));

        ended.setName("Ended up with");
        ended.getData().add(new XYChart.Data<>("Jan",250));
        ended.getData().add(new XYChart.Data<>("Feb",-250));
        ended.getData().add(new XYChart.Data<>("Mar",500));

        chart.getData().addAll(incoming, outgoing, ended);
        chart.setLegendVisible(false);

        chart.setMaxHeight(300);

        pane.getChildren().clear();

        pane.getChildren().setAll(chart);
    }

    public void setIncome(String income) {
        this.income.setText(income);
    }

    public void setSpent(String spent) {
        this.spent.setText(spent);
    }
}
