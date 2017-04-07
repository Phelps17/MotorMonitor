package com.tylerphelps.motormonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

public class AddModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_module_screen);

        SurfaceView cameraView = (SurfaceView)findViewById(R.id.camera_view);
    }

}
