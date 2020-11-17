package ac.hurley.ai.net.customnet;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/14 20:48
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public interface Cache<K, V> {

    public V get(K key);

    public Response put(K key, V value);

    public void remove(K key);
}
