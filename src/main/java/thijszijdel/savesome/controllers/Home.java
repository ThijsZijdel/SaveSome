package thijszijdel.savesome.controllers;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.connections.Expense.ExpenseConnection;
import thijszijdel.savesome.interfaces.IData;
import thijszijdel.savesome.connections.Expense.ExpensesData;
import thijszijdel.savesome.ui.displays.BillsCalendar;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML PieChart chart;

    @FXML Label chartLabel;

    @FXML AnchorPane topLeft,topRight;

    @FXML
    Pane dateBills;
    /**
     * Initializing method for the Home view
     *
     * @param url link
     * @param rb  resources
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



        setupCategoryChart();
        setUpBillsCalendar();

        // TODO: impliment all views
        MainApp.setAppMessage("Home screen is loaded.");

        setView("/FXML/Expenses.fxml", topLeft);
        setView("/FXML/Income.fxml", topRight);
    }

    private void setupCategoryChart() {
        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();

        ExpenseConnection data = MainApp.getExpenseConnection();

        datalist.addAll(data.getExpensesSumMonth(1));

        chart.getData().clear();

        chart.setData( datalist);
        chart.setAnimated(true);
        chart.setTitle("Categories");

        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.RIGHT);
    }

    private void setUpBillsCalendar() {
        BillsCalendar cal = new BillsCalendar();

        dateBills.getChildren().add(cal.getCalendarNode());
    }

    /**
     * Set a view on the top left side of the home screen
     * Note: try catch for io exceptions
     *
     * @param viewLink FXML link to the view
     */
    private void setView(String viewLink, AnchorPane location) {
        try {
            Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(viewLink));

            Scene scene = new Scene(fxmlView);
            scene.getStylesheets().add("/styles/Styles.css");

            Node node;
            node = (Node)fxmlView;

            location.getChildren().add(node);
        } catch (IOException e) {
            MainApp.log(e);
        }
    }


    /**
     * Get the selection of the incomeChart
     *
     * @param event when click incomeChart piece
     */
    @FXML
    private void getChartSelection(MouseEvent event ) {
        //TODO move incomeChart selection to incomeChart class
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> chartSelected(data));
        }
    }
    private void chartSelected(PieChart.Data data) {
        chartLabel.setText(String.valueOf(data.getPieValue()) + "%");
        MainApp.setAppMessage(String.valueOf(data.getPieValue()) + "% spend on "+data.getName());

    }

}
