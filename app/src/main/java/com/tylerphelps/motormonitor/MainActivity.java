package com.tylerphelps.motormonitor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;
import android.text.InputType;
import android.content.DialogInterface;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.tylerphelps.motormonitor.barcode.BarcodeCaptureActivity;
import android.widget.ListView;
import org.json.JSONObject;
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

        /*for (SensorModule module : dc.getSensorModules()) {
            for (int i = 0; i < 50; i++) {
                double vibration = 75 + Math.random() * 50;
                double temperature = 85 + Math.random()*40;
                SensorDataEntry data = new SensorDataEntry((long) 0, module.getAccess_name(), new Date(), (double) i, vibration, temperature);
                data.setId(this.dc.getNextDataEntryId());
                this.dc.addDataEntry(data);
            }
        }*/

        showThumbnailScroller();
        showModuleScreens(null);
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
            Toast.makeText(this, "Home selected.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_add_module) {
            addNewModule();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings selected.", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            sendFeedbackEmail();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showThumbnailScroller() {
        /*dc.addSensorModule(new SensorModule((long) 1, "a", "a", "Main 1", "", (long) 1));
        dc.addSensorModule(new SensorModule((long) 2, "b", "b", "Main 2", "", (long) 2));
        dc.addSensorModule(new SensorModule((long) 3, "c", "c", "Test 1", "", (long) 3));
        dc.addSensorModule(new SensorModule((long) 4, "d", "d", "Back Pump Valve", "", (long) 4));
        dc.addSensorModule(new SensorModule((long) 5, "e", "e", "Office AC Unit", "", (long) 5));*/
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
            sideScroller.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "SensorModule " + module.getAccess_name() + "Selected from Scroller.", Toast.LENGTH_SHORT).show();
                    showModuleScreens(module);
                }
            });
        }
    }

    private void showModuleScreens(SensorModule module) {
        /*ListView verticleScroller = (ListView) findViewById(R.id.module_screen_scroller);

        //TODO POPULATE GRAPHS HERE

        ModuleScreenController msc = new ModuleScreenController(verticleScroller, module, getApplicationContext());
        msc.updateListView();*/

        ListView verticleScroller = (ListView) findViewById(R.id.module_screen_scroller);
        verticleScroller.removeAllViewsInLayout();
        ArrayList<Integer> screensNeeded = new ArrayList<Integer>();
        screensNeeded.add(1);
        screensNeeded.add(2);
        screensNeeded.add(3);

        ModuleScreenAdapter adapter = new ModuleScreenAdapter(this, screensNeeded);

        //Set listview adapter
        ListView listView = (ListView) findViewById(R.id.module_screen_scroller);
        listView.setAdapter(adapter);

        adapter.setNotifyOnChange(true);
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
                            // //String viewable_name, String details, long sensorModuleId)
                            SensorModule newModule = new SensorModule(newId, json.getString("access_name"),
                                    json.getString("access_passcode"), json.getString("viewable_name"),
                                    "", json.getLong("sensor_module_id"));

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

