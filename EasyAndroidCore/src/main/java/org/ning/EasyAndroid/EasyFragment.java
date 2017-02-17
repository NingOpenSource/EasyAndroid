package org.ning.EasyAndroid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanni on 2017/2/10.
 */

public abstract class EasyFragment extends Fragment {

    private HashMap<String, OnStartListener> stringOnStartListenerMap = new HashMap<>();

    public interface FragmentListener extends EasyListener {

    }

    /**
     * 注册扩展的监听器
     *
     * @param clazz
     * @param listener
     * @param <T>
     */
    public final <T extends FragmentListener> void registerListener(Class clazz, T listener) {
        registerListener(clazz.toString(), listener);
    }

    /**
     * 注册扩展的监听器
     *
     * @param tag
     * @param listener
     * @param <T>
     */
    public final <T extends FragmentListener> void registerListener(String tag, T listener) {
        if (listener instanceof OnStartListener) {
            stringOnStartListenerMap.put(tag, (OnStartListener) listener);
        }
    }

    /**
     * 取消注册的监听器，（一般写在监听的方法中）
     *
     * @param tag
     * @param listenerClass
     * @param <T>
     */
    public final <T extends FragmentListener> void unRegisterListener(String tag, Class<T> listenerClass) {
        if (listenerClass == OnStartListener.class) {
            if (stringOnStartListenerMap.containsKey(tag)) stringOnStartListenerMap.remove(tag);
        }
    }

    public static abstract class OnStartListener implements FragmentListener {
        public abstract void onStart(EasyFragment easyFragment);
    }

    private View viewRoot;

    public View getViewRoot() {
        return viewRoot;
    }

    /**
     * 返回加载布局的资源ID
     *
     * @return
     */
    @LayoutRes
    protected abstract int loadLayout();
    /**
     * fragment的创建
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(loadLayout(), container, false);
        return viewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();

        for (String key : stringOnStartListenerMap.keySet()) {
            stringOnStartListenerMap.get(key).onStart(this);
        }
        afterLoadLayout(viewRoot);
    }

    /**
     * 删除子view
     *
     * @param view
     */
    public void removeView(View view) {
        if (getViewRoot() instanceof ViewGroup) {
            ((ViewGroup) getViewRoot()).removeView(view);
        }
    }

    public static EasyFragment newInstance(Context context, String fname, @Nullable Bundle args) {
        return (EasyFragment) instantiate(context, fname, args);
    }

    private static Map<Class<? extends EasyFragment>, EasyFragment> classRHCFragmentMap = new HashMap<>();

    public static <T extends EasyFragment> T newInstance(Context context, Class<T> fname) {
        T t = null;
        if (classRHCFragmentMap.containsKey(fname)) {
            t = (T) classRHCFragmentMap.get(fname);
        } else {
            t = (T) T.newInstance(context, fname.getCanonicalName(), null);
            classRHCFragmentMap.put(fname, t);
        }
        return t;
    }

    protected final View findViewById(@IdRes int resId) {
        return viewRoot.findViewById(resId);
    }

    /**
     * 加载布局之后的操作
     *
     * @param viewRoot
     */
    protected abstract void afterLoadLayout(View viewRoot);

    public EasyActivity getEasyActivity() {
        if (getActivity() instanceof EasyActivity)
            return (EasyActivity) getActivity();
        return null;
    }
}
