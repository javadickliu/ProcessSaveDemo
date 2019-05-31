package com.bnv.liudongxun.processsavedemo.onepixel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bnv.liudongxun.processsavedemo.R;

public class OnePixelActivity extends AppCompatActivity {
    private static final String TAG = "OnePixelActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pixel);
        Window window = getWindow();
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = 1;
        layoutParams.height = 1;
        layoutParams.x = 0;
        layoutParams.y = 0;
        window.setAttributes(layoutParams);
        Log.d(TAG, "onCreate: ");
        ActivityManager.getInstance(OnePixelActivity.this).setActivity(this);
    }
}
