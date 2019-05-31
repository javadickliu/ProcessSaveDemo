package com.bnv.liudongxun.processsavedemo.foregroundservice;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bnv.liudongxun.processsavedemo.MainActivity;

import java.nio.channels.ClosedSelectorException;

public class BackGroundService extends Service {
    private static final String TAG = "BackGroundService";
    public BackGroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyIBider();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        Intent bindService = new Intent(BackGroundService.this, AssistService.class);
        bindService(bindService, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: ");
                Service assistService = ((AssistService.MyBinder) service).getService();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(BackGroundService.this);
                Notification notification = builder.build();
                assistService.startForeground(1,notification);
                startForeground(1, notification);
                stopForeground(true);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: ");
            }
        }, BIND_AUTO_CREATE);

    }

    private class MyIBider extends Binder{

    }
}
