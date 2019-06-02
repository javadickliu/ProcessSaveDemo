package com.bnv.liudongxun.processsavedemo;

import android.Manifest;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bnv.liudongxun.processsavedemo.foregroundservice.BackGroundService;
import com.bnv.liudongxun.processsavedemo.jobscheduler.JobServiceManager;
import com.bnv.liudongxun.processsavedemo.onepixel.ActivityManager;
import com.bnv.liudongxun.processsavedemo.onepixel.OnePixelActivity;
import com.bnv.liudongxun.processsavedemo.onepixel.ScreenBCListener;
import com.bnv.liudongxun.processsavedemo.permission.PermissionUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //    testOnPixel();
        //  Intent.ACTION_TIME_TICK
        //testForeGroundService();
    //    JobService
        testJonScheduler();


//        //=======动态申请危险权限====
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){//如果该权限没有获得权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//        // todo 直接申请一个权限组和申请一个权限的区别
//        //todo  onRequestPermissionsResult 几个参数作用
//        /**onRequestPermissionsResult
//         * 1.用户对权限是否授权的结果
//         *2.如果用户选择了不在提示,那么是否授予权限的对话框将不会弹出,直接调用onRequestPermissionsResult方法.
//         * 3.requestCode用来识别是哪一个权限申请的请求
//         */
       String[] permissionArray=new String[]{
               Manifest.permission.CAMERA

       };
        PermissionUtils.requestPerssions(MainActivity.this,1,permissionArray);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionUtils.OnRequestPermissionsResultCallbacks() {
           @Override
           public void onPermissionsDenied(int i, List<String> list, boolean z) {

           }

           @Override
           public void onPermissionsGranted(int i, List<String> list, boolean z) {

           }
       },MainActivity.this);
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

    /**
     * 使用JonScheduler进行保活
     */
    public void testJonScheduler() {
        new JobServiceManager().startJobScheduler(MainActivity.this);
    }
}
