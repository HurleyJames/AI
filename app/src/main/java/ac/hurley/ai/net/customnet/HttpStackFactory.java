package ac.hurley.ai.net.customnet;

import android.os.Build;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 10:25
 *      github  : https://github.com/HurleyJames
 *      desc    : 根据API版本选择HttpClient或者HttpURLConnection
 * </pre>
 */
public final class HttpStackFactory {

    /**
     * 定义Android版本
     */
    private static final int GINGERBREAD_SDK_NUM = 9;

    public static HttpStack createHttpStack() {
        int runtimeSDKApi = Build.VERSION.SDK_INT;
        // 如果版本号大于这个版本
        if (runtimeSDKApi >= GINGERBREAD_SDK_NUM) {
            // 使用HttpURLConnection()来创建连接
            return new HttpUrlConnStack();
        }
        // 使用HttpClient()来创建连接
        return new HttpClientStack();
    }
}
