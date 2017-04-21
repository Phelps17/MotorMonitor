package com.tylerphelps.motormonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;

public class CompareGroupGraphs extends AppCompatActivity {
    private DatabaseController dc;
    private SensorModule module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_group_graphs);

        Intent intent = getIntent();
        String moduleAccessName = intent.getStringExtra("moduleAccessName");
        String graphType = intent.getStringExtra("graphType");

        this.dc = new DatabaseController(this);
        for (SensorModule sm : this.dc.getSensorModules()) {
            if (sm.getAccess_name().equals(moduleAccessName)) {
                this.module = sm;
                break;
            }
        }

        drawGraph(this.module, graphType);
    }

    private void drawGraph(SensorModule sm, String type) {
        GraphView graph = ((GraphView) findViewById(R.id.graph));

        switch (type) {
            case "temp":
                getActionBar().setTitle("Temperature Analytics");
                drawTempGraph(sm, graph);
                break;
            case "current":
                getActionBar().setTitle("Electric Current Analytics");
                drawCurrentGraph(sm, graph);
                break;
            case "vibration":
                getActionBar().setTitle("Vibration Analytics");
                drawVibrationGraph(sm, graph);
                break;
        }

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
    }

    private void drawTempGraph(SensorModule sm, GraphView graph) {
        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        if (sm.getGroup().equals("")) {
            for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                graphData.add(new DataPoint(entry.getDate(), entry.getTemperature()));
            }
            DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
            graph.addSeries(series);
        }
        else {
            for (SensorModule module : this.dc.getSensorModules()) {
                if (module.getGroup().equals(sm.getGroup())) {
                    for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                        graphData.add(new DataPoint(entry.getDate(), entry.getTemperature()));
                    }
                    DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
                    graph.addSeries(series);
                }
            }
        }
    }

    private void drawCurrentGraph(SensorModule sm, GraphView graph) {
        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        if (sm.getGroup().equals("")) {
            for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                graphData.add(new DataPoint(entry.getDate(), entry.getCurrent()));
            }
            DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
            graph.addSeries(series);
        }
        else {
            for (SensorModule module : this.dc.getSensorModules()) {
                if (module.getGroup().equals(sm.getGroup())) {
                    for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                        graphData.add(new DataPoint(entry.getDate(), entry.getCurrent()));
                    }
                    DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
                    graph.addSeries(series);
                }
            }
        }
    }

    private void drawVibrationGraph(SensorModule sm, GraphView graph) {
        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        if (sm.getGroup().equals("")) {
            for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                graphData.add(new DataPoint(entry.getDate(), entry.getVibration()));
            }
            DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
            graph.addSeries(series);
        }
        else {
            for (SensorModule module : this.dc.getSensorModules()) {
                if (module.getGroup().equals(sm.getGroup())) {
                    for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                        graphData.add(new DataPoint(entry.getDate(), entry.getVibration()));
                    }
                    DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
                    graph.addSeries(series);
                }
            }
        }
    }
}
