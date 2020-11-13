package ac.hurley.ai.net.androidnet;

import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/10 10:56
 *      github  : https://github.com/HurleyJames
 *      desc    :
 *
 *      POST / HTTP/1.1
 *      Connection: Keep-Alive
 *      User-Agent: Dalvik/1.6.0 (Linux; U; Android 4.4.2; MX4 Build/KOT49H
 *      Host: www.devtf.cn
 *      Accept-Encoding: gzip
 *      Content-Type: application/x-www-form-urlencoded
 *      Content-Length: 28
 *      username=hurley&pwd=mypwd
 * </pre>
 */
public class HttpURLConnectionTest {
    private void sendRequest(String url) throws IOException {
        InputStream is = null;
        try {
            URL newUrl = new URL(url);
            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            // 设置读取超时为10秒
            conn.setReadTimeout(10000);
            // 设置连接超时为15秒
            conn.setConnectTimeout(15000);
            // 设置请求方式
            conn.setRequestMethod("POST");
            // 接收输入流
            conn.setDoInput(true);
            // 启动输出流，如果需要传递参数时，需要开启
            conn.setDoOutput(true);
            // 添加header
            conn.setRequestProperty("Connection", "Keep-Alive");
            List<NameValuePair> paramsList = new ArrayList<>();
            paramsList.add(new BasicNameValuePair("username", "hurley"));
            paramsList.add(new BasicNameValuePair("pwd", "mypwd"));
            writeParams(conn.getOutputStream(), paramsList);

            // 发起连接
            conn.connect();
            is = conn.getInputStream();
            String result = convertStreamToString(is);
            Log.i("", "请求结果：" + result);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 写入参数
     *
     * @param output
     * @param paramsList
     * @throws IOException
     */
    private void writeParams(OutputStream output, List<NameValuePair> paramsList) throws IOException {
        StringBuilder paramStr = new StringBuilder();
        for (NameValuePair pair : paramsList) {
            if (!TextUtils.isEmpty(paramStr)) {
                // 如果不为空，就通过$分隔符连接
                paramStr.append("$");
            }
            paramStr.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            paramStr.append("=");
            paramStr.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
        // 将参数写入到输出流
        writer.write(paramStr.toString());
        writer.flush();
        writer.close();
    }

    /**
     * 将请求的结果转化为String类型
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
