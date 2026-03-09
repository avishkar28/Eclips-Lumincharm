package com.yourapp.controller;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.chart.Charts;
import org.zkoss.chart.PieChart;
import org.zkoss.chart.LineChart;
import org.zkoss.chart.data.CategoryXYDataModel;
import org.zkoss.chart.data.xy.XYDataModel;
import org.zkoss.chart.data.xy.XYSeries;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChartsViewModel {

    private List<Double> values = new ArrayList<>();  // List to store entered values for graphs
    private Double newValue = 0.0;  // Bound to textbox

    // Constructor: Initialize with sample data
    public ChartsViewModel() {
        // Add some sample values for initial charts
        values.add(30.0);
        values.add(50.0);
        values.add(70.0);
        values.add(40.0);
    }

    // Getter/Setter for newValue (bound to textbox)
    public Double getNewValue() { return newValue; }
    public void setNewValue(Double newValue) { this.newValue = newValue; }

    // Command to update charts when button is clicked
    @Command
    @NotifyChange({"lineModel", "pieModel"})  // Auto-refresh charts
    public void updateCharts() {
        if (newValue != null && newValue > 0) {
            values.add(newValue);  // Add new value to list
            System.out.println("Added value: " + newValue + " | Total values: " + values.size());  // Console log
        }
        newValue = 0.0;  // Reset textbox
    }

    // Line Graph Model (updates real-time as values list changes)
    public LineChart getLineModel() {
        LineChart lineChart = Charts.lineChart();
        lineChart.setTitle("Value Trends");

        // Create data model from values list
        XYDataModel dataModel = new CategoryXYDataModel();
        XYSeries series = new XYSeries("Real-Time Values");
        for (int i = 0; i < values.size(); i++) {
            series.add(i, values.get(i));  // X = index (time), Y = value
        }
        dataModel.addSeries(series);

        lineChart.setModel(dataModel);
        lineChart.getYAxis().setTitle("Value");
        lineChart.getXAxis().setTitle("Time/Index");
        return lineChart;
    }

    // Pie Chart Model (updates real-time, e.g., split total into fixed slices + new value)
    public PieChart getPieModel() {
        PieChart pieChart = Charts.pieChart();
        pieChart.setTitle("Value Distribution");

        // Simple example: Pie slices for "Fixed" (sum of old values) and "New" (latest value)
        double totalOld = 0.0;
        for (int i = 0; i < values.size() - 1; i++) {
            totalOld += values.get(i);
        }
        double latest = values.isEmpty() ? 0.0 : values.get(values.size() - 1);

        PieModel model = new SimplePieModel();
        model.add("Previous Total", totalOld);
        model.add("Latest Value", latest);

        pieChart.setModel(model);
        return pieChart;
    }
}