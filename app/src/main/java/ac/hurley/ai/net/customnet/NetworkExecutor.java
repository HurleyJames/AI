package ac.hurley.ai.net.customnet;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 10:24
 *      github  : https://github.com/HurleyJames
 *      desc    : 从网络请求队列中循环读取数据并且执行
 * </pre>
 */
public final class NetworkExecutor extends Thread {

    /**
     * 网络请求队列
     */
    private BlockingQueue<Request<?>> mRequestQueue;
    /**
     * 网络请求栈
     */
    private HttpStack mHttpStack;
    /**
     * 结果分发器，将结果投递到主线程
     */
    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();
    /**
     * 请求缓存
     */
    private static Cache<String, Response> mReqCache = new LruMemCache();

    /**
     * 是否停止
     */
    private boolean isStop = false;

    public NetworkExecutor(BlockingQueue<Request<?>> queue, HttpStack httpStack) {
        mRequestQueue = queue;
        mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                final Request<?> request = mRequestQueue.take();
                if (request.isCanceled()) {
                    Log.d("###", "取消执行了");
                    continue;
                }
                Response response = null;
                if (isUseCache(request)) {
                    // 从缓存中去读
                    response = mReqCache.get(request.getUrl());
                } else {
                    response = mHttpStack.performRequest(request);
                    if (request.shouldCache() && isSuccess(response)) {
                        mReqCache.put(request.getUrl(), response);
                    }
                }

                // 分发请求结果
                mResponseDelivery.deliveryResponse(request, response);
            }
        } catch (InterruptedException e) {
            Log.i("", "请求分发器退出");
        }
    }

    /**
     * 执行是否成功
     *
     * @param response
     * @return
     */
    private boolean isSuccess(Response response) {
        // response返回200状态码，说明请求成功
        return response != null && response.getStatusCode() == 200;
    }

    /**
     * 是否从缓存中读取
     *
     * @param request
     * @return
     */
    private boolean isUseCache(Request<?> request) {
        return request.shouldCache() && mReqCache.get(request.getUrl()) != null;
    }

    public void quit() {
        isStop = true;
        interrupt();
    }
}
