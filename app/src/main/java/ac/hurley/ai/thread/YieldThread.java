package ac.hurley.ai.thread;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 09:58
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class YieldThread extends Thread {
    public YieldThread(String name) {
        super(name);
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            System.out.printf("%s [%d] -----> %d\n", this.getName(), this.getPriority(), i);
            if (i == 2) {
                // 线程礼让。目标线程让出执行权限，让其它线程得以执行，但其它线程是否优先执行是未知的
                Thread.yield();
            }
        }
    }

    static void yieldDemo() {
        YieldThread t1 = new YieldThread("thread-1");
        YieldThread t2 = new YieldThread("thread-2");
        t1.start();
        t2.start();
    }
}
