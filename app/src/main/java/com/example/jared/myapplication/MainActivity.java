package com.example.jared.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.provider.Settings;
import android.content.ContentResolver;
import android.media.AudioManager;
import android.content.Context;


public class MainActivity extends AppCompatActivity {

    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    private AudioManager audioManager = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //This button takes the place of the trigger that will occur when phone is moving over a certain speed
    public void onClickButt(View V){



        //Each of the following "disturbance eliminations" can be moved into it's own method to be cleaner"


        //Turning the brightness to 0, need a better way to get rid of visual distractions (this only dims the screen)
        //Airplane mode cannot be changed without a rooted device
        //Noifications cannot be stopped without a rooted device
        //There does seem to be a way to "lock" the phone that only requires admin access
        //^but this requires permissions that would make most users uncomfortable.
        cResolver =  getContentResolver();
        Settings.System.putInt(cResolver,Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 0);



        //adding comment
        //Setting all possible audio disturbances to silent
        //audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
    }
}
