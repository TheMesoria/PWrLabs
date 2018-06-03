import javax.ejb.*;

import interfaces.AnalyserMXBean;
import thirdParty.RandomString;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@Startup
@LocalBean
public class Analyser implements AnalyserMXBean {
    private int maxThreadCount = 3;
    private int threadCount = 0;
    private int cacheSize = 5;
    private CacheStorage cache = new CacheStorage(5);

    private ArrayBlockingQueue<String> threadEliminationQueue;
    private HashMap<String, Runner> threadsKnown;

    @Override
    public int getCacheSize() {
        return cacheSize;
    }

    @Override
    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
        cache.changeCacheSize(cacheSize);
    }

    @Override
    public void setThreadCount(int threadCount) {
        if ( maxThreadCount == threadCount) return;

        RandomString nameGenerator = new RandomString(8, ThreadLocalRandom.current());
        this.maxThreadCount = threadCount;

        if( threadCount < this.maxThreadCount )
        {
            while (threadCount < this.maxThreadCount )
            {
                String name = nameGenerator.nextString();
                threadEliminationQueue.add(name);
                Runner newRunner = new Runner(this, name);
                Thread thread = new Thread(newRunner);
                threadsKnown.putIfAbsent(name, newRunner);

                threadCount++;
            }
        } else
        {

        }
    }

    @Override
    public int getThreadCount() {
        return threadCount;
    }

    public void printInfo()
    {
        System.out.println("Amount of threads running: " + getThreadCount());

    }
}
