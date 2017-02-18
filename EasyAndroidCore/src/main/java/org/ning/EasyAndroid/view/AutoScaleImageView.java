package org.ning.EasyAndroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * <P>按照比例自动缩放的imageView</P>
 * Created by YanNing on 2016/12/22.
 */
public class AutoScaleImageView extends ImageView {
    public AutoScaleImageView(Context context) {
        super(context);
    }

    public AutoScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoScaleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * true使用width进行自适应,false使用height进行自适应
     */
    private boolean isScaleHeight = true;
    /**
     * 宽度比例值
     */
    private float widthUnit=-1;
    /**
     * 高度的比例值
     */
    private float heightUnit=-1;

    /**
     * @param widthUnit  宽度比例值
     * @param heightUnit 高度的比例值
     * @param isScaleHeight true使用height缩放,false使用width进行缩放
     */
    public void setImageScale(float widthUnit, float heightUnit, boolean isScaleHeight) {
        this.widthUnit = widthUnit;
        this.heightUnit = heightUnit;
        this.isScaleHeight = isScaleHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthUnit==-1&&heightUnit==-1){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        if (isScaleHeight) {
            sizeHeight = (int) (sizeWidth * (heightUnit / widthUnit));
        } else {
            sizeWidth = (int) (sizeHeight * (widthUnit / heightUnit));
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(sizeWidth,modeWidth), MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight));
        setMeasuredDimension(sizeWidth, sizeHeight);
//        if (isInEditMode()) {
//            return;
//        }
//        getLayoutParams().height = sizeHeight;
//        getLayoutParams().width = sizeWidth;
    }
}
