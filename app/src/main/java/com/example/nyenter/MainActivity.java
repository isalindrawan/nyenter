package com.example.nyenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private CameraManager cameraManager;
    private String cameraID;
    private ToggleButton toggleButton;
    private TextView statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager
                        .FEATURE_CAMERA_FLASH)) {
            
            showNoFlashError();
        }

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        
        try {
            
            cameraID = cameraManager.getCameraIdList()[0];
            
        } catch (CameraAccessException exception) {
            
            exception.printStackTrace();
        }

        statusMessage = findViewById(R.id.status);

        toggleButton = findViewById(R.id.toggle);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
                switchFlashlight(isChecked);
            }
        });
    }

    private void switchFlashlight(boolean isChecked) {

        try {

            cameraManager.setTorchMode(cameraID, isChecked);

            if (isChecked) {

                statusMessage.setText("OK, MURUP IKI SOB");

            } else {

                statusMessage.setText("MATI SOB");
            }

        } catch (CameraAccessException exception) {

            exception.printStackTrace();
        }
    }

    private void showNoFlashError() {

        new AlertDialog.Builder(this)
                .setTitle("Dude ... !!!")
                .setMessage("Your phone isn't smart enough, it doesn't have a fucking flash on it ...")
                .setPositiveButton("oh, ok then ...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                })
                .show();
    }
}