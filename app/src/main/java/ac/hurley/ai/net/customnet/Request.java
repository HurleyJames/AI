package ac.hurley.ai.net.customnet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/10 15:16
 *      github  : https://github.com/HurleyJames
 *      desc    : GET和DELETE是不能传递请求参数的，因为其请求的性质所致，用户可以将参数构建到URL后传递进来并入到Request中
 * </pre>
 */
public abstract class Request<T> implements Comparable<Request<T>> {

    /**
     * 默认的编码方式
     */
    public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    /**
     * 请求头类型
     */
    public static final String HEADER_CONTENT_TYPE = "";
    /**
     * 请求的序列号
     */
    protected int mSerialNum = 0;
    /**
     * 优先级默认设置为NORMAL
     */
    protected Priority mPriority = Priority.NORMAL;
    /**
     * 是否取消该请求，默认为false
     */
    protected boolean isCancel = false;
    /**
     * 是否应该缓存
     */
    private boolean mShouldCache = true;
    /**
     * 请求Listener
     */
    protected RequestListener<T> mRequestListener;
    /**
     * 请求的URL
     */
    private String mUrl = "";
    /**
     * 请求方法，默认GET，可更改
     */
    HttpMethod mHttpMethod = HttpMethod.GET;
    /**
     * 请求头
     */
    private Map<String, String> mHeaders = new HashMap<>();
    /**
     * 请求参数
     */
    private Map<String, String> mBodyParams = new HashMap<>();

    /**
     * 携带请求方式、URL、回调接口的参数
     *
     * @param method
     * @param url
     * @param listener
     */
    public Request(HttpMethod method, String url, RequestListener<T> listener) {
        mHttpMethod = method;
        mUrl = url;
        mRequestListener = listener;
    }

    /**
     * 从原生的网络请求中解析结果，子类必须重写该方法
     *
     * @param response
     * @return
     */
    public abstract T parseResponse(Response response);

    /**
     * 处理得到的结果Response，该方法需要运行在UI主线程中
     */
    public final void deliveryResponse(Response response) {
        T result = parseResponse(response);
        if (mRequestListener != null) {
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unknown error";
            mRequestListener.onComplete(stCode, result, msg);

        }
    }

    /**
     * 获得编码的方式
     *
     * @return
     */
    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    /**
     * 获得Body的内容类型
     *
     * @return
     */
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    /**
     * 返回POST或者PUT请求时的Body参数字节数组
     *
     * @return
     */
    public byte[] getBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    /**
     * 获得请求头
     *
     * @return
     */
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    /**
     * 获得请求的参数
     *
     * @return
     */
    public Map<String, String> getParams() {
        return mBodyParams;
    }

    /**
     * 获得请求的URL链接
     *
     * @return
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * 获得请求方式
     *
     * @return
     */
    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    /**
     * 将参数转换为URL编码的参数串，格式为key1=value&key2=value2
     *
     * @param params
     * @param paramsEncoding
     * @return
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // key值
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append("=");
                // value值
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append("&");
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding);
        }
    }

    /**
     * 用于对请求的排序处理，根据优先级和加入到队列的序号进行排序
     *
     * @param another
     * @return
     */
    @Override
    public int compareTo(Request<T> another) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = another.getPriority();
        // 如果优先级相等，那么按照添加到队列的序列号顺序来执行
        return myPriority.equals(another) ? this.getSerialNumber() - another.getSerialNumber() :
                myPriority.ordinal() - anotherPriority.ordinal();
    }

    public boolean shouldCache() {
        return mShouldCache;
    }

    public boolean isCanceled() {
        return isCancel;
    }

    /**
     * 获得优先级
     *
     * @return
     */
    public Priority getPriority() {
        return mPriority;
    }

    /**
     * 获得序列号
     *
     * @return
     */
    public int getSerialNumber() {
        return mSerialNum;
    }

    /**
     * 设置序列号
     *
     * @param serialNumber
     */
    public void setSerialNumber(int serialNumber) {
        this.mSerialNum = serialNumber;
    }

    /**
     * 网络请求Listener会被执行在UI线程
     *
     * @param <T>
     */
    public static interface RequestListener<T> {
        /**
         * 请求完成的回调
         *
         * @param stCode
         * @param response
         * @param errMsg
         */
        public void onComplete(int stCode, T response, String errMsg);
    }
}
