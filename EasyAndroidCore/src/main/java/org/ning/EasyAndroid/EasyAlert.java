package org.ning.EasyAndroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import org.ning.EasyAndroid.utils.ViewUtils;
import org.ning.EasyObserver.core.EasyAcceptor;

/**
 * Created by yanni on 2017/2/10.
 */

public abstract class EasyAlert extends PopupWindow {
    private View contentLayout;
    private Context context;

    public Context getContext() {
        return context;
    }

    private int timeAnimation = 300;

    public void setTimeAnimation(int timeAnimation) {
        this.timeAnimation = timeAnimation;
    }

    public EasyAlert(Context context) {
        super(new LinearLayout(context), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        // TODO Auto-generated constructor stub
        {

            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);

            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        this.context = context;
//        setTouchable(true);
//        setFocusable(true);
        setAnimationStyle(0);
        super.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                if (dismissListener != null) {
                    dismissListener.onDismiss();
                }
            }
        });
        setBackgroundResourse(R.color.easy_alert_bg);
        LinearLayout frameLayout = ((LinearLayout) getContentView());
        LinearLayout.LayoutParams layoutParams = getLayoutParams();
        layoutParams.gravity = getLayoutGravity();
        frameLayout.setLayoutParams(getLayoutParams());
        frameLayout.setGravity(getLayoutGravity());
        frameLayout.addView(contentLayout = onCreateContentView(context), layoutParams);
        setOutSideClickable(true);
    }

    public boolean isOutSideClickable() {
        return outSideClickable;
    }

    public void setOutSideClickable(final boolean outSideClickable) {
        this.outSideClickable = outSideClickable;
        if (outSideClickable)
            getContentView().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View paramView) {
                    // TODO Auto-generated method stub
                    if (isOutSideClickable())
                        dismiss();
                }
            });
        else
            getContentView().setOnClickListener(null);
    }

    protected LinearLayout.LayoutParams getLayoutParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void setBackgroundResourse(@DrawableRes int resId) {
        setBackgroundDrawable(ContextCompat.getDrawable(context, resId));
        getContentView().setBackgroundResource(resId);
    }

    private boolean outSideClickable = true;

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        if (contentLayout != null) {
            dissmissAnimation(contentLayout);
        }
    }

    private void doDismiss() {
        super.dismiss();
    }

    protected int getLayoutGravity() {
        return Gravity.BOTTOM;
    }

    protected View getAnimationContent(View contentLayout) {
        return contentLayout;
    }

    protected Techniques getDismissAnimation() {
        return Techniques.TakingOff;
    }

    protected Techniques getShowAnimation() {
        return Techniques.Landing;
    }

    protected void dissmissAnimation(View contentLayout) {
        YoYo.with(getDismissAnimation()).duration(timeAnimation).withListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub
                doDismiss();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        }).playOn(getAnimationContent(contentLayout));
    }

    protected void showAnimation(final View contentLayout) {
        getAnimationContent(contentLayout).setVisibility(View.INVISIBLE);
        new EasyThread.SubThread() {

            @Override
            public void onRunning(EasyThread.SubThread subThread) {
                try {
                    sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new EasyThread.UIThread(getContext()) {
                    @Override
                    public void onRunning(EasyThread.UIThread uiThread, Context context) {
                        YoYo.with(getShowAnimation()).duration(timeAnimation).withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                getAnimationContent(contentLayout).setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (showListener != null) {
                                    showListener.accept(true);
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).playOn(getAnimationContent(contentLayout));
                    }
                }.start();
            }
        }.start();

    }

    private EasyAcceptor.Acceptor1<Boolean> showListener;

    public void setShowListener(EasyAcceptor.Acceptor1<Boolean> showListener) {
        this.showListener = showListener;
    }

    protected abstract View onCreateContentView(@NonNull Context context);

    private OnDismissListener dismissListener;

    public void setOnDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    /**
     * @param activity
     * @author 颜宁<br>
     * 2016年5月25日上午11:00:27<br>
     */
    public void showAtActivity(Activity activity) {
        // TODO Auto-generated method stub
        if (!activity.isFinishing())
            showAtLocation(ViewUtils.getRootView(activity), Gravity.TOP, 0, 0);
        if (activity instanceof EasyActivity) {
            EasyActivity easyActivity = (EasyActivity) activity;
            easyActivity.registerListener(getClass().toString(), new EasyActivity.OnFinishListener() {
                @Override
                public void finish(EasyActivity activity) {
                    if (isShowing()) {
                        dismiss();
                    }
                }
            });
        }
    }

    /**
     * @param context
     * @author 颜宁<br>
     * 2016年5月25日上午11:00:27<br>
     */
    public void showAtContext(Context context) {
        // TODO Auto-generated method stub
        if (context instanceof Activity)
            showAtActivity((Activity) context);
    }

    @Override
    public void showAsDropDown(View anchor) {
        // TODO Auto-generated method stub
        beforeShow();
        try {
            super.showAsDropDown(anchor);
            if (contentLayout != null) {
                showAnimation(contentLayout);
            }
            afterShow();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        // TODO Auto-generated method stub
        beforeShow();
        try {
            super.showAsDropDown(anchor, xoff, yoff);
            if (contentLayout != null) {
                showAnimation(contentLayout);
            }
            afterShow();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(21)
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        // TODO Auto-generated method stub
        beforeShow();
        try {

            super.showAsDropDown(anchor, xoff, yoff, gravity);
            if (contentLayout != null) {
                showAnimation(contentLayout);
            }
            afterShow();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        // TODO Auto-generated method stub
        beforeShow();
        try {
            super.showAtLocation(parent, gravity, x, y);
            if (contentLayout != null) {
                showAnimation(contentLayout);
            }
        } catch (Exception e) {
            Log.v(getClass().toString(), "view关闭异常\n" + e.getClass());
        }
        afterShow();
    }

    public void beforeShow() {

    }

    public void afterShow() {

    }
}
