package ac.hurley.ai.thread.pool;

import android.os.SystemClock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/18 00:04
 *      github  : https://github.com/HurleyJames
 *      desc    : 线程池的测试类
 * </pre>
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(command);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(command);

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        // 2000ms之后再执行command
        scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
        // 延迟10ms后，每隔1000ms再执行一次command
        scheduledThreadPool.scheduleAtFixedRate(command, 10, 1000, TimeUnit.MILLISECONDS);

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(command);
    }


    /**
     * FixedThreadPool
     * <p>
     * 是一种线程数量固定的线程池。当线程处于空闲状态时，就不会被回收，除非线程池关闭了
     * 只有核心线程，没有超时机制，没有大小限制，能更快地响应外界的请求
     *
     * @param nThreads
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * CachedThreadPool
     * <p>
     * 线程数量不固定的线程池，只有非核心线程，最大线程数为Integer.MAX_VALUE，即任意大
     *
     * @return
     */
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    /**
     * ScheduledExecutorSize
     * <p>
     * 核心线程数是不固定的，当非核心线程被闲置时会被立即回收
     * 主要用于执行定时任务和具有固定周期的重复任务
     *
     * @param corePoolSize
     * @return
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    /**
     * SingleThreadExecutor
     * <p>
     * 线程池内部只有一个核心线程，它确保所有的任务都在同一个线程中按顺序执行
     *
     * @return
     */
    public static ExecutorService newSingleThread() {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }
}
