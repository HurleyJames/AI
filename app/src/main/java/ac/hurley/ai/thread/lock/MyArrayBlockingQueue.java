package ac.hurley.ai.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 16:35
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class MyArrayBlockingQueue<T> {
    /**
     * 数据数组
     */
    private final T[] items;
    /**
     * 锁
     */
    private final Lock lock = new ReentrantLock();
    /**
     * 队满的条件
     */
    private Condition notFull = lock.newCondition();
    /**
     * 队空的条件
     */
    private Condition notEmpty = lock.newCondition();
    /**
     * 队列的头部索引
     */
    private int head;
    /**
     * 队列的尾部索引
     */
    private int tail;
    /**
     * 数据的个数
     */
    private int count;

    public static void main(String[] args) {
        MyArrayBlockingQueue<Integer> aQueue = new MyArrayBlockingQueue<>();
        aQueue.put(3);
        aQueue.put(24);
        for (int i = 0; i < 5; i++) {
            System.out.println(aQueue.take());
        }
    }


    public MyArrayBlockingQueue(int maxSize) {
        items = (T[]) new Object[maxSize];
    }

    public MyArrayBlockingQueue() {
        this(10);
    }

    public void put(T t) {
        lock.lock();
        try {
            while (count == getCapacity()) {
                System.out.println("数据已满，等待");
                notFull.await();
            }
            items[tail] = t;
            if (++tail == getCapacity()) {
                tail = 0;
            }
            ++count;
            // 唤醒等待数据的线程
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("还没有数据，请等待");
                notEmpty.await();
            }
            T ret = items[head];
            items[head] = null;
            if (++head == getCapacity()) {
                head = 0;
            }
            --count;
            // 唤醒添加数据的线程
            notFull.signalAll();
            return ret;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public int getCapacity() {
        return items.length;
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
