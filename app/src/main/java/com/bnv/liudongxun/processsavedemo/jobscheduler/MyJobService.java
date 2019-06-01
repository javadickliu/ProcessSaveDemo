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
        //2.JobParameters 对象作用
        //3.onStartJob返回值的影响
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
