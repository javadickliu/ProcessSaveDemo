package com.bnv.liudongxun.processsavedemo.permission;

/**
 * Created by liudongxun on 2019/6/2.
 */

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通权限不需要用户明确授予即可使用
 * ACCESS_LOCATION_EXTRA_COMMANDS	定位权限
 * ACCESS_NETWORK_STATE	网络状态权限
 * ACCESS_NOTIFICATION_POLICY	通知 APP通知显示在状态栏
 * ACCESS_WIFI_STATE	WiFi状态权限
 * BLUETOOTH	使用蓝牙权限
 * BLUETOOTH_ADMIN	控制蓝牙开关
 * BROADCAST_STICKY	粘性广播
 * CHANGE_NETWORK_STATE	改变网络状态
 * CHANGE_WIFI_MULTICAST_STATE	改变WiFi多播状态，应该是控制手机热点（猜测）
 * CHANGE_WIFI_STATE	控制WiFi开关，改变WiFi状态
 * DISABLE_KEYGUARD	改变键盘为不可用
 * EXPAND_STATUS_BAR	扩展bar的状态
 * GET_PACKAGE_SIZE	获取应用安装包大小
 * INTERNET	网络权限
 * KILL_BACKGROUND_PROCESSES	杀死后台进程
 * MODIFY_AUDIO_SETTINGS	改变音频输出设置
 * NFC	支付
 * READ_SYNC_SETTINGS	获取手机设置信息
 * READ_SYNC_STATS	数据统计
 * RECEIVE_BOOT_COMPLETED	监听启动广播
 * REORDER_TASKS	创建新栈
 * REQUEST_INSTALL_PACKAGES	安装应用程序
 * SET_TIME_ZONE	允许应用程序设置系统时间区域
 * SET_WALLPAPER	设置壁纸
 * SET_WALLPAPER_HINTS	设置壁纸上的提示信息，个性化语言
 * TRANSMIT_IR	红外发射
 * USE_FINGERPRINT	指纹识别
 * VIBRATE	震动
 * WAKE_LOCK	锁屏
 * WRITE_SYNC_SETTINGS	改变设置
 * SET_ALARM	设置警告提示
 * INSTALL_SHORTCUT	创建快捷方式
 * UNINSTALL_SHORTCUT	删除快捷方式
 * <p>
 * <p>
 * 危险权限和其权限组
 * CALENDAR:
 * READ_CALENDAR
 * WRITE_CALENDAR
 * <p>
 * CALL_LOG:
 * READ_CALL_LOG
 * WRITE_CALL_LOG
 * PROCESS_OUTGOING_CALLS
 * <p>
 * CAMERA:
 * CAMERA
 * <p>
 * CONTACTS:
 * READ_CONTACTS
 * WRITE_CONTACTS
 * GET_ACCOUNTS
 * <p>
 * LOCATION:
 * ACCESS_FINE_LOCATION
 * ACCESS_COARSE_LOCATION
 * <p>
 * MICROPHONE:
 * RECORD_AUDIO
 * <p>
 * PHONE:
 * READ_PHONE_STATE
 * READ_PHONE_NUMBERS
 * CALL_PHONE
 * ANSWER_PHONE_CALLS
 * ADD_VOICEMAIL
 * USE_SIP
 * <p>
 * SENSORS:
 * BODY_SENSORS
 * <p>
 * SMS:
 * SEND_SMS
 * RECEIVE_SMS
 * READ_SMS
 * RECEIVE_WAP_PUSH
 * RECEIVE_MMS
 * <p>
 * STORAGE:
 * READ_EXTERNAL_STORAGE
 * WRITE_EXTERNAL_STORAGE
 */
public class PermissionUtils {
    /**
     * 常见的权限
     */
    public static final int BASE_PERMISSION_REQUEST = 100;
    public static final int REQUESTCODE_AUDIO = 3;
    public static final int REQUESTCODE_CALL = 7;
    public static final int REQUESTCODE_CAMERA = 2;
    public static final int REQUESTCODE_CONTACTS = 5;
    public static final int REQUESTCODE_EXTERMASTORAGE = 1;
    public static final int REQUESTCODE_LOCATION = 4;
    public static final int REQUESTCODE_SENDSMS = 6;
    private static final String TAG = "PermissionUtils";

    public interface OnRequestPermissionsResultCallbacks {
        void onPermissionsDenied(int i, List<String> list, boolean z);

        void onPermissionsGranted(int i, List<String> list, boolean z);
    }

    /**
     * 检查当前SDK版本
     * @return
     */
    private static boolean needCheckPermission() {
        return VERSION.SDK_INT >= 23;
    }

    /**
     * 检查权限格式是否正确
     * @param permissions
     */
    private static void checkPermissionIfExists(@NonNull String... permissions) {
        int i = 0;
        while (i < permissions.length) {
            String[] splitResult = permissions[i].split("\\.");
            if ("android".equals(splitResult[0]) || "com".equals(splitResult[0])) {
                i++;
            } else {
                throw new RuntimeException("permissions type is error ;error permissions ==" + splitResult[0]);
            }
        }
    }

    /**
     * 请求存储权限
     * @param activity
     * @param requestCode
     * @return
     */
    public static boolean getExternalStoragePermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean getCameraPermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean getAudioPermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean getLocationPermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.ACCESS_COARSE_LOCATION");
    }

    public static boolean getContactsPermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.READ_CONTACTS");
    }

    public static boolean getSendSMSPermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.SEND_SMS");
    }

    public static boolean getCallPhonePermissions(@NonNull Activity activity, int requestCode) {
        return requestPerssions(activity, requestCode, "android.permission.CALL_PHONE");
    }

    /**
     * 动态请求权限
     * @param activity
     * @param requestCode
     * @param permissions
     * @return
     */
    public static boolean requestPerssions(Activity activity, int requestCode, String... permissions) {
        checkPermissionIfExists(permissions);
        if (!needCheckPermission()) {
            return true;
        }
        if (hasPermissons(activity, permissions)) {
            return true;
        }
        Log.d(TAG, "requestPerssions:permissions.length== " + permissions.length);
        List<String> deniedPermissions = getDeniedPermissions(activity, permissions);
        if (deniedPermissions != null && deniedPermissions.size() > 0) {
            List<String> rationaleList = new ArrayList();
            for (String permission : deniedPermissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    rationaleList.add(permission);
                }
            }
            if (rationaleList.size() > 0) {
                Log.d(TAG, "requestPerssions: 11111111111111");
                showRationaleDialog(activity, (String[]) rationaleList.toArray(new String[rationaleList.size()]), requestCode);
                return true;
            }
            Log.d(TAG, "requestPerssions: 2222222222");
            ActivityCompat.requestPermissions(activity, (String[]) deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        }
        return false;
    }

    private static String getAlertInfo(int requestCode) {
        String info = "error";
        switch (requestCode) {
            case 1:
                return "通知:应用需要读取外部存储卡,否则应用可能无法正常使用";
            default:
                return info;
        }
    }

    /**
     * 展示提示对话框,主动告知用户需要权限的原因
     * @param activity
     * @param deniedPermissions
     * @param requestCode
     */
    public static void showRationaleDialog(final Activity activity, final String[] deniedPermissions, final int requestCode) {
        Log.d(TAG, "showRationaleDialog: ");
        String alertInfo = "test";
        for (String str : deniedPermissions) {
            alertInfo = alertInfo + str;
        }
        new Builder(activity).setMessage("通知:应用需要" + alertInfo + "权限,否则应用可能无法正常使用").setPositiveButton("去设置", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity, deniedPermissions, requestCode);
            }
        }).setNegativeButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "权限未开启无法启动功能", Toast.LENGTH_SHORT).show();
            }
        }).create().show();
    }

    public static void showSetttingDialog(final Activity activity, String[] deniedPermissions, final int requestCode) {
        new Builder(activity).setMessage("未获得获取指定权限,此功能无法使用,需前往应用权限设置打开权限").setPositiveButton("去设置", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PermissionUtils.startApplicationDetailsSettings(activity, requestCode);
            }
        }).setNegativeButton("取消", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "前往应用权限设置打开权限", Toast.LENGTH_SHORT).show();
            }
        }).create().show();
    }

    public static boolean deniedRequestPermissonsAgain(@NonNull Activity activity, @NonNull String... permissions) {
        for (String permission : getDeniedPermissions(activity, permissions)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回被拒绝的权限
     * @param activity 1
     * @param permissions 1
     * @return 1
     */
    public static List<String> getDeniedPermissions(@NonNull Activity activity, @NonNull String... permissions) {
        checkPermissionIfExists(permissions);
        if (!needCheckPermission()) {
            return null;
        }
        List<String> deniedPermissions = new ArrayList();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != 0) {
                deniedPermissions.add(permission);
            }
        }
        if (deniedPermissions.isEmpty()) {
            return null;
        }
        return deniedPermissions;
    }

    public static void startApplicationDetailsSettings(@NonNull Activity activity, int requestCode) {
        Toast.makeText(activity, "点击权限，并打开全部权限", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        Log.d(TAG, "startApplicationDetailsSettings: ");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 判断是否已经拥有权限
     * @param activity 1
     * @param permissions 1
     * @return
     */
    public static boolean hasPermissons(@NonNull Activity activity, @NonNull String... permissions) {
        checkPermissionIfExists(permissions);
        if (!needCheckPermission()) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在Activity的onRequestPermissionsResult调用,一定要实现这个方法
     * @param requestCode 1
     * @param permissions 1
     * @param grantResults 1
     * @param callBack 1
     * @param activity 1
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, OnRequestPermissionsResultCallbacks callBack, Activity activity) {
        if (requestCode == 1 && grantResults.length != 0 && grantResults[0] == -1) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                showRationaleDialog(activity, permissions, requestCode);
            } else {
                showSetttingDialog(activity, permissions, requestCode);
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: permissions ==" + permissions.length + " grantResults==" + grantResults.length);
        if (requestCode == 100) {
            ArrayList<String> deniedNoShowList = new ArrayList();
            ArrayList<String> deniedList = new ArrayList();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                        deniedList.add(permissions[i]);
                    } else {
                        deniedNoShowList.add(permissions[i]);
                    }
                }
            }
            if (deniedNoShowList.size() > 0) {
                showSetttingDialog(activity, (String[]) deniedNoShowList.toArray(new String[deniedNoShowList.size()]), requestCode);
            } else if (deniedList.size() > 0) {
                showRationaleDialog(activity, (String[]) deniedList.toArray(new String[deniedList.size()]), requestCode);
            }
        }
    }

}
