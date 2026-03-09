package com.yourapp.controller;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

// Charts / Highcharts related (correct packages)
import org.zkoss.chart.Charts;
import org.zkoss.chart.model.CategoryModel;     // ← was CategoryXYDataModel in older versions
import org.zkoss.chart.model.XYModel;           // ← was XYDataModel
import org.zkoss.chart.model.XYSeries;

// Pie model (these are in ZK core, not Charts)
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;	

import java.util.ArrayList;
import java.util.List;

public class ChartsViewModel {

    private List<Double> values = new ArrayList<>();   // stores all entered values
    private Double newValue = null;                    // bound to textbox

    // Initial sample data
    public ChartsViewModel() {
        values.add(120.0);  // example: orders
        values.add(180.0);
        values.add(95.0);
        values.add(220.0);
    }

    // Getter / Setter for textbox
    public Double getNewValue() { return newValue; }
    public void setNewValue(Double newValue) { this.newValue = newValue; }

    // Command: called when button clicked
    @Command
    @NotifyChange({"lineModel", "pieModel"})
    public void addValue() {
        if (newValue != null && newValue > 0) {
            values.add(newValue);
        }
        newValue = null;  // clear input
    }

    // Line chart model
    public XYDataModel getLineModel() {
        CategoryXYDataModel model = new CategoryXYDataModel();
        XYSeries series = new XYSeries("Entered Values");

        for (int i = 0; i < values.size(); i++) {
            series.add(i + 1, values.get(i));  // x = entry number, y = value
        }

        model.addSeries(series);
        return model;
    }

    // Pie chart model (example: last value vs rest)
    public PieModel getPieModel() {
        PieModel model = new SimplePieModel();

        if (values.isEmpty()) {
            model.add("No Data", 1);
            return model;
        }

        double sum = 0;
        for (Double v : values) sum += v;

        double last = values.get(values.size() - 1);
        double previous = sum - last;

        model.add("Previous Total", previous);
        model.add("Latest Entry", last);

        return model;
    }
}