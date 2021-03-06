package com.tylerphelps.motormonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by TylerPhelps on 2/28/17.
 */

public class ModuleThumbnailAdapter extends ArrayAdapter<SensorModule> {

    public ModuleThumbnailAdapter(Context context, ArrayList<SensorModule> modules) {
        super(context, R.layout.module_thumb, modules);
    }

    private static class ViewHolder {
        TextView name;
        TextView vibration;
        TextView temperature;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SensorModule module = getItem(position);

        //Create view holder for efficiency to cut down on inflating if reusing same layout
        //DOES NOT ALTERNATE COLORS IN LISTVIEW YET
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.module_thumb, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.moduleNameTextView);
            viewHolder.vibration = (TextView) convertView.findViewById(R.id.vibrationTextView);
            viewHolder.temperature = (TextView) convertView.findViewById(R.id.temperatureTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Fill in view holder
        if(module == null || module.getViewable_name() == null) {
            viewHolder.name.setText("Module Load Error");
            viewHolder.vibration.setText("N/A");
            viewHolder.temperature.setText("--°F");
        } else {
            viewHolder.name.setText(module.getViewable_name());
            viewHolder.vibration.setText("35.5K");
            viewHolder.temperature.setText("106.67°F");
        }

        return convertView;
    }
}
