package com.tylerphelps.motormonitor;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
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
import android.widget.Toast;
import android.widget.Button;

/**
 * Created by TylerPhelps on 3/4/17.
 */

public class ModuleScreenAdapter extends ArrayAdapter<Integer> {
    private SensorModule module;
    private DatabaseController dc;
    private MainActivity parent;
    private double avg_temp, vibrations, avg_current;
    private ArrayList<SensorDataEntry> data;
    private NumberFormat formatter = new DecimalFormat("#0.00");

    public ModuleScreenAdapter(Context context, ArrayList<Integer> modules) {
        super(context, R.layout.module_main_view, modules);
    }

    public void setUpAdapter(DatabaseController dc, SensorModule module, MainActivity parent) {
        this.module = module;
        this.dc = dc;
        this.data = new ArrayList(this.dc.getDataFromModule(module));
        this.parent = parent;
        int count = 0;

        for (SensorDataEntry entry : this.data) {
            count++;
            this.avg_temp += entry.getTemperature();
            this.avg_current += entry.getCurrent();
            this.vibrations += entry.getVibration();
        }
        this.avg_temp = this.avg_temp / count;
        this.avg_current = this.avg_current / count;
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
                case 3:
                    convertView = inflater.inflate(R.layout.module_electric_current_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    populateElectricCurrentGraph(convertView);
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
                case 3:
                    convertView = inflater.inflate(R.layout.module_electric_current_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    populateElectricCurrentGraph(convertView);
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
        ((TextView) convertView.findViewById(R.id.temperatureTextView)).setText("Average Temperature: " +
                formatter.format(this.avg_temp)+"°F");

        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        for (SensorDataEntry entry : this.data) {
            graphData.add(new DataPoint(entry.getTime(), entry.getTemperature()));
        }
        DataPoint [] graphDataArray = graphData.toArray(new DataPoint[graphData.size()]);

        GraphView graph = ((GraphView) convertView.findViewById(R.id.graph));
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(graphDataArray);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
    }

    private void populateElectricCurrentGraph(View convertView) {
        ((TextView) convertView.findViewById(R.id.electricCurrentTextView)).setText("Average Current: " +
                formatter.format(this.avg_current)+"A");

        ArrayList<DataPoint> graphData = new ArrayList<DataPoint>();
        for (SensorDataEntry entry : this.data) {
            graphData.add(new DataPoint(entry.getTime(), entry.getCurrent()));
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

        final SensorModule module = this.module;
        final DatabaseController dc = this.dc;
        final MainActivity myParent = this.parent;

        Spinner groupSpinner = ((Spinner) convertView.findViewById(R.id.groupDropDown));
        setUpGroupDropdown(groupSpinner);

        EditText nameBox = ((EditText) convertView.findViewById(R.id.nameEditText));
        nameBox.setText(this.module.getViewable_name());
        nameBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSensorName(module, dc, myParent);
            }
        });

        EditText detailsBox = ((EditText) convertView.findViewById(R.id.notesEditText));
        detailsBox.setText(this.module.getDetails());
        detailsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSensorNotes(module, dc, myParent);
            }
        });

        Button deleteButton = ((Button) convertView.findViewById(R.id.deleteButton));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSensor(module, dc, myParent);
            }
        });
    }

    private void setUpGroupDropdown(Spinner spinner) {
        List<String> groups = new ArrayList<String>();
        groups.add("No Group");
        groups.add("Create New Group");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, groups);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void changeSensorName(final SensorModule module, final DatabaseController dc, final MainActivity parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Edit Sensor Module Name:");
        final EditText input = new EditText(this.getContext());
        input.setText(module.getViewable_name());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                module.setViewable_name(input.getText().toString());
                dc.updateSensorModule(module);
                parent.refreshScreens(module);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void changeSensorNotes(final SensorModule module, final DatabaseController dc, final MainActivity parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Edit Sensor Notes:");
        final EditText input = new EditText(this.getContext());
        input.setText(module.getDetails());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                module.setDetails(input.getText().toString());
                dc.updateSensorModule(module);
                parent.refreshScreens(module);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void deleteSensor(final SensorModule module, final DatabaseController dc, final MainActivity parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Delete Sensor Module?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dc.deleteSensorModule(module);
                parent.refreshScreens(0);
            }
        });
        builder.show();
    }
}
