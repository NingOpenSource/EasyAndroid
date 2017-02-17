package org.ning.EasyAndroid;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Created by yanni on 2017/2/10.
 */
public class EasyThread{
    /**
     * 普通的子线程
     */
    public static abstract class SubThread extends Thread {
        @Override
        public final void run() {
            super.run();
            onRunning(this);
        }

        /**
         * 线程的内容
         * @param subThread
         */
        public abstract void onRunning(SubThread subThread);
    }

    public static class DefaultSubThread extends SubThread{

        @Override
        public void onRunning(SubThread subThread) {

        }
    }

    /**
     * UI线程
     */
    public abstract static class UIThread extends Thread {
        private Context context;
        private Handler handler;
        public UIThread(@NonNull Context context) {
            this.context = context;
            if (context==null)
                throw new NullPointerException("The context must not be a null object!");
            handler = new Handler(context.getMainLooper());
        }

        public final Context getContext() {
            return context;
        }

        @Override
        public final void run() {
            super.run();
            onRunning(this,context);
        }

        /**
         * 线程的内容
         * @param uiThread
         */
        public abstract void onRunning(UIThread uiThread,Context context);
        @Override
        public final synchronized void start() {
            handler.post(this);
        }
    }

    UIThread uiThread=new UIThread(null) {
        @Override
        public void onRunning(UIThread uiThread, Context context) {

        }
    };
}
