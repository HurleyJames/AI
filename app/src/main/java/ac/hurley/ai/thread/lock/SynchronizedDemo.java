package ac.hurley.ai.thread.lock;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 16:27
 *      github  : https://github.com/HurleyJames
 *      desc    : synchronized同步锁Demo
 * </pre>
 */
public class SynchronizedDemo {


    /**
     * 前两种方法锁的是对象，后两种锁的是class对象
     */

    /**
     * 同步方法
     */
    public synchronized void syncMethod() {

    }

    public void syncThis() {
        /**
         * 同步块
         */
        synchronized (this) {

        }
    }

    public void syncClassMethod() {
        /**
         * 同步类对象
         */
        synchronized (SynchronizedDemo.class) {

        }
    }

    /**
     * 同步静态方法
     */
    public synchronized static void syncStaticMethod() {

    }

}
