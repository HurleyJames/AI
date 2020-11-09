package ac.hurley.ai.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 19:09
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public abstract class SimpleAsyncTask<Result> {
    /**
     * HandlerThread内部封装了Handler和Thread，有单独的Looper和消息队列
     */
    private static final HandlerThread handlerThread = new HandlerThread("SimpleAsyncTask",
            Process.THREAD_PRIORITY_BACKGROUND);

    static {
        handlerThread.start();
    }

    /**
     * 获取调用execute线程的Looper，构建Handler
     */
    final Handler mUiHandler = new Handler(Looper.getMainLooper());
    /**
     * 与异步线程队列关联的Handler
     */
    final Handler mAsyncHandler = new Handler(handlerThread.getLooper());

    protected void onPreExecute() {

    }

    protected abstract Result doInBackground();

    protected void onPostExecute(Result result) {

    }

    public final SimpleAsyncTask<Result> execute() {
        onPreExecute();
        // 将任务放到HandlerThread线程中执行
        mAsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                // 后台执行任务，完成之后向UI线程post发送Result
                postResult(doInBackground());
            }
        });
        return this;
    }

    private void postResult(final Result result) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

}
