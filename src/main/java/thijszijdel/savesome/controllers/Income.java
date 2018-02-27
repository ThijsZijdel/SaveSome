package thijszijdel.savesome.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.ui.charts.IncomeChart;

import java.net.URL;
import java.util.*;

public class Income implements Initializable {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    @FXML
    Pane pane;

    @FXML
    Label income, spent;

    IncomeChart incomeChart = new IncomeChart(xAxis, yAxis, this);


     /**
     * Initializing method for the Income view
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



        pane.getChildren().clear();


        // keep this
        pane.getChildren().setAll(incomeChart.getChart());




    }


    public void setIncome(String income) {
        this.income.setText(income);
    }

    public void setSpent(String spent) {
        this.spent.setText(spent);
    }
}
