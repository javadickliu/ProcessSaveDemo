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
//                .setMinimumLatency(1000)// 任务延时执行,单位毫秒
//                .setOverrideDeadline(6000)// 任务deadline,当到期没达到指定条件也会开始执行
           //     .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)//任务执行的条件，默认值NETWORK_TYPE_NONE
                .setRequiresCharging(false)//任务执行的条件,是否充电 ？？？？？？
            //    .setRequiresDeviceIdle(false)//任务执行条件,设备是否空闲
                //.setPersisted(true) //设备重启后是否继续执行 需要权限android.permission.RECEIVE_BOOT_COMPLETED
               // .setBackoffCriteria(3000, JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略.
                .setPeriodic(3000)//每隔三秒执行一次JonService任,不能和setOverrideDeadline一起用,androidN api24之后至少为15分钟
                .build();
        if (scheduler != null) {
            Log.d(TAG, "startJobScheduler:11 ");
            scheduler.schedule(jobInfo);
        }
        //1.上面JobInfo这种设置表示每隔三秒执行一次JonService的onStartJob()方法
        //·需要注意的是andori7.0之后周期执行的事件至少为15min.
        //·实测华为8.1周期执行JobService方法无效,所以使用JobScheduler一定要注意版本和不同厂商之间的适配.
        //2.JobScheduler
        //·一种系统服务,我们可以设置某种任务交给其帮我们执行.
        //3.JobInfo
        //·我们期望的JobServie的触发条件.
    }

}
