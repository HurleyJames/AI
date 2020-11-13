package ac.hurley.ai.net.customnet;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/10 15:10
 *      github  : https://github.com/HurleyJames
 *      desc    : 支持GET、POST、PUT、DELETE四种请求方式
 * </pre>
 */
public enum HttpMethod {
    // GET请求
    GET("GET"),
    // POST请求
    POST("POST"),
    // PUT请求
    PUT("PUT"),
    // DELETE请求
    DELETE("DELETE");

    private String mHttpMethod = "";

    private HttpMethod(String method) {
        mHttpMethod = method;
    }

    @Override
    public String toString() {
        return mHttpMethod;
    }
}
