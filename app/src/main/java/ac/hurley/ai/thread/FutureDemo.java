package ac.hurley.ai.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 10:14
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class FutureDemo {

    /**
     * 线程池
     */
    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        try {
            futureWithRunnable();
            futureWithCallable();
            futureTask();
        } catch (Exception e) {

        }
    }

    /**
     * 向线程池中提交Runnable对象
     * 没有返回值，future没有数据
     * 结果：null，因为该函数无返回值
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void futureWithRunnable() throws InterruptedException, ExecutionException {
        Future<?> result = mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                fibc(20);
            }
        });

        System.out.println("future result from runnable : " + result.get());
    }

    /**
     * 向线程池中提交Callable对象，有返回值
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void futureWithCallable() throws InterruptedException, ExecutionException {
        Future<Integer> result2 = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });
    }

    /**
     * 提交FutureTask对象
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void futureTask() throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<>(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return fibc(20);
                    }
                });
        mExecutor.submit(futureTask);
        System.out.println("future result from futureTask : " + futureTask.get());
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
