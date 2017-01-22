package com.example.jared.myapplication;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by jared on 1/22/17.
 */

public class DeviceAdminSample extends DeviceAdminReceiver{


    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "Driving Mode is now enabled",
                Toast.LENGTH_SHORT).show();
    }


}
