package thijszijdel.savesome.ui.charts;

import javafx.event.ActionEvent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import thijszijdel.savesome.MainApp;
import thijszijdel.savesome.controllers.Income;

import java.beans.EventHandler;

public class OverviewChart extends StackedBarChart<String, Number> {

    private Income incomeClass = null;

    public OverviewChart(CategoryAxis xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
    }
    public OverviewChart(CategoryAxis xAxis, Axis<Number> yAxis, Income incomeClass) {
        super(xAxis, yAxis);

        //TODO move chart data etc to here

        // for outputing the results
        this.incomeClass = incomeClass;
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
}