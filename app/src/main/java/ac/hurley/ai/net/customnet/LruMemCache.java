package ac.hurley.ai.net.customnet;

import android.util.LruCache;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/14 20:49
 *      github  : https://github.com/HurleyJames
 *      desc    : 将请求结果缓存到内存中
 * </pre>
 */
public class LruMemCache implements Cache<String, Response> {

    /**
     * LRU缓存
     */
    private LruCache<String, Response> mResponseCache;

    public LruMemCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 取1/8八分之一的可用内存作为缓存的空间使用
        final int cacheSize = maxMemory / 8;
        mResponseCache = new LruCache<String, Response>(cacheSize) {
            @Override
            protected int sizeOf(String key, Response response) {
                return response.rawData.length / 1024;
            }
        };

    }

    @Override
    public Response get(String key) {
        return mResponseCache.get(key);
    }

    @Override
    public Response put(String key, Response value) {
        return mResponseCache.put(key, value);
    }

    @Override
    public void remove(String key) {
        mResponseCache.remove(key);
    }
}
