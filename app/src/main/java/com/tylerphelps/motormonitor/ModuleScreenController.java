package com.tylerphelps.motormonitor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by TylerPhelps on 3/19/17.
 */

public class ModuleScreenController {
    ListView listview;
    SensorModule module;
    Context context;

    ModuleScreenController(ListView listView, SensorModule module, Context context) {
        this.listview = listView;
        this.module = module;
        this.context = context;
    }

    private static class ViewHolder {}

    private void clearListView() {
        this.listview.removeAllViewsInLayout();
    }

    private void populateListView() {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = null;

        setUpVibrationAnalyticScreen(convertView, inflater, viewHolder);
        setUpTempAnalyticScreen(convertView, inflater, viewHolder);
    }

    public void updateListView() {
        clearListView();

        try {
            populateListView();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void setUpVibrationAnalyticScreen(View convertView,
                                              LayoutInflater inflater, ViewHolder viewHolder) {

        convertView = inflater.inflate(R.layout.module_vibration_screen, this.listview, false);
        convertView.setMinimumHeight(this.listview.getMeasuredHeight());
        convertView.setTag(viewHolder);
        System.out.println("Vibration up");

    }

    private void setUpTempAnalyticScreen(View convertView,
                                         LayoutInflater inflater, ViewHolder viewHolder) {

        convertView = inflater.inflate(R.layout.module_temperature_screen, this.listview, false);
        convertView.setMinimumHeight(this.listview.getMeasuredHeight());
        convertView.setTag(viewHolder);
        System.out.println("Temp up");

    }

}