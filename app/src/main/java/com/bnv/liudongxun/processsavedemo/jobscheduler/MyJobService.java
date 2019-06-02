package com.bnv.liudongxun.processsavedemo.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.util.EventLogTags;
import android.util.Log;

import java.io.File;

/**
 * Created by liudongxun on 2019/6/1.
 */

public class MyJobService extends JobService {
    private static final String TAG = "MyJobService";
    @Override
    public boolean onStartJob(JobParameters params) {
        //1.onStartJob调用时机
        //·当我们通过JobScheduler对象设置的JobInfo的条件触发的时候调用
        //2.onStartJob返回值作用
        //·如果我们需要在onStartJob开启一个线程执行时耗时任务,那么我们返回true,否者返回false
        //·如果我们不返回true,有可能我们的耗时任务没执行完JonService就销毁了.
        //3.JobParameters 对象作用

        Log.d(TAG, "onStartJob: ");
        String file=Environment.getExternalStorageDirectory().getPath();
        long time=System.currentTimeMillis();
        FileUtil.writeToFile(file,"jobscheduler.txt","onStartJob"+time,true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        return false;
    }
}
