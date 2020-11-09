package ac.hurley.ai.thread;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 09:36
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class TestThread {

    private static Object sLockObject = new Object();

    static void waitAndNotifyAll() {
        System.out.println("主线程运行");
        Thread thread = new WaitThread();
        thread.start();
        long startTime = System.currentTimeMillis();
        try {
            synchronized (sLockObject) {
                System.out.println("主线程等待");
                // 线程进入等待
                sLockObject.wait();
            }
        } catch (Exception e) {

        }
        long timeMs = (System.currentTimeMillis() - startTime);
        System.out.println("主线程继续 --> 等待耗时：" + timeMs + "ms");
    }

    /**
     * 创建一个等待线程
     */
    static class WaitThread extends Thread {
        @Override
        public void run() {
            try {
                synchronized (sLockObject) {
                    // 调用sleep函数沉睡3秒
                    Thread.sleep(3000);
                    // 重新唤醒
                    sLockObject.notifyAll();
                }
            } catch (Exception e) {

            }
        }
    }
}
