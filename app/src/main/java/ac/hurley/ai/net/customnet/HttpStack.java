package ac.hurley.ai.net.customnet;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 10:24
 *      github  : https://github.com/HurleyJames
 *      desc    : 执行网络请求的接口
 * </pre>
 */
public interface HttpStack {

    /**
     * 执行HTTP请求
     *
     * @param request
     * @return
     */
    public Response performRequest(Request<?> request);
}
