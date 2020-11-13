package ac.hurley.ai.net.customnet;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 10:35
 *      github  : https://github.com/HurleyJames
 *      desc    : 请求结果传递给UI主线程
 * </pre>
 */
public class ResponseDelivery implements Executor {

    /**
     * 关联主线程消息队列的Handler
     */
    Handler mResponseHandler = new Handler(Looper.getMainLooper());

    /**
     * 处理请求结果，在UI主线程中执行
     *
     * @param request
     * @param response
     */
    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable respRunnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };

        execute(respRunnable);
    }


    @Override
    public void execute(Runnable command) {
        mResponseHandler.post(command);
    }
}
