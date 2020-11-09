package ac.hurley.ai.net;

import com.blankj.utilcode.util.FileIOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 22:32
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class HttpPost {

    /**
     * 请求URL
     */
    public String url;
    /**
     * 请求参数
     */
    private Map<String, String> mParamsMap = new HashMap<>();
    /**
     * 客户端Socket
     */
    Socket mSocket;

    public HttpPost(String url) {
        this.url = url;
    }

    public void addParam(String key, String value) {
        mParamsMap.put(key, value);
    }

    /**
     * execute函数中
     */
    public void execute() {
        try {
            // 客户端首先创建Socket连接，目标地址就是用户执行的URL以及端口号
            mSocket = new Socket(this.url, SimpleHttpServer.HTTP_PORT);
            PrintStream outputStream = new PrintStream(mSocket.getOutputStream());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            final String boundary = "my_boundary_1";
            // 写入header
            writeHeader(boundary, outputStream);
            // 写入请求参数
            writeParams(boundary, outputStream);
            // 等待response的返回
            waitResponse(inputStream);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 对header固定做出以下配置
     *
     * @param boundary
     * @param outputStream
     */
    private void writeHeader(String boundary, PrintStream outputStream) {
        outputStream.println("POST /api/login/ HTTP/1.1");
        outputStream.println("content-length:123");
        outputStream.println("Host:" + this.url + ":" + SimpleHttpServer.HTTP_PORT);
        outputStream.println("Content-Type: multipart/form-data; boundary=" + boundary);
        outputStream.println("User-Agent:android");
        outputStream.println();
    }

    /**
     * 将所有参数通过输出流传递给服务端
     * 每个参数必须遵循以下特定的格式：
     * <p>
     * --boundary
     * Content-Disposition: form-data; name="参数名"
     * 空行
     * 参数值
     *
     * @param boundary
     * @param outputStream
     */
    private void writeParams(String boundary, PrintStream outputStream) {
        Iterator<String> paramsKeySet = mParamsMap.keySet().iterator();
        while (paramsKeySet.hasNext()) {
            String paramName = paramsKeySet.next();
            outputStream.println("--" + boundary);
            outputStream.println("Content-Disposition: form-data; name=" + paramName);
            outputStream.println();
            outputStream.println(mParamsMap.get(paramName));
        }
        // 结束符
        outputStream.println("--" + boundary + "--");
    }

    private void waitResponse(BufferedReader inputStream) throws IOException {
        System.out.println("请求结果：");
        String responseLine = inputStream.readLine();
        while (responseLine == null || !responseLine.contains("HTTP")) {
            responseLine = inputStream.readLine();
        }
        // 输出Response
        while ((responseLine = inputStream.readLine()) != null) {
            System.out.println(responseLine);
        }
    }
}
