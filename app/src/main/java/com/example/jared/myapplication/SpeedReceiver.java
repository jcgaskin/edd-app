package com.example.jared.myapplication;

import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;



/**
 * Created by jared on 1/22/17.
 * A BroadcastReceiver class to receive the Intent from the class "StayOnService"
 * This Intent contains an "extra" with the key "speed" whose value is the last known speed
 * of the device in meters per second. This class then locks the device for a period of 15 seconds
 * if that speed is above 9 meters per second
 */

public class SpeedReceiver extends BroadcastReceiver
{

    //This method is fired when this receiver gets the Intent from StayOnService, whose action is called "HelloWorld"
    public void onReceive(Context context, Intent intent)
    {

        Bundle SpeedBundle = intent.getExtras();
        float currentSpeed =  SpeedBundle.getFloat("Speed");
        Log.i("********","Intent Received");

        //If the received speed is above 9 m/s
        if(currentSpeed > 9)
        {
            long timeBegin = System.currentTimeMillis();
            long time = timeBegin;
            //Continually lock the phone for 15 seconds, after which the user will be able to wake their
            //phone, if another intent has not been sent with a speed above 9 m/s

            DevicePolicyManager deviceManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            while ((time - timeBegin) < 5000)
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
                audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                deviceManager.lockNow();
                time = System.currentTimeMillis();
            }
        }
    }
}
