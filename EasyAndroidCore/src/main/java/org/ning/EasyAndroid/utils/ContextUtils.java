package org.ning.EasyAndroid.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * apk信息工具
 *
 * @author yanni
 */
public class ContextUtils extends ContextCompat{

    /**
     * @param context
     * @return 版本名
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }


    /**
     * @param context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 软件是否在后台运行
     *
     * @param context
     * @return
     */
    public static boolean isOnBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 软件是否运行在前台
     * @param context
     * @return
     */
    public boolean isOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取asset中的数据资源
     * @param asset
     * @param context
     * @return
     */
    public static String getAssetString(String asset, Context context) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(asset)));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while (null != (line = bufferedReader.readLine())) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            bufferedReader = null;
        }
        return "";
    }

}
