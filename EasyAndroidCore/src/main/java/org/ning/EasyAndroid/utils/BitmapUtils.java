package org.ning.EasyAndroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 *
 * @author yanni
 */
public class BitmapUtils {
    /**
     * 处理图片
     *
     * @param bm           所要转换的bitmap
     * @param newWidth 新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        // 通知回收bitmap
//		if (bm != null) {
//			bm.recycle();
//			System.gc();
//		}
        return newbm;
    }

    public static Bitmap zoomImgNoRecycle(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    /**
     * Bitmap质量压缩
     *
     * @param image
     * @param maxSize 最大的位图大小单位KB
     * @return
     * @author 颜宁<br>
     * 2016年5月20日下午2:26:51<br>
     */
    public static Bitmap compressImage(Bitmap image, int maxSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos1);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(baos1.toByteArray()), null, null);
    }

    /**
     * 获取view在当前界面上所显示的内容
     *
     * @param view
     * @return
     * @author 颜宁<br>
     * 2016年5月18日下午5:49:02<br>
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    // 压缩图片尺寸
    public static Bitmap compressBySize(String pathName, int targetWidth, int targetHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        opts.inSampleSize = 1;
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        // 设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }

    // 存储进SD卡
    public static void saveFile(Bitmap bm, String fileName) throws Exception {
        File dirFile = new File(fileName);
        // 检测图片是否存在
        if (dirFile.exists()) {
            dirFile.delete(); // 删除原图片
        }
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        // 100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        bm.recycle();
        bm = null;
    }

    /***
     * 将drawable转bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitamp(Drawable drawable) {
        Bitmap bitmap;
        BitmapDrawable bd = (BitmapDrawable) drawable;
        bitmap = bd.getBitmap();
        return bitmap;
    }

}
