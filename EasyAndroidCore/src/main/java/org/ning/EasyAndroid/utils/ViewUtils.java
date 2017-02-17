package org.ning.EasyAndroid.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 正对View相关的一些utils
 *
 * @author yanni
 *
 */
public class ViewUtils extends ViewCompat{


	public static View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
	}

	@SuppressLint("NewApi")
	public static int getMinimumHeight(View v) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			return v.getMinimumHeight();
		} else {
			try {
				Method method = v.getClass().getDeclaredMethod("getSuggestedMinimumHeight");
				return (int) method.invoke(v);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日下午2:37:07<br>
	 * @param context
	 * @param id
	 * @return
	 */
	public static final int getColor(@NonNull Context context, int id) {
		if (context==null) return 0x888888;
		return ContextCompat.getColor(context, id);
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日下午2:39:24<br>
	 * @param context
	 * @param id
	 * @return
	 */
	public static final Drawable getDrawable(Context context, int id) {
		return ContextCompat.getDrawable(context, id);
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日下午2:40:18<br>
	 * @param context
	 * @return
	 */
	public static File getCodeCacheDir(Context context) {
		return ContextCompat.getCodeCacheDir(context);
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日下午2:42:00<br>
	 * @param context
	 * @param id
	 * @return
	 */
	public static final ColorStateList getColorStateList(Context context, int id) {
		return ContextCompat.getColorStateList(context, id);
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日下午2:38:05<br>
	 * @param v
	 * @return
	 */
	@SuppressLint("NewApi")
	public static int getMinimumWidth(View v) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			return v.getMinimumWidth();
		} else {
			try {
				Method method = v.getClass().getDeclaredMethod("getSuggestedMinimumWidth");
				return (int) method.invoke(v);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}

	/**
	 * 式状态栏透明
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日上午9:53:32<br>
	 * @param activity
	 */
	public static void makeStatusTransparent(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			makeStatusTransparent19(activity);
		} else {

		}
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月27日下午2:38:12<br>
	 * @param activity
	 */
	@TargetApi(19)
	private static void makeStatusTransparent19(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}

	/**
	 * 
	 * @author 颜宁<br>
	 *         2016年6月30日下午6:14:07<br>
	 * @param context
	 * @param intent
	 */
	public static void startActivity(Context context, Intent intent) {
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	
}
