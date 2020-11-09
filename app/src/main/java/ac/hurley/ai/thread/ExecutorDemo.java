package ac.hurley.ai.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 11:38
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class ExecutorDemo {

    private static final int MAX = 10;

    public static void main(String[] args) {
        try {
            // 启动了含有3个线程的线程池
            fixedThreadPool(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建固定数量的线程池
     *
     * @param size
     * @throws CancellationException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void fixedThreadPool(int size) throws CancellationException,
            ExecutionException, InterruptedException {
        // 创建固定数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < MAX; i++) {
            Future<Integer> task = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("执行线程：" + Thread.currentThread().getName());
                    return fibc(40);
                }
            });
            // 获取结果
            System.out.println("第 " + i + "次计算，结果：" + task.get());
        }

    }

    /**
     * 当来了一个新的任务，并且没有空闲线程可用，此时必须马上创建一个线程来立即执行任务
     * 就可以通过newCachedThreadPool函数来实现
     *
     * @throws CancellationException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void newCachedThreadPool() throws CancellationException,
            ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < MAX; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("执行线程：" + Thread.currentThread().getName() + "，结果：" + fibc(20));
                }
            });
        }
    }

    /**
     * 创建定时执行的线程池
     *
     * @throws CancellationException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void scheduledThreadPool() throws CancellationException, ExecutionException, InterruptedException {
        // 总共有3个线程
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        // 以下指定了两个定时任务，因此就有2个线程来完成

        // initialDelay是第一次延迟的时间，period是执行的周期
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getName() + "，定时计算：");
                System.out.println("结果：" + fibc(30));
            }
        }, 1, 2, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getName() + "，定时计算2：");
                System.out.println("结果：" + fibc(40));
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    /**
     * 效率低下的斐波那契数列，是一个耗时操作
     *
     * @param num
     * @return
     */
    private static int fibc(int num) {
        if (num == 0 || num == 1) {
            return num;
        }
        return fibc(num - 1) + fibc(num - 2);
    }
}
