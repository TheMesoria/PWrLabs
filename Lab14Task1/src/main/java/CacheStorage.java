import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

public class CacheStorage {
    private float loads = 0.f;
    private float fails = 0.f;
    private ArrayBlockingQueue<String> names = new ArrayBlockingQueue<>(30);
    private HashMap<String, CachedFile> cache = new HashMap<>();
    private int maxSize;

    public float getFailRate()
    {
        return fails/loads;
    }

    public CacheStorage(int size) {
        this.changeCacheSize(size);
    }

    synchronized CachedFile getFile(String name) {
        loads++;
        if (cache.containsKey(name)) {
            return cache.get(name);
        }

        fails++;

        if (maxSize <= names.size()) {
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
