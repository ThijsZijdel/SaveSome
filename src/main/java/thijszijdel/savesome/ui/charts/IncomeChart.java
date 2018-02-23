package thijszijdel.savesome.ui.charts;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.controllers.Income;

import java.beans.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class IncomeChart extends StackedBarChart<String, Number> {


    ArrayList<String> dates = new ArrayList<>();

    XYChart.Series<String, Number> incoming = new XYChart.Series<>();
    XYChart.Series<String, Number> outgoing = new XYChart.Series<>();
    XYChart.Series<String, Number> ended = new XYChart.Series<>();




    private Income incomeClass = null;

    private IncomeChart chart = this;

    //public IncomeChart(CategoryAxis xAxis, Axis<Number> yAxis) {  super(xAxis, yAxis); }

    public IncomeChart(CategoryAxis xAxis, Axis<Number> yAxis, Income incomeClass) {
        super(xAxis, yAxis);




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


        xAxis.setCategories(FXCollections.observableArrayList(dates));





        //TODO move chart data etc to here

        // for outputing the results
        this.incomeClass = incomeClass;
    }

    public IncomeChart getChart() {
        return chart;
    }


    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();

        //loop trough the series (incoming / outgoing)
        for (Series<String, Number> series : getData()) {

            //loop trough the data y values
            for (Data<String, Number> data : series.getData()) {
                StackPane bar = (StackPane) data.getNode();

                Text dataText = new Text(data.getYValue() + "");
                dataText.setOpacity(0.2);

                bar.setOnMouseEntered(t ->{
                    dataText.setOpacity(1);
                });
                bar.setOnMouseExited(t ->{
                    dataText.setOpacity(0.2);
                });

                bar.setOnMouseClicked(t -> {

                    if (incomeClass != null)
                        if (series.getName().equals("Incoming"))
                            incomeClass.setIncome("$ "+data.getYValue());
                        if (series.getName().equals("Outgoing"))
                            incomeClass.setSpent("$ "+data.getYValue());

                    MainApp.setAppMessage(series.getName()+" : "+data.getYValue()+" in "+data.getXValue());
                });
                bar.getChildren().add(dataText);

            }
        }
    }
    private void setIncomingData() {
        incoming.setName("Incoming");
//        incoming.getData().add(new XYChart.IData("Jan", 750));
//        incoming.getData().add(new XYChart.IData("Feb", 500));
//        incoming.getData().add(new XYChart.IData("Mar", 1000));
//        incoming.getData().add(new XYChart.IData("Apr", 3000));
//        incoming.getData().add(new XYChart.IData("May", 1750));

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
}