package com.example.jared.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * This class checks the device's location at a specified time intervals
 * and uses the MyLocationListener class as a callback to send an Intent if
 * the device has moved.
 *
 */
public class StayOnService extends Service {

    //Action for the Intent that will be sent to "SpeedReceiver"
    public static final String BROADCAST_ACTION = "HelloWorld";

    //Declaring stuff necessary to find device location.
    public LocationManager locationManager;
    public MyLocationListener listener;

    //Intent to be sent to "SpeedReceiver"
    Intent SpeedIntent;


    @Override
    public void onCreate()
    {
        super.onCreate();
        SpeedIntent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();

        //Requesting updates on the device's location every 10000 milliseconds (every minute)
        //--listener is the callback function
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, listener);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);
    }

    //**********************************************************************************************
    /**
     * This class is the location listener that will fire a method when the device has moved.
     * contains the call to send a broadcast with the Intent "SpeedIntent" to the class "SpeedReceiver"
     */

    public class MyLocationListener implements LocationListener{


        //This method fires when one of the calls to .requestLocationUpdates() above shows that the
        //Location has changed
        public void onLocationChanged(Location location)
        {
            SpeedIntent.setPackage("com.example.jared.myapplication");
            //If the device's speeed can be found and it is above 9m/s...
            if (location.hasSpeed() && location.getSpeed() > 9) {
                //Put a "True" value as an extra in the Intent
                SpeedIntent.putExtra("Speed", true);
            }
            else
            {
                //Otherwise put a "False" value
                SpeedIntent.putExtra("Speed", false);
            }
            //Sending the broadcast
            sendBroadcast(SpeedIntent);
        }

        //These methods are required to implement the interface:

        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }

        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    }


}
