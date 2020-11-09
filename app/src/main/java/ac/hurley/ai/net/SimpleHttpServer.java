package ac.hurley.ai.net;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 19:54
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class SimpleHttpServer extends Thread {

    /**
     * 监听端口
     */
    public static final int HTTP_PORT = 8000;

    /**
     * 服务器Socket
     */
    ServerSocket mSocket = null;

    public SimpleHttpServer() {
        try {
            // 构建服务端Socket，监听8000端口
            mSocket = new ServerSocket(HTTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mSocket == null) {
            throw new RuntimeException("服务器Socket初始化失败");
        }
    }

    @Override
    public void run() {
        try {
            // 开启无线循环，调用accept()方法等待客户端的连接，该函数会阻塞，直到有客户端进行连接
            while (true) {
                System.out.println("等待连接中");
                new DeliverThread(mSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


