package ac.hurley.ai.thread;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 09:44
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class JoinThread {

    /*
     * join()函数的作用是：等待目标线程执行完毕之后再继续执行
     */

    static void joinDemo() {
        Worker worker1 = new Worker("work-1");
        Worker worker2 = new Worker("work-2");
        worker1.start();
        System.out.println("启动线程1");
        try {
            // 调用worker1.join()，主线程会阻塞直到worker1执行完成
            worker1.join();
            System.out.println("启动线程2");
            // 启动线程2，调用join()，主线程会阻塞直到worker2执行完成
            worker2.start();
            worker2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程继续执行");
    }

    static class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 显示正处于的线程名
            System.out.println("work in " + getName());
        }
    }
}
