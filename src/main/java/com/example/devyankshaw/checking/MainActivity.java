package com.example.devyankshaw.checking;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch swtSwitch;
    private boolean checkLock;
    private SharedPreferences settings;
    public static final String PREFS_NAME = "MyPrefsFile";
    UserPresentBroadcastReceiver userPresentBroadcastReceiver = new UserPresentBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_FULLSCREEN|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swtSwitch = findViewById(R.id.swtService);
        swtSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    startService(new Intent(MainActivity.this,LockService.class).setAction(Intent.ACTION_SCREEN_OFF));
                }else {
                    stopService(new Intent(MainActivity.this,LockService.class));
                }
                //Saving the state of the switch i.e swtLock
                SharedPreferences.Editor editorLock = settings.edit();
                editorLock.putBoolean("switchKeyLock", isChecked);
                editorLock.commit();
            }
        });

        settings = getSharedPreferences(PREFS_NAME, 0);
        boolean lockValue = settings.getBoolean("switchKeyLock", false);
        swtSwitch.setChecked(lockValue);

//        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
//        registerReceiver(userPresentBroadcastReceiver, filter);
//
//        IntentFilter filter1 = new IntentFilter(Intent.ACTION_SCREEN_ON);
//        registerReceiver(userPresentBroadcastReceiver, filter1);

//        IntentFilter filter1 = new IntentFilter(Intent.ACTION_USER_UNLOCKED);
//        registerReceiver(userPresentBroadcastReceiver, filter1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(userPresentBroadcastReceiver);
    }
}
