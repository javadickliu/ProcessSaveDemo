package com.bnv.liudongxun.processsavedemo.foregroundservice;

import android.app.Service;
import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelUuid;

public class AssistService extends Service {
    public AssistService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class MyBinder extends Binder {
        public Service getService() {
            return AssistService.this;
        }

    }
}
