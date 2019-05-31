package com.bnv.liudongxun.processsavedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bnv.liudongxun.processsavedemo.foregroundservice.BackGroundService;
import com.bnv.liudongxun.processsavedemo.onepixel.ActivityManager;
import com.bnv.liudongxun.processsavedemo.onepixel.OnePixelActivity;
import com.bnv.liudongxun.processsavedemo.onepixel.ScreenBCListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //    testOnPixel();
        //  Intent.ACTION_TIME_TICK
        testForeGroundService();
    }

    /**
     * 测试开启1像素Activity来保活进程
     */
    public void testOnPixel() {
        ScreenBCListener screenBCListener = new ScreenBCListener(MainActivity.this);
        screenBCListener.registerListener(new ScreenBCListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.d(TAG, "onScreenOn: ");
                ActivityManager.getInstance(MainActivity.this).finishActivity();
            }

            @Override
            public void onScreenOff() {
                Log.d(TAG, "onScreenOff: ");
                Intent startOnPixel = new Intent(MainActivity.this, OnePixelActivity.class);
                startActivity(startOnPixel);
            }
        });
    }


    /**
     * 使用前台服务进行保活
     */
    public void testForeGroundService() {
        Intent bindService = new Intent(MainActivity.this, BackGroundService.class);
        bindService(bindService, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: ");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: ");
            }
        }, BIND_AUTO_CREATE);
    }
}
