package com.example.jared.myapplication;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.provider.Settings;
import android.content.ContentResolver;
import android.media.AudioManager;
import android.content.Context;


public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager = null;
    private SpeedReceiver speedReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        //Creating an instance of SpeedReceiver and starting the StayOnService Service:
        //(These contain the functionality of the app)
        speedReceiver = new SpeedReceiver(mDevicePolicyManager);
        startService(new Intent(this, StayOnService.class));

    }

    //*****************************THE CODE BELOW IS FOR TESTING ONLY***************************************

    //This button takes the place of the trigger that will occur when phone is moving over a certain speed
    public void onClickButt(View V){

        //Setting all possible audio disturbances to silent
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);

    }
}
