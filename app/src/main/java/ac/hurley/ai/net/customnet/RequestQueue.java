package ac.hurley.ai.net.customnet;

import android.util.Log;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 10:15
 *      github  : https://github.com/HurleyJames
 *      desc    : 请求队列，使用优先队列，使得请求可以按照优先级来处理
 * </pre>
 */
public final class RequestQueue {

    /**
     * 线程安全的请求队列
     */
    private PriorityBlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<Request<?>>();
    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);
    /**
     * 默认的核心数为CPU数加1
     */
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * CPU核心数 + 1个分发线程数
     */
    private int mDispatcherNums = DEFAULT_CORE_NUMS;
    /**
     * 执行网络请求的线程
     */
    private NetworkExecutor[] mDispatchers = null;
    /**
     * Http请求的真正执行者
     */
    private HttpStack mHttpStack;

    protected RequestQueue(int coreNums, HttpStack httpStack) {
        mDispatcherNums = coreNums;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    /**
     * 启动网络执行器
     */
    private final void startNetworkExecutor() {
        mDispatchers = new NetworkExecutor[mDispatcherNums];
        for (int i = 0; i < mDispatcherNums; i++) {
            mDispatchers[i] = new NetworkExecutor(mRequestQueue, mHttpStack);
            mDispatchers[i].start();
        }
    }

    /**
     * 启动网络请求的线程
     */
    public void start() {
        // 先停止已经启动的线程
        stop();
        startNetworkExecutor();
    }

    /**
     * 停止已经启动的线程
     */
    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].quit();
            }
        }
    }

    /**
     * 往消息队列中添加请求
     *
     * @param request
     */
    public void addRequest(Request<?> request) {
        if (!mRequestQueue.contains(request)) {
            // 为请求设置序列号
            request.setSerialNumber(this.generateSerialNumber());
            mRequestQueue.add(request);
        } else {
            Log.d("", "请求队列中已经含有");
        }
    }

    /**
     * 生成序列号
     *
     * @return
     */
    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
