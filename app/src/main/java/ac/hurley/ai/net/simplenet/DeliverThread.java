package ac.hurley.ai.net.simplenet;

import com.blankj.utilcode.util.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/9 20:08
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class DeliverThread extends Thread {


    /******响应报文的基本格式******/
    /******响应行******/
    /******header区域******/
    /******空行******/
    /******响应数据******/

    /**
     * Socket实例
     */
    Socket mClientSocket;
    /**
     * 输入流
     */
    BufferedReader mInputStream;
    /**
     * 输出流
     */
    PrintStream mOutputStream;
    /**
     * 请求方法GET、POST等
     */
    String httpMethod;
    /**
     * 子路径
     */
    String subPath;
    /**
     * 分隔符
     */
    String boundary;
    /**
     * header参数
     */
    Map<String, String> mHeaders = new HashMap<>();
    /**
     * 请求参数
     */
    Map<String, String> mParams = new HashMap<>();
    /**
     * 是否已经解析完了Header
     */
    boolean isParseHeader = false;

    public DeliverThread(Socket mSocket) {
        mClientSocket = mSocket;
    }

    @Override
    public void run() {
        try {
            // 获取输入流
            mInputStream = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
            // 获取输出流
            mOutputStream = new PrintStream(mClientSocket.getOutputStream());
            // 解析请求
            parseRequest();
            // 返回Response
            handleResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 解析请求
     */
    private void parseRequest() {
        String line;
        try {
            int lineNum = 0;
            // 从输入流读取数据
            while ((line = mInputStream.readLine()) != null) {
                // 第一行为请求行
                if (lineNum == 0) {
                    parseRequestLine(line);
                }
                // 判断是否是数据的结束行
//                if (isEnd(line)) {
//                    break;
//                }
                // 解析header参数
                if (lineNum != 0 && !isParseHeader) {
                    parseHeaders(line);
                }
                // 解析请求参数
                if (isParseHeader) {
                    parseRequestParams(line);
                }
                lineNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析请求行
     * 请求行由3部分组成：请求方式、请求子路径、协议版本，之间通过空格来进行分割
     *
     * @param lineOne
     */
    private void parseRequestLine(String lineOne) {
        String[] tempStrings = lineOne.split(" ");
        httpMethod = tempStrings[0];
        subPath = tempStrings[1];
        System.out.println("请求方式：" + tempStrings[0]);
        System.out.println("子路径：" + tempStrings[1]);
        System.out.println("HTTP版本：" + tempStrings[2]);
    }

    /**
     * 解析header，参数为每个header的字符串
     * 每一个header是一个独立行
     *
     * @param headerLine
     */
    private void parseHeaders(String headerLine) {
        // header区域的结束符
        if (headerLine.equals("")) {
            isParseHeader = true;
            System.out.println("------> header解析完成\n");
            return;
        } else if (headerLine.contains("boundary")) {
            boundary = parseSecondField(headerLine);
            System.out.println("分隔符：" + boundary);
        } else {
            // 解析普通header参数
            parseHeaderParam(headerLine);
        }
    }

    /**
     * 当一个header字段含有boundary字段时，调用parseSecondField函数解析
     *
     * @param line
     * @return
     */
    private String parseSecondField(String line) {
        String[] headerArray = line.split(";");
        parseHeaderParam(headerArray[0]);
        if (headerArray.length > 1) {
            return headerArray[1].split("=")[1];
        }
        return "";
    }

    /**
     * 解析单个header
     *
     * @param headerLine
     */
    private void parseHeaderParam(String headerLine) {
        String[] keyValue = headerLine.split(":");
        mHeaders.put(keyValue[0].trim(), keyValue[1].trim());
        System.out.println("header参数名：" + keyValue[0].trim() + "，参数值:" + keyValue[1].trim());
    }

    /**
     * 解析请求参数
     *
     * @param paramLine
     * @throws IOException
     */
    private void parseRequestParams(String paramLine) throws IOException {
        if (paramLine.equals("--" + boundary)) {
            String ContentDisposition = mInputStream.readLine();
            String paramName = parseSecondField(ContentDisposition);
            mInputStream.readLine();
            String paramValue = mInputStream.readLine();
            mParams.put(paramName, paramValue);
            System.out.println("参数名：" + paramName + "，参数值：" + paramValue);
        }
    }

    /**
     * 返回结果
     */
    private void handleResponse() {
        // 模拟处理耗时
        sleep();
        mOutputStream.println("HTTP/1.1 200 OK");
        mOutputStream.println("Content-Type: application/json");
        mOutputStream.println();
        mOutputStream.println("{\"stCode\":\"success\"}");
    }

    /**
     * 模拟处理耗时，设置线程休眠1秒
     */
    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
