package ac.hurley.ai.net.customnet;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/10 15:27
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class Response extends BasicHttpResponse {

    /**
     * 原始的Response主体数据
     */
    public byte[] rawData = new byte[0];

    public Response(StatusLine statusline, ReasonPhraseCatalog catalog, Locale locale) {
        super(statusline, catalog, locale);
    }

    public Response(StatusLine statusline) {
        super(statusline);
    }

    public Response(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
    }

    @Override
    public void setEntity(HttpEntity entity) {
        super.setEntity(entity);
        rawData = entityToBytes(getEntity());
    }

    public int getStatusCode() {

    }

    @Override
    public void setStatusCode(int code) {

    }

    public String getMessage() {

    }

    public void setMessage(String msg) {

    }

    /**
     * 获得原始的数据
     *
     * @return
     */
    public byte[] getRawData() {
        return rawData;
    }

    /**
     * 将entity转化为bytes
     *
     * @param entity
     * @return
     */
    private byte[] entityToBytes(HttpEntity entity) {
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
