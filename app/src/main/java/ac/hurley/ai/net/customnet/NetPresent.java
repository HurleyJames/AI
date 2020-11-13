package ac.hurley.ai.net.customnet;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/12 19:52
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class NetPresent {

    RequestQueue mQueue = new RequestQueue(Runtime.getRuntime().availableProcessors() + 1, HttpStackFactory.createHttpStack());

    private void sendStringRequest() {
        // 携带请求方式、URL、回调接口的参数
        StringRequest request = new StringRequest(HttpMethod.GET, "http://www.baidu.com", new Request.RequestListener<String>() {
            @Override
            public void onComplete(int stCode, String response, String errMsg) {
                // 处理结果
            }
        });

        mQueue.addRequest(request);
    }
}
