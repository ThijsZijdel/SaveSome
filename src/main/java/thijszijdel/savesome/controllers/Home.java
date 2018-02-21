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
import thijszijdel.savesome.interfaces.IData;
import thijszijdel.savesome.connections.Expense.ExpensesData;

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


        // TODO:      Move overview cat chart for expenses
        //**     WILL BE MOVED      **/
        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();

        IData data = new ExpensesData();
        int index = 1;
        try {
            ResultSet results = data.connection.executeResultSetQuery("" +
                    "SELECT sum(amount) AS amount, " +
                    "SubCategory.name, SubCategory.color " +
                        "FROM Expense " +
                            "LEFT JOIN SubCategory " +
                            "ON Expense.subCategoryFk = SubCategory.idSubCategory "+
                        "GROUP BY SubCategory.name, SubCategory.color;");

            while (results.next()){

                double amount = results.getDouble("amount");
                String name = results.getString("SubCategory.name");
                String color = results.getString("SubCategory.color");

                if (amount < 0)
                    amount = amount * -1;

                datalist.add(new PieChart.Data(name, amount));
//                chart.setStyle("CHART_COLOR_"+index+" : "+color+";");
//
//                System.out.println("-fx-CHART_COLOR_"+index+" : "+color+";");

                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chart.getData().clear();

        chart.setData( datalist);
        chart.setAnimated(true);
        chart.setTitle("Categories");

        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.RIGHT);





        setUpBillsCalendar();

        // TODO: impliment all views
        MainApp.setAppMessage("Home screen is loaded.");

        setView("/FXML/Expenses.fxml", topLeft);
        setView("/FXML/Income.fxml", topRight);
    }

    private void setUpBillsCalendar() {

        //JFXDatePickerSkin datePickerSkin = new JFXDatePickerSkin(new JFXDatePicker(LocalDate.now()));

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setShowWeekNumbers(false);

        datePicker.setEffect(null);
        datePicker.setStyle("-fx-effect: null;");

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();


        dateBills.getChildren().add(popupContent);
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






    // TODO: possible move these with the chart
    /**
     * Get the selection of the chart
     *
     * @param event when click chart piece
     */
    @FXML
    private void getChartSelection(MouseEvent event ) {
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
