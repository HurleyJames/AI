package ac.hurley.ai.net.androidnet;

import android.net.UrlQuerySanitizer;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/10 09:41
 *      github  : https://github.com/HurleyJames
 *      desc    : HttpClient在Android6.0的版本中已经被废弃
 * </pre>
 */
public class HttpClientTest {

    /**
     * 发送Get的请求
     *
     * @param url
     * @throws IOException
     */
    public void sendGetRequest(String url) throws IOException {
        HttpClient httpClient = new DefaultHttpClient(defaultHttpParams());
        HttpGet httpGet = new HttpGet(url);
        // 添加header头
        httpGet.addHeader("Connection", "Keep-Alive");
        // 执行请求，得到response实体
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            // 解析请求结果
            String result = convertStreamToString(inputStream);
            Log.e("", "请求结果：" + result);
            inputStream.close();
        }
    }

    /**
     * 发送POST的请求
     *
     * @param url
     * @throws IOException
     */
    public void sendPostRequest(String url) throws IOException {
        HttpClient client = new DefaultHttpClient(defaultHttpParams());
        HttpPost request = new HttpPost(url);
        // 添加header头
        request.addHeader("Connection", "Keep-Alive");
        // POST将请求参数通过键值对的方式存储在List<NameValuePair>中
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("username", "myname"));
        postParameters.add(new BasicNameValuePair("pwd", "mypwd"));
        // 将所有参数打包，实例化UrlEncodedFormEntity对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
        // 使用HttpPost对象来设置UrlEncodedFormEntity的Entity
        request.setEntity(formEntity);

        // 执行网络请求
        HttpResponse response = client.execute(request);
        HttpEntity respEntity = response.getEntity();
        if (respEntity != null) {
            InputStream inputStream = respEntity.getContent();
            // 获取结果
            String result = convertStreamToString(inputStream);
            Log.e("", "请求结果：" + result);
            // 关闭输入流连接
            inputStream.close();
        }
    }

    /**
     * 设置默认的请求参数
     *
     * @return
     */
    public static HttpParams defaultHttpParams() {
        HttpParams mDefaultParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(mDefaultParams, 10000);
        HttpConnectionParams.setSoTimeout(mDefaultParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mDefaultParams, true);
        // 关闭旧连接检查的配置为false
        HttpConnectionParams.setStaleCheckingEnabled(mDefaultParams, false);
        // 协议参数
        HttpProtocolParams.setVersion(mDefaultParams, HttpVersion.HTTP_1_1);
        // 持续握手
        HttpProtocolParams.setUseExpectContinue(mDefaultParams, true);
        return mDefaultParams;
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
