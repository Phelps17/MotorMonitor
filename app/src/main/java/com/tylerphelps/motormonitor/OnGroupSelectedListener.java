package com.tylerphelps.motormonitor;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import android.widget.Spinner;

/**
 * Created by TylerPhelps on 4/20/17.
 */

public class OnGroupSelectedListener implements OnItemSelectedListener {
    private SensorModule module;
    private DatabaseController dc;
    private MainActivity myParent;

    public void setUpAdapter(DatabaseController dc, SensorModule module, MainActivity parent) {
        this.module = module;
        this.dc = dc;
        this.myParent = parent;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        System.out.println("Item selected");

        if (pos == 0) {
            setNewGroup("");
            return;
        }
        else if (parent.getItemAtPosition(pos).toString().equals("Create New Group")){
            createNewGroup();
            return;
        }
        else {
            setNewGroup(parent.getItemAtPosition(pos).toString());
            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void setNewGroup(String groupName) {
        module.setGroup(groupName);
        dc.updateSensorModule(module);
    }

    private void createNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.myParent);
        builder.setTitle("New Group Name:");
        final EditText input = new EditText(this.myParent);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setNewGroup(input.getText().toString());
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
}
