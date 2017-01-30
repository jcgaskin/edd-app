package com.example.jared.myapplication;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


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
                    devicePolicyManager.lockNow();
                }
            }
        }
    }
}
