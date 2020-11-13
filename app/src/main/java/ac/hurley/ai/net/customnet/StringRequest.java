package ac.hurley.ai.net.customnet;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/12 20:00
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class StringRequest extends Request<String> {

    public StringRequest(HttpMethod method, String url, RequestListener<String> listener) {
        super(method, url, listener);
    }

    @Override
    public String parseResponse(Response response) {
        return new String(response.getRawData());
    }
}
