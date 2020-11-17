package ac.hurley.ai.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/15 23:50
 *      github  : https://github.com/HurleyJames
 *      desc    : 图片压缩类
 * </pre>
 */
public class ImageResizer {

    private static final String TAG = "ImageResizer";

    public ImageResizer() {

    }

    /**
     * 从resource中进行decode
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 检查尺寸
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 计算大小
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // decode
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从FileDescriptor中进行decode
     *
     * @param fd
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampleBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        // 计算大小
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // decode
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 计算尺寸
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        // 原生尺寸大小
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w=" + width + " h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
}
