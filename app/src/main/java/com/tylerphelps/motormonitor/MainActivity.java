package com.tylerphelps.motormonitor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        showListView();
        displayModuleScreens(null);
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

    public void switchToModule(int position) {
        Toast.makeText(this, "Could not get module at position " + position + ".",
                Toast.LENGTH_SHORT).show();
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

    private void showListView() {
        //SensorModule(Long id, String access_name, String access_passcode, String viewable_name, String details, long sensorModuleId)
        DatabaseController dc = new DatabaseController(this.getApplicationContext());
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
        for (SensorModule module : modules) {
            View view  = inflater.inflate(R.layout.module_thumb, sideScroller, false);

            // set item content in view
            ((TextView) view.findViewById(R.id.moduleNameTextView)).setText(module.getViewable_name());
            sideScroller.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "SensorModule Selected from Scroller.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void  displayModuleScreens(SensorModule module) {
        ListView verticleScroller = (ListView) findViewById(R.id.module_screen_scroller);

        ModuleScreenController msc = new ModuleScreenController(verticleScroller, null, getApplicationContext());
        msc.updateListView();
    }

    private void sendFeedbackEmail() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"phelps3@wisc.edu"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Motor Monitor Feedback");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n\nSent from MotorMonitor Android Application");
        startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
    }

    private void addNewModule() {

    }
}

