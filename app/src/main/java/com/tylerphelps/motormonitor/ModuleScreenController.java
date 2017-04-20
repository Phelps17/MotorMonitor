package com.tylerphelps.motormonitor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

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
        this.listview.removeAllViewsInLayout();
        ArrayList<Integer> screensNeeded = new ArrayList<Integer>();
        screensNeeded.add(1);
        screensNeeded.add(2);
        screensNeeded.add(3);

        ModuleScreenAdapter adapter = new ModuleScreenAdapter(this.context, screensNeeded);

        //Set listview adapter
        this.listview.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        setUpVibrationAnalyticScreen();
        setUpTempAnalyticScreen();
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

    private void setUpVibrationAnalyticScreen() {

    }

    private void setUpTempAnalyticScreen() {

    }

}