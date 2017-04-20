package com.tylerphelps.motormonitor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;
import java.security.acl.Group;
import java.util.ArrayList;
import android.content.Intent;
import android.text.InputType;
import android.content.DialogInterface;

import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.tylerphelps.motormonitor.barcode.BarcodeCaptureActivity;
import android.widget.ListView;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.view.ViewGroup;
import android.view.MotionEvent;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseController dc;
    private String m_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.dc = new DatabaseController(this);
        this.m_Text = "";

        /*this.dc.addSensorModule(new SensorModule(this.dc.getNextSensorModuleId(), "65ft3vr3hfue3", "12345", "Main Valve 1", "No notes saved."));
        this.dc.addSensorModule(new SensorModule(this.dc.getNextSensorModuleId(), "65fadasdafue3", "12345", "Main Valve 2", "No notes saved."));
        this.dc.addSensorModule(new SensorModule(this.dc.getNextSensorModuleId(), "65ft3vrasddqf", "12345", "Overflow Valve", "No notes saved."));
        this.dc.addSensorModule(new SensorModule(this.dc.getNextSensorModuleId(), "76b733hfe0ue3", "12345", "Front Office AC", "No notes saved."));

        for (SensorModule module : dc.getSensorModules()) {
            for (int i = 0; i < 50; i++) {
                double vibration = 75 + Math.random() * 50;
                double temperature = 85 + Math.random()*20;
                double current = 150 + Math.random()*10;
                SensorDataEntry data = new SensorDataEntry((long) 0, module.getAccess_name(), new Date(), (double) i, vibration, temperature, current);
                data.setId(this.dc.getNextDataEntryId());
                this.dc.addDataEntry(data);
            }
        }*/

        refreshScreens(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //nothing
        } else if (id == R.id.nav_add_module) {
            addNewModule();
        } else if (id == R.id.nav_send) {
            sendFeedbackEmail();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showThumbnailScroller() {
        ArrayList<SensorModule> modules = new ArrayList<>(dc.getSensorModules());

        // Set listview adapter
        LinearLayout sideScroller = (LinearLayout) findViewById(R.id.module_thumbnail_container);
        sideScroller.removeAllViewsInLayout();
        LayoutInflater inflater = LayoutInflater.from(this);

        // Populate module list from database here
        for (final SensorModule module : modules) {
            View view = inflater.inflate(R.layout.module_thumb, sideScroller, false);

            // set item content in view
            ((TextView) view.findViewById(R.id.moduleNameTextView)).setText(module.getViewable_name());

            double avg_temp = 0.0;
            double vibrations = 0.0;
            int count = 0;
            NumberFormat formatter = new DecimalFormat("#0.00");

            for (SensorDataEntry data : this.dc.getDataFromModule(module)) {
                count++;
                avg_temp += data.getTemperature();
                vibrations += data.getVibration();
            }
            avg_temp = avg_temp / count;

            ((TextView) view.findViewById(R.id.temperatureTextView)).setText(formatter.format(avg_temp)+"°F");
            ((TextView) view.findViewById(R.id.vibrationTextView)).setText(formatter.format(vibrations/1000)+"K");
            sideScroller.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showModuleScreens(module);
                }
            });
        }
    }

    private void showModuleScreens(SensorModule module) {
        ListView verticleScroller = (ListView) findViewById(R.id.module_screen_scroller);
        verticleScroller.removeAllViewsInLayout();
        ArrayList<Integer> screensNeeded = new ArrayList<Integer>();
        screensNeeded.add(0); // main view
        screensNeeded.add(1); // temperature
        screensNeeded.add(2); // vibrations
        screensNeeded.add(3); // electic current

        ModuleScreenAdapter adapter = new ModuleScreenAdapter(this, screensNeeded);
        adapter.setUpAdapter(this.dc, module, this);

        //Set listview adapter
        ListView listView = (ListView) findViewById(R.id.module_screen_scroller);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        verticleScroller.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        verticleScroller.setFocusable(true);
        verticleScroller.setFocusableInTouchMode(true);
        verticleScroller.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    public void refreshScreens(SensorModule sm) {
        int moduleIndex = 0;
        ArrayList<SensorModule> modules = new ArrayList<>(dc.getSensorModules());

        for (int i = 0; i < modules.size(); i++) {
            if (sm.getAccess_name().equals(modules.get(i).getAccess_name())) {
                moduleIndex = i;
                break;
            }
        }

        refreshScreens(moduleIndex);
    }

    public void refreshScreens(int moduleIndex) {
        try {
            showThumbnailScroller();
        }
        catch (Exception e) {System.out.println(e.toString());}
        try {
            showModuleScreens(this.dc.getSensorModules().get(moduleIndex));
        }
        catch (Exception e) {System.out.println(e.toString());}
    }

    private void sendFeedbackEmail() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"phelps3@wisc.edu"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Motor Monitor Feedback");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\nSent from MotorMonitor Android Application");
        startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    private void addNewModule() {
        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        Toast.makeText(getBaseContext(), "Scan Barcode to Add New Module",
                Toast.LENGTH_LONG).show();
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    String barcodeData = barcode.displayValue;
                    Log.d("QR CODE SCANNER", barcodeData);

                    try {
                        JSONObject json = new JSONObject(barcodeData);
                        long newId = 0;
                        try {
                            //(Long id, String access_name, String access_passcode,
                            // String viewable_name, String details)
                            SensorModule newModule = new SensorModule(newId, json.getString("access_name"),
                                    json.getString("access_passcode"), json.getString("viewable_name"),
                                    "No saved notes.");

                            checkForModulePassword(newModule);
                        } catch (Exception e) {
                            //invalid format
                            Toast.makeText(getBaseContext(), "Error: Invalid QR Code Data",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //couldnt parse json data
                        Toast.makeText(getBaseContext(), "Error: Invalid QR Code Data",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("QR CODE SCANNER", "Nothing Captured");
                }
            } else {
                Log.e("QR CODE SCANNER", "ERROR");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkForModulePassword(final SensorModule module) {
        final String passcode = module.getAccess_passcode();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Sensor Module Passcode");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().equals(passcode)) {
                    addNewModule(module);
                }
                else {
                    Toast.makeText(getBaseContext(), "Incorrect Passcode",
                            Toast.LENGTH_SHORT).show();
                }
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

    private void addNewModule(SensorModule module) {
        try {
            module.setId(this.dc.getNextSensorModuleId());
            this.dc.addSensorModule(module);
            Toast.makeText(getBaseContext(), "New Sensor Module Added!",
                    Toast.LENGTH_SHORT).show();

            try {
                showThumbnailScroller();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Error Refreshing",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            //database errors
            Toast.makeText(getBaseContext(), "Error: Could Not Add New Sensor Module",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

