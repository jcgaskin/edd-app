package com.example.jared.myapplication;

import android.app.admin.DeviceAdminReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.media.AudioManager;
import android.content.Context;


/**
 * KEY ASSUMPTIONS:
 *
 * 1. LOCATION: The user has location services enabled(GPS). There are functions that fire when GPS is disabled/enabled that
 * could be used to promt the user to re-enable GPS in the LocationListener inner class in StayOnService.java
 *
 * 2. ADMINISTRATOR: The user has the application enabled as a device administrator in settings->security. The askForadmin() method
 * was a partially sucessful attempt at prompting the user to enable device admin if it is not already enabled when they
 * start the app.
 *
 * 3. RUNNING: The user has the application open. Testing has only been conducted with the app open, and it must be determined if the user
 * can close the application (app runs in recents), can fully terminate it (swipe off recents list) and what services/functionality will remain
 * Ideally, the functions of the application would continue to lock the device even after the app had been quit, and anything short of
 * the service being stopped manually would allow the app to conintue indefinitely. Here battery usage will be a huge factor, but preliminary testing
 * has indicated that battery may not be such a large issue. In a perfect world, this application is built into the OS on the device,
 * or produced by Apple/Google in such a way that it is always running regardless of user choice.
 *
 * 4. DRIVING: The user is "driving" . Currently the app has no way of telling if the user is a driver or a passenger, it only
 * knows how fast they are moving. Thus our market could be for begining drivers who would likely be a)suddenly driving more frequently
 * and b) forced to install the app by a parent. This means a password to disabling the application should be something that is added.
 * Currently, device administration prevents the app from being uninstalled before its device admin status is removed. So, looking into
 * making enabling/disabling the apps device admin status password protected would be a good idea.
 *
 * Choices the user must make for it to work in current state:
 * 1.DOWNLOAD Application (circumvented with teen driver user model, as parents with force teens to download)
 * 2.AGREE to device admin (circumvented with teen driver user model, as parents will accept device admin [set password in future])
 * 3.OPEN app each time entering vehicle as driver (this is the real problem, and the best possible case would be that this app
 * runs forever after launched for the first time (unless the service is stopped by the user) and maintain its functionality
 *
 * to disable, a teen could:
 *
 * 1. Disable app as device admin (need password protection)
 * 2. Disable location services (see if device admin can force this on)
 * 3. Exit the application (if it doesn't run in the background), or kill the service that is running in the background (maybe a way for
 * device admin to prevent this?)
 *

 **/


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ENABLE_ADMIN = 117;
    private SpeedReceiver speedReceiver;
    private DeviceAdminReceiver mDeviceAdminReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDeviceAdminReceiver = new DeviceAdminSample();
        //Creating an instance of SpeedReceiver and starting the StayOnService Service:
        //(These contain the functionality of the app)

        speedReceiver = new SpeedReceiver();
        startService(new Intent(this, StayOnService.class));

        //askForAdmin();


    }

    /** It may be appropriate to move this function somewhere else
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
    **/


}
