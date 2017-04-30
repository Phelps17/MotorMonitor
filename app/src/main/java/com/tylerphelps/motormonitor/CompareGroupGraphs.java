package com.tylerphelps.motormonitor;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.LegendRenderer;
import android.util.Log;
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
                getSupportActionBar().setTitle("Temperature Analytics");
                drawTempGraph(sm, graph);
                break;
            case "current":
                getSupportActionBar().setTitle("Electric Current Analytics");
                drawCurrentGraph(sm, graph);
                break;
            case "vibration":
                getSupportActionBar().setTitle("Vibration Analytics");
                drawVibrationGraph(sm, graph);
                break;
        }

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Log.e("On Config Change","LANDSCAPE");
        }
        else{
            Log.e("On Config Change","PORTRAIT");
            finish();
        }
    }

    private void drawTempGraph(SensorModule sm, GraphView graph) {
        if (sm.getGroup().equals("")) {
            ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
            for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                graphData.add(new DataPoint(entry.getTime(), entry.getTemperature()));
            }
            DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
            series.setTitle(sm.getViewable_name());
            series.setColor(Color.RED);
            graph.addSeries(series);
        }
        else {
            for (SensorModule module : this.dc.getSensorModules()) {
                if (module.getGroup().equals(sm.getGroup())) {
                    ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
                    for (SensorDataEntry entry : this.dc.getDataFromModule(module)) {
                        graphData.add(new DataPoint(entry.getTime(), entry.getTemperature()));
                    }
                    DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
                    series.setTitle(module.getViewable_name());
                    if (!module.equals(sm)) {
                        series.setColor(Color.GRAY);
                    }
                    else {
                        series.setColor(Color.RED);
                    }
                    graph.addSeries(series);
                }
            }
        }

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private void drawCurrentGraph(SensorModule sm, GraphView graph) {
        if (sm.getGroup().equals("")) {
            ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
            for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                graphData.add(new DataPoint(entry.getTime(), entry.getCurrent()));
            }
            DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
            series.setTitle(sm.getViewable_name());
            series.setColor(Color.RED);
            graph.addSeries(series);
        }
        else {
            for (SensorModule module : this.dc.getSensorModules()) {
                if (module.getGroup().equals(sm.getGroup())) {
                    ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
                    for (SensorDataEntry entry : this.dc.getDataFromModule(module)) {
                        graphData.add(new DataPoint(entry.getTime(), entry.getCurrent()));
                    }
                    DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
                    series.setTitle(module.getViewable_name());
                    if (!module.equals(sm)) {
                        series.setColor(Color.GRAY);
                    }
                    else {
                        series.setColor(Color.RED);
                    }
                    graph.addSeries(series);
                }
            }
        }

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private void drawVibrationGraph(SensorModule sm, GraphView graph) {
        if (sm.getGroup().equals("")) {
            ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
            for (SensorDataEntry entry : this.dc.getDataFromModule(sm)) {
                graphData.add(new DataPoint(entry.getTime(), entry.getVibration()));
            }
            DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
            series.setTitle(sm.getViewable_name());
            series.setColor(Color.RED);
            graph.addSeries(series);
        }
        else {
            for (SensorModule module : this.dc.getSensorModules()) {
                if (module.getGroup().equals(sm.getGroup())) {
                    ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
                    for (SensorDataEntry entry : this.dc.getDataFromModule(module)) {
                        graphData.add(new DataPoint(entry.getTime(), entry.getVibration()));
                    }
                    DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
                    series.setTitle(module.getViewable_name());
                    if (!module.equals(sm)) {
                        series.setColor(Color.GRAY);
                    }
                    else {
                        series.setColor(Color.RED);
                    }
                    graph.addSeries(series);
                }
            }
        }

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}
