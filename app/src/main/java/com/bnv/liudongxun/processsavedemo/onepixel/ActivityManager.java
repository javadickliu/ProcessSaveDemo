package com.bnv.liudongxun.processsavedemo.onepixel;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by liudongxun on 2019/5/31.
 */

public class ActivityManager {

    private Context mContext;

    private WeakReference<Activity> mActivityWref;

    public static ActivityManager gDefualt;

    public static ActivityManager getInstance(Context pContext) {
        if (gDefualt == null) {
            gDefualt = new ActivityManager(pContext.getApplicationContext());
        }
        return gDefualt;
    }
    private ActivityManager(Context pContext) {
        this.mContext = pContext;
    }

    public void setActivity(Activity pActivity) {
        mActivityWref = new WeakReference<Activity>(pActivity);
    }

//    public void startActivity() {
//        SinglePixelActivity.actionToSinglePixelActivity(mContext);
//    }

    public void finishActivity() {
        //结束掉SinglePixelActivity
        if (mActivityWref != null) {
            Activity activity = mActivityWref.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
