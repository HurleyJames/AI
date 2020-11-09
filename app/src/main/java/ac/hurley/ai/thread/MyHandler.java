package ac.hurley.ai.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 09:13
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class MyHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        // 更新UI
    }
}

class HandlerThread extends Thread {
    private static MyHandler myHandler = new MyHandler();
    // 开启新的线程

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(123);
            };
        }.start();

        new Thread() {
            Handler handler = null;
            @Override
            public void run() {
                // 1. 为当前线程创建Looper，并且会绑定到ThreadLocal中
                Looper.prepare();
                handler = new Handler();
                // 2. 启动消息循环
                Looper.loop();
            }
        }.start();
    }
}