package org.ning.EasyAndroid.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.ViewGroup;

import org.ning.EasyJava.utils.StringUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YanNing on 2016/8/12.
 */
public class ActivityUtils extends ActivityCompat {
    /**
     * activity是否在前台運行
     *
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
     *
     * @param context
     * @return
     */
    public static boolean isForeground(Context context) {
        return isForeground(context, getRunningActivityName(context));
    }

    /**
     * @param context
     * @return
     */
    public static String getRunningActivityName(Context context) {
        String runningActivity = null;
        try {

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(runningActivity)) {
            String contextString = context.toString();
            return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
        }
        return runningActivity;
    }

    /**
     * 获取最顶层的view
     *
     * @param activity
     * @return
     */
    @Nullable
    public static ViewGroup getActivityDecorView(Activity activity) {
        ViewGroup decorView = null;
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        if (activityWeakReference != null && activityWeakReference.get() != null) {
            decorView = (ViewGroup) activityWeakReference.get().getWindow().getDecorView();
        }
        return decorView;
    }

    private HashMap<String, Application.ActivityLifecycleCallbacks> currentActivityLifecycleCallbacks = new HashMap<>();

    /**
     * 注册可以响应当前activity的
     * @param tag
     * @param activityLifecycleCallbacks
     */
    public void registerCurrentActivityLifecycleCallbacks(String tag, Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        currentActivityLifecycleCallbacks.put(tag, activityLifecycleCallbacks);
    }


    public void unRegisterCurrentActivityLifecycleCallbacks(String tag) {
        if (currentActivityLifecycleCallbacks.containsKey(tag))
            currentActivityLifecycleCallbacks.remove(tag);
    }

    public void clear() {
        currentActivityLifecycleCallbacks.clear();
    }

    private Activity currentActivity;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private ActivityUtils(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                currentActivity = activity;
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivityStarted(activity);

            }

            @Override
            public void onActivityResumed(Activity activity) {
                currentActivity = activity;
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivityResumed(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivityPaused(activity);

            }

            @Override
            public void onActivityStopped(Activity activity) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivityStopped(activity );

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivitySaveInstanceState(activity, outState);

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : currentActivityLifecycleCallbacks.values())
                    activityLifecycleCallbacks.onActivityDestroyed(activity);

            }
        });
    }

    private static ActivityUtils activityUtils;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public ActivityUtils build(Application application) {
        if (activityUtils == null)
            return activityUtils = new ActivityUtils(application);
        return activityUtils;
    }

}
