package ac.hurley.ai.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 18:30
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class SemaphoreTest {
    static int time = 0;

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获得许可
                        semaphore.acquire();
                        System.out.println("剩余许可：" + semaphore.availablePermits());
                        Thread.sleep(1000);
                        // 离开时释放许可
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
