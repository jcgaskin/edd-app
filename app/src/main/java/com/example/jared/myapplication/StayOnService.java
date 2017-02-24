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
public class StayOnService extends Service
{

    //Action for the Intent that will be sent to "SpeedReceiver"
    public static final String BROADCAST_ACTION = "SPEEDING";

    //Declaring stuff necessary to find device location.
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location lastLocation;


    //Intent to be sent to "SpeedReceiver"
    Intent SpeedIntent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        SpeedIntent = new Intent(BROADCAST_ACTION);
        lastLocation = null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        //Requesting location updates every 1 second
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, listener);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);
    }

    //**********************************************************************************************
    /**
     * This class is the location listener that will fire a method when the device has moved.
     * contains the call to send a broadcast with the Intent "SpeedIntent" to the class "SpeedReceiver"
     */

    public class MyLocationListener implements LocationListener
    {

        Location prevLocation;
        //This method fires when one of the calls to .requestLocationUpdates() above shows that the
        //Location has changed
        public void onLocationChanged(Location location)
        {
            float speed = location.getSpeed();
            // FALLBACK: Sometimes, getSpeed() will return 0.0 if it cannot get a speed.
            // When this happens, we fall to calculating the speed manually from the last
            // location we've received. This may mean that the user has to wait a little bit
            // longer before slowing down below the threshold to use their phone.
            if (prevLocation != null && speed == 0)
            {
                float distance = location.distanceTo(prevLocation);
                float time = (location.getTime() - prevLocation.getTime()) / 1000.0f;

                speed = distance / time;
            }

            String speedreadout = Float.toString(speed);
            Log.i("speed",speedreadout);

            SpeedIntent.setPackage("com.example.jared.myapplication");
            SpeedIntent.putExtra("Speed", speed);
            sendBroadcast(SpeedIntent);
            prevLocation = location;
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
            //Do nothing
        }
    }


}
