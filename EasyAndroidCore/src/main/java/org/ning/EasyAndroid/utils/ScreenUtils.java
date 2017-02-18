package org.ning.EasyAndroid.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by yanni on 2017/2/17.
 */

public class ScreenUtils {
    /**
     * 获取通知栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


    /**
     * 返回屏幕寬度的數據
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        final WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 获得手机屏幕宽高
        DisplayMetrics dm1 = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm1);
        return dm1.widthPixels;
    }

    /**
     * 返回屏幕高度的數據
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        final WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 获得手机屏幕宽高
        DisplayMetrics dm1 = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm1);
        return dm1.heightPixels;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     * @author 颜宁<br>
     * 2016年3月31日上午11:22:12<br>
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     * @author 颜宁<br>
     * 2016年3月31日上午11:22:19<br>
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     * @author 颜宁<br>
     * 2016年5月31日下午4:28:42<br>
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
