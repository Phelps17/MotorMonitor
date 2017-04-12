package com.tylerphelps.motormonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.widget.Spinner;

/**
 * Created by TylerPhelps on 3/4/17.
 */

public class ModuleScreenAdapter extends ArrayAdapter<Integer> {
    SensorModule module;
    DatabaseController dc;
    double avg_temp, vibrations;
    ArrayList<SensorDataEntry> data;
    NumberFormat formatter = new DecimalFormat("#0.00");

    public ModuleScreenAdapter(Context context, ArrayList<Integer> modules) {
        super(context, R.layout.module_main_view, modules);
    }

    public void setUpAdapter(DatabaseController dc, SensorModule module) {
        this.module = module;
        this.dc = dc;
        this.data = new ArrayList(this.dc.getDataFromModule(module));
        int count = 0;

        for (SensorDataEntry entry : this.data) {
            count++;
            this.avg_temp += entry.getTemperature();
            this.vibrations += entry.getVibration();
        }
        this.avg_temp = this.avg_temp / count;
    }

    private static class ViewHolder {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Create view holder for efficiency to cut down on inflating if reusing same layout
        //DOES NOT ALTERNATE COLORS IN LISTVIEW YET
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            switch (position) {
                case 0:
                    convertView = inflater.inflate(R.layout.module_main_view, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    setupSensorDetails(convertView);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.module_vibration_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    populateVibrationGraph(convertView);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.module_temperature_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    populateTemperatureGraph(convertView);
                    break;
                default:
                    break;
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            switch (position) {
                case 0:
                    convertView = inflater.inflate(R.layout.module_main_view, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    setupSensorDetails(convertView);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.module_vibration_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    populateVibrationGraph(convertView);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.module_temperature_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    populateTemperatureGraph(convertView);
                    break;
                default:
                    break;
            }

            convertView.setTag(viewHolder);
        }

        return convertView;
    }

    private void populateVibrationGraph(View convertView) {
        ((TextView) convertView.findViewById(R.id.vibrationTextView)).setText(formatter.format(this.vibrations/1000)+"k Vibrations");

        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        for (SensorDataEntry entry : this.data) {
            graphData.add(new DataPoint(entry.getTime(), entry.getVibration()));
        }
        DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);

        GraphView graph = ((GraphView) convertView.findViewById(R.id.graph));
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
    }

    private void populateTemperatureGraph(View convertView) {
        ((TextView) convertView.findViewById(R.id.temperatureTextView)).setText(formatter.format(this.vibrations/1000)+"k Vibrations");

        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        for (SensorDataEntry entry : this.data) {
            graphData.add(new DataPoint(entry.getTime(), entry.getTemperature()));
            //System.out.println("Temp Data: (" + entry.getTime() + ", " + entry.getTemperature() + ")");
        }
        DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);

        GraphView graph = ((GraphView) convertView.findViewById(R.id.graph));
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
    }

    private void setupSensorDetails(View convertView) {
        convertView.setFocusable(true);
        convertView.setFocusableInTouchMode(true);

        EditText nameBox = ((EditText) convertView.findViewById(R.id.nameEditText));
        nameBox.setText(this.module.getViewable_name());

        List<String> groups = new ArrayList<String>();
        groups.add("");
        groups.add("Create New Group");
        Spinner groupSpinner = ((Spinner) convertView.findViewById(R.id.groupDropDown));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, groups);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(dataAdapter);

        EditText detailsBox = ((EditText) convertView.findViewById(R.id.notesEditText));
        detailsBox.setText(this.module.getDetails());
    }
}
