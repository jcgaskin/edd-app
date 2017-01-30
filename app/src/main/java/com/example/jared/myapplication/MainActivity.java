package com.example.jared.myapplication;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.provider.Settings;
import android.content.ContentResolver;
import android.media.AudioManager;
import android.content.Context;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ENABLE_ADMIN = 117;
    private AudioManager audioManager = null;


    private SpeedReceiver speedReceiver;
    private DevicePolicyManager mDevicePolicyManager;
    private DeviceAdminReceiver mDeviceAdminReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdminReceiver = new DeviceAdminSample();


        //Creating an instance of SpeedReceiver and starting the StayOnService Service:
        //(These contain the functionality of the app)
        speedReceiver = new SpeedReceiver(mDevicePolicyManager);
        startService(new Intent(this, StayOnService.class));

        askForAdmin();


    }


    private void askForAdmin(){

        //Every time the user opens the app, we will make a check to determine if the user has the administration
        //Settings enabled (which allow the app to do its job) if they do not, we will open a UI for them to enable these
        //settings, and otherwise...?

        ComponentName admin = new ComponentName("com.example.jared.myapplication","DeviceAdminSample");

        if(!mDevicePolicyManager.isAdminActive(admin))
        {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminReceiver.getWho(this));
            //intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            //  MainActivity.getString(R.string.add_admin_extra_app_text));
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
        }

        //Need to make this function return a value, and basically kill the app if the user
        //decline the adminstration (as the app is useless without it)
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
