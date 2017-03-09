package com.tylerphelps.motormonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Random;

/**
 * Created by TylerPhelps on 3/4/17.
 */

public class ModuleScreenAdapter extends ArrayAdapter<Integer> {

    public ModuleScreenAdapter(Context context, ArrayList<Integer> modules) {
        super(context, R.layout.module_main_view, modules);
    }

    private static class ViewHolder {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Integer module = getItem(position);

        //Create view holder for efficiency to cut down on inflating if reusing same layout
        //DOES NOT ALTERNATE COLORS IN LISTVIEW YET
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.module_main_view, parent, false);
            convertView.setMinimumHeight(parent.getMeasuredHeight());

            GraphView graph = (GraphView) convertView.findViewById(R.id.graph);
            Random random = new Random();

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, random.nextInt(11)),
                    new DataPoint(1, random.nextInt(11)),
                    new DataPoint(2, random.nextInt(11)),
                    new DataPoint(3, random.nextInt(11)),
                    new DataPoint(4, random.nextInt(11)),
                    new DataPoint(5, random.nextInt(11)),
                    new DataPoint(6, random.nextInt(11)),
                    new DataPoint(7, random.nextInt(11)),
                    new DataPoint(8, random.nextInt(11)),
                    new DataPoint(9, random.nextInt(11)),
                    new DataPoint(10, random.nextInt(11))
            });
            graph.addSeries(series);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
