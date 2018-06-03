import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class CacheStorage {
    private ArrayBlockingQueue<String> names;
    private HashMap<String, CachedFile> cache;
    private int maxSize;

    public CacheStorage(int size) {
        maxSize = size;
    }

    synchronized CachedFile getFile(String name) {
        if (cache.containsKey(name))
            return cache.get(name);

        if (maxSize >= names.size()) {
            String nameRemoved = names.poll();
            cache.remove(nameRemoved);
        }

        names.add(name);
        cache.put(name, new CachedFile(name));

        return cache.get(name);
    }

    void changeCacheSize(int size) {
        if (size > maxSize) {
            maxSize = size;
        } else if (size < maxSize) {
            while(size != maxSize){
                String removed = names.poll();
                cache.remove(removed);
                maxSize--;
            }
        }
    }

    int getFilesStored() {
        return names.size();
    }
}
