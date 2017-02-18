package org.ning.EasyAndroid.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import org.ning.EasyJava.utils.StringUtils;

import java.util.List;

/**
 * Created by YanNing on 2016/8/12.
 */
public class ActivityUtils  extends ActivityCompat{
    /**
     * activity是否在前台運行
     * @param context
     * @param className
     * @return
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * activity是否在前台運行
     * @param context
     * @return
     */
    public static boolean isForeground(Context context){
        return isForeground(context,getRunningActivityName(context));
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getRunningActivityName(Context context){
        String runningActivity = null;
        try {

        ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(runningActivity)){
            String contextString = context.toString();
            return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
        }
        return runningActivity;
    }
}
