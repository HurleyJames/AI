package ac.hurley.ai.net.customnet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 10:26
 *      github  : https://github.com/HurleyJames
 *      desc    : 使用HttpURLConnection来创建连接
 * </pre>
 */
public class HttpUrlConnStack implements HttpStack {

    @Override
    public Response performRequest(Request<?> request) {
        HttpURLConnection urlConnection = null;
        try {
            // 构建HttpURLConnection
            urlConnection = createUrlConnection(request.getUrl());
            // 设置headers
            setRequestHeaders(urlConnection, request);
            // 设置body参数
            setRequestParams(urlConnection, request);
            return fetchResponse(urlConnection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                // 结束之前一定要关闭连接
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * 创建连接
     *
     * @param url
     * @return
     * @throws IOException
     */
    private HttpURLConnection createUrlConnection(String url) throws IOException {
        URL newURL = new URL(url);
        URLConnection urlConnection = newURL.openConnection();
        urlConnection.setConnectTimeout(mConfig.connTimeOut);
        urlConnection.setReadTimeout(mConfig.soTimeOut);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        return (HttpURLConnection) urlConnection;
    }

    /**
     * 设置请求头header
     *
     * @param connection
     * @param request
     */
    private void setRequestHeaders(HttpURLConnection connection, Request<?> request) {
        Set<String> headerKeys = request.getHeaders().keySet();
        for (String headerName : headerKeys) {
            connection.addRequestProperty(headerName, request.getHeaders().get(headerName));
        }
    }

    /**
     * 设置请求参数
     *
     * @param connection
     * @param request
     * @throws ProtocolException
     * @throws IOException
     */
    protected void setRequestParams(HttpURLConnection connection, Request<?> request) throws ProtocolException, IOException {
        HttpMethod method = request.getHttpMethod();
        connection.setRequestMethod(method.toString());
        // 添加参数
        byte[] body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            // 设置内容类型
            connection.addRequestProperty(Request.HEADER_CONTENT_TYPE, request.getBodyContentType());
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body);
            dataOutputStream.close();
        }
    }

    /**
     * 解析response
     *
     * @param connection
     * @return
     * @throws IOException
     */
    private Response fetchResponse(HttpURLConnection connection) throws IOException {
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = connection.getResponseCode();
        if (responseCode == 1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        // 状态行数据
        StatusLine responseStatus = new BasicStatusLine(protocolVersion, connection.getResponseCode(),
                connection.getResponseMessage());
        // 构建response
        Response response = new Response(responseStatus);
        // 设置response数据
        response.setEntity(entityFromURLConnection(connection));
        addHeadersToResponse(response, connection);
        return response;
    }

    /**
     * 执行HTTP请求之后获取到其数据流，即返回请求结果的流
     *
     * @param connection
     * @return
     */
    private HttpEntity entityFromURLConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = connection.getErrorStream();
        }
        // TODO: GZIP
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }

    /**
     * 添加header到response中
     *
     * @param response
     * @param connection
     */
    private void addHeadersToResponse(BasicHttpResponse response, HttpURLConnection connection) {
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            // 将header依次加入response中
            if (header.getKey() != null) {
                Header h = new BasicHeader(header.getKey(), header.getValue().get(0));
                response.addHeader(h);
            }
        }
    }
}
