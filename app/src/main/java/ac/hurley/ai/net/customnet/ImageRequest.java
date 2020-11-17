package ac.hurley.ai.net.customnet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/14 20:46
 *      github  : https://github.com/HurleyJames
 *      desc    : 图像Image请求类
 * </pre>
 */
public class ImageRequest extends Request<Bitmap> {

    /**
     * 携带请求方式、URL、回调接口的参数
     *
     * @param method
     * @param url
     * @param listener
     */
    public ImageRequest(HttpMethod method, String url, RequestListener<Bitmap> listener) {
        super(method, url, listener);
    }

    @Override
    public Bitmap parseResponse(Response response) {
        return BitmapFactory.decodeByteArray(response.rawData, 0, response.rawData.length);
    }
}
