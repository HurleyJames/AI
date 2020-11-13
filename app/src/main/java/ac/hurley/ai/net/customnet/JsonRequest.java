package ac.hurley.ai.net.customnet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/11 09:55
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class JsonRequest extends Request<JSONObject> {

    public JsonRequest(HttpMethod method, String url, RequestListener<JSONObject> listener) {
        super(method, url, listener);
    }

    /**
     * 将Response的结果转化为JSONObject
     * @param response
     * @return
     */
    @Override
    public JSONObject parseResponse(Response response) {
        String jsonString = new String(response.getRawData());
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
