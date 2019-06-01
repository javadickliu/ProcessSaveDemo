package com.bnv.liudongxun.processsavedemo.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by liudongxun on 2019/6/1.
 */

public class JobServiceManager {
    private static final String TAG = "JobServiceManager";
    public void startJobScheduler(Context context) {
        ComponentName jobService = new ComponentName(context, MyJobService.class);
        Intent service = new Intent(context, MyJobService.class);
        context.startService(service);
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(10087, jobService) //任务Id等于10087
             //   .setMinimumLatency(1000)// 任务延时执行,单位毫秒
            //    .setOverrideDeadline(60000)// 任务deadline,当到期没达到指定条件也会开始执行
           //     .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)//任务执行的条件，默认值NETWORK_TYPE_NONE
                .setRequiresCharging(true)//任务执行的条件,是否充电 ？？？？？？
            //    .setRequiresDeviceIdle(false)//任务执行条件,设备是否空闲
                //.setPersisted(true) //设备重启后是否继续执行 需要权限android.permission.RECEIVE_BOOT_COMPLETED
               // .setBackoffCriteria(3000, JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略.
                .setPeriodic(900000)//每隔三秒执行一次JonService任,不能和setOverrideDeadline一起用,至少为15分钟
                .build();
        if (scheduler != null) {
            Log.d(TAG, "startJobScheduler: ");
            scheduler.schedule(jobInfo);
        }
//        这个方法如果另外起线程做一些费事的事情的话，最好返回为true，
//        然后在线程运行完成后 调用jobFinished， 不然线程没执行完成，jobservice就直接onDestory了，
//        虽然还没看到这个有什么影响，如果你要返回结果进行加工显示就是另外的情况了。
    }

}
