package com.example.jared.myapplication;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.media.AudioManager;
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


/**
 * Created by jared on 1/22/17.
 * A BroadcastReceiver class to receive the Intent from the class "StayOnService"
 * This Intent contains an "extra" with the key "speed" whose value is either:
 * TRUE: to indicate the device is moving at a speed higher than 9 m/s
 * FALSE: to indicate the device is moving at a speed lower than 9 m/s
 */

public class SpeedReceiver extends BroadcastReceiver {

    public boolean speeding;
    public DevicePolicyManager devicePolicyManager;
    public AudioManager audioManager;

    public SpeedReceiver(){

    }

    //Constructor that allows the DevicePolicyManager object to be passed to this Broadcast Receiver.
    //(This is needed to lock the device).
    public SpeedReceiver(DevicePolicyManager mDevicePolicyManager) {
        speeding = false;
        devicePolicyManager = mDevicePolicyManager;
    }


    //This method is fired when this receiver gets the Intent from StayOnService, whose action is called "HelloWorld"
    public void onReceive(Context context, Intent intent){

        Bundle SpeedBundle = intent.getExtras();
        speeding =  SpeedBundle.getBoolean("Speed");
        Log.i("********","Intent Received");

        KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if(!myKM.inKeyguardRestrictedInputMode()) //if device is not locked to begin with (avoids multiple loops)...
        {
            while (speeding)
            {
                //loop while speeding and only run .lockNow() if necessary to lock the device
                if(!myKM.inKeyguardRestrictedInputMode()) {

                    audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                    //audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0); DO NOT SILENCE ALARMS BAD IDEA WILL PISS PEOPLE OFF
                    audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);

                    devicePolicyManager.lockNow();
                }
            }
        }
    }
}
