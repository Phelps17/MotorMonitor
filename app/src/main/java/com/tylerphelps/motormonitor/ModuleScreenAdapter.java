package com.tylerphelps.motormonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Random;

/**
 * Created by TylerPhelps on 3/4/17.
 */

public class ModuleScreenAdapter extends ArrayAdapter<Integer> {
    int curScreenIndex;

    public ModuleScreenAdapter(Context context, ArrayList<Integer> modules) {
        super(context, R.layout.module_main_view, modules);
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
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.module_vibration_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.module_temperature_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
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
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.module_vibration_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.module_temperature_screen, parent, false);
                    convertView.setMinimumHeight(parent.getMeasuredHeight());
                    break;
                default:
                    break;
            }

            convertView.setTag(viewHolder);
        }

        return convertView;
    }
}
