package org.ning.EasyAndroid.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * <P>可以被scrollview等滚动布局所嵌套加载的gridview布局</P>
 * Created by yanni on 2017/2/14.
 */
public class NestedGridView extends GridView {
    public NestedGridView(Context context) {
        super(context);
    }

    public NestedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST));
    }
}
