package org.ning.EasyAndroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yanni on 2017/2/10.
 */

public abstract class EasyActivity extends AppCompatActivity {


    private HashMap<String, OnFinishListener> stringOnFinishListenerMap = new HashMap<>();
    private HashMap<String, OnResumeListener> stringOnResumeListenerHashMap = new HashMap<>();
    private HashMap<String, BeforeCreateListener> stringBeforeCreateListenerHashMap = new HashMap<>();
    private HashMap<String, OnPauseListener> stringOnPauseListenerHashMap = new HashMap<>();
    private HashMap<String, OnNewIntentListener> stringOnNewIntentListenerHashMap = new HashMap<>();
    private HashMap<String, OnAddContentViewListener> stringOnAddContentViewListenerHashMap = new HashMap<>();
    private HashMap<String, OnActivityResultListener> stringOnActivityResultListenerHashMap = new HashMap<>();
    private HashMap<String, OnStartListener> stringOnStartListenerHashMap = new HashMap<>();
    private HashMap<String, OnRequestPermissionsResultListener> stringOnRequestPermissionsResultListenerHashMap = new HashMap<>();
    private HashMap<String, OnAttachedToWindowListener> stringOnAttachedToWindowListenerHashMap = new HashMap<>();

    public interface ActivityListener extends EasyListener {

    }

    /**
     * 注册扩展的监听器
     * @param clazz
     * @param listener
     * @param <T>
     */
    public final <T extends ActivityListener> void registerListener(Class clazz, T listener) {
        registerListener(clazz.toString(),listener);
    }
    /**
     * 注册扩展的监听器
     * @param tag
     * @param listener
     * @param <T>
     */
    public final <T extends ActivityListener> void registerListener(String tag, T listener) {
        if (listener instanceof OnFinishListener) {
            stringOnFinishListenerMap.put(tag, (OnFinishListener) listener);
        } else if (listener instanceof OnResumeListener) {
            stringOnResumeListenerHashMap.put(tag, (OnResumeListener) listener);
        } else if (listener instanceof BeforeCreateListener) {
            stringBeforeCreateListenerHashMap.put(tag, (BeforeCreateListener) listener);
        } else if (listener instanceof OnActivityResultListener) {
            stringOnActivityResultListenerHashMap.put(tag, (OnActivityResultListener) listener);
        } else if (listener instanceof OnNewIntentListener) {
            stringOnNewIntentListenerHashMap.put(tag, (OnNewIntentListener) listener);
        } else if (listener instanceof OnPauseListener) {
            stringOnPauseListenerHashMap.put(tag, (OnPauseListener) listener);
        } else if (listener instanceof OnAddContentViewListener) {
            stringOnAddContentViewListenerHashMap.put(tag, (OnAddContentViewListener) listener);
        } else if (listener instanceof OnStartListener) {
            stringOnStartListenerHashMap.put(tag, (OnStartListener) listener);
        } else if (listener instanceof OnRequestPermissionsResultListener) {
            stringOnRequestPermissionsResultListenerHashMap.put(tag, (OnRequestPermissionsResultListener) listener);
        } else if (listener instanceof OnAttachedToWindowListener) {
            stringOnAttachedToWindowListenerHashMap.put(tag, (OnAttachedToWindowListener) listener);
        }
    }
    public final <T extends ActivityListener> void unRegisterListener(Class clazz, Class<T> listenerClass) {
        unRegisterListener(clazz.toString(),listenerClass);
    }
    /**
     * 取消注册的监听器，（一般写在监听的方法中）
     * @param tag
     * @param listenerClass
     * @param <T>
     */
    public final <T extends ActivityListener> void unRegisterListener(String tag, Class<T> listenerClass) {
        if (listenerClass == OnFinishListener.class) {
            if (stringOnFinishListenerMap.containsKey(tag)) stringOnFinishListenerMap.remove(tag);
        } else if (listenerClass == OnResumeListener.class) {
            if (stringOnResumeListenerHashMap.containsKey(tag))
                stringOnResumeListenerHashMap.remove(tag);
        } else if (listenerClass == BeforeCreateListener.class) {
            if (stringBeforeCreateListenerHashMap.containsKey(tag))
                stringBeforeCreateListenerHashMap.remove(tag);
        } else if (listenerClass == OnActivityResultListener.class) {
            if (stringOnActivityResultListenerHashMap.containsKey(tag))
                stringOnActivityResultListenerHashMap.remove(tag);
        } else if (listenerClass == OnNewIntentListener.class) {
            if (stringOnNewIntentListenerHashMap.containsKey(tag))
                stringOnNewIntentListenerHashMap.remove(tag);
        } else if (listenerClass == OnPauseListener.class) {
            if (stringOnPauseListenerHashMap.containsKey(tag))
                stringOnPauseListenerHashMap.remove(tag);
        } else if (listenerClass == OnAddContentViewListener.class) {
            if (stringOnAddContentViewListenerHashMap.containsKey(tag))
                stringOnAddContentViewListenerHashMap.remove(tag);
        } else if (listenerClass == OnStartListener.class) {
            if (stringOnStartListenerHashMap.containsKey(tag))
                stringOnStartListenerHashMap.remove(tag);
        } else if (listenerClass == OnRequestPermissionsResultListener.class) {
            if (stringOnRequestPermissionsResultListenerHashMap.containsKey(tag))
                stringOnRequestPermissionsResultListenerHashMap.remove(tag);
        } else if (listenerClass == OnAttachedToWindowListener.class) {
            if (stringOnAttachedToWindowListenerHashMap.containsKey(tag))
                stringOnAttachedToWindowListenerHashMap.remove(tag);
        }
    }

    //################################################################################################################
    public static abstract class OnFinishListener implements ActivityListener {
        public abstract void finish(EasyActivity easyActivity);
    }

    public static abstract class OnResumeListener implements ActivityListener {
        public abstract void onResume(EasyActivity easyActivity);
    }

    public static abstract class BeforeCreateListener implements ActivityListener {
        public abstract void onCreate(EasyActivity easyActivity, @Nullable Bundle savedInstanceState);
    }

    public static abstract class OnStartListener implements ActivityListener {
        public abstract void onStart(EasyActivity easyActivity);
    }

    public static abstract class OnRequestPermissionsResultListener implements ActivityListener {
        public abstract void onRequestPermissionsResultListener(EasyActivity easyActivity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }

    public static abstract class OnPauseListener implements ActivityListener {
        public abstract void onPause(EasyActivity easyActivity);
    }

    public static abstract class OnNewIntentListener implements ActivityListener {
        public abstract void onNewIntent(EasyActivity easyActivity, Intent intent);
    }

    public static abstract class OnAddContentViewListener implements ActivityListener {
        public abstract void addContentView(EasyActivity easyActivity, View view, ViewGroup.LayoutParams params);
    }

    public static abstract class OnAttachedToWindowListener implements ActivityListener {
        public abstract void onAttachedToWindow(EasyActivity easyActivity);
    }

    public static abstract class OnActivityResultListener implements ActivityListener {
        public abstract void onActivityResult(EasyActivity easyActivity, int requestCode, int resultCode, Intent data);
    }



    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        for (String key : new ArrayList<>(stringBeforeCreateListenerHashMap.keySet())) {
            stringBeforeCreateListenerHashMap.get(key).onCreate(this, savedInstanceState);
        }
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (String key : new ArrayList<>(stringOnPauseListenerHashMap.keySet())) {
            stringOnPauseListenerHashMap.get(key).onPause(this);
        }
    }



    @Override
    public void finish() {
        for (String key : new ArrayList<>(stringOnFinishListenerMap.keySet())) {
            stringOnFinishListenerMap.get(key).finish(this);
        }
        super.finish();
    }
//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        for (String key : new ArrayList<>(stringOnActivityResultListenerHashMap.keySet())) {
            stringOnActivityResultListenerHashMap.get(key).onActivityResult(this, requestCode, resultCode, data);
        }
    }


    // }
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        for (String key : new ArrayList<>(stringOnNewIntentListenerHashMap.keySet())) {
            stringOnNewIntentListenerHashMap.get(key).onNewIntent(this, intent);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        for (String key : new ArrayList<>(stringOnStartListenerHashMap.keySet())) {
            stringOnStartListenerHashMap.get(key).onStart(this);
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        for (String key : new ArrayList<>(stringOnResumeListenerHashMap.keySet())) {
            stringOnResumeListenerHashMap.get(key).onResume(this);
        }
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (String key : new ArrayList<>(stringOnAttachedToWindowListenerHashMap.keySet())) {
            stringOnAttachedToWindowListenerHashMap.get(key).onAttachedToWindow(this);
        }
    }

    /**
     * 返回activity的布局资源ID
     *
     * @return
     */
    @LayoutRes
    protected abstract int getContentLayoutRes();

    /**
     * 初始布局资源之后的操作
     */
    public abstract void afterInitContentLayout();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        {
            if (getContentLayoutRes() > 0) {
                setContentView(getContentLayoutRes());
            }
            afterInitContentLayout();
        }
    }

    public EasyActivity getActivity(){
        return this;
    }
}
