import javax.ejb.*;

import interfaces.AnalyserMXBean;
import thirdParty.RandomString;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@Startup
@LocalBean
public class Analyser implements AnalyserMXBean {
    private int maxThreadCount;
    private int threadCount = 0;
    private int cacheSize;
    private CacheStorage cache = new CacheStorage(5);

    private ArrayBlockingQueue<Runner> threadEliminationQueue = new ArrayBlockingQueue<>(100);

    public Analyser() {
        setCacheSize(30);
        setThreadCount(3);
    }

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
        if ( maxThreadCount == threadCount || threadCount < 0 ) return;

        RandomString nameGenerator = new RandomString(8, ThreadLocalRandom.current());
        this.maxThreadCount = threadCount;

        if( this.threadCount < this.maxThreadCount )
        {
            while (this.threadCount < this.maxThreadCount )
            {
                String name = nameGenerator.nextString();
                Runner newRunner = new Runner(this, name);
                Thread thread = new Thread(newRunner);
                threadEliminationQueue.add(newRunner);
                thread.start();

                this.threadCount++;
            }
        } else
        {
            while ( this.threadCount > this.maxThreadCount && threadEliminationQueue.size()!=0)
            {
                Runner runner = threadEliminationQueue.poll();
                if ( runner == null ) return;
                runner.killMe();
                this.threadCount--;
            }
        }
    }

    @Override
    public int getThreadCount() {
        return threadCount;
    }

    public CacheStorage getCache()
    {
        return cache;
    }

    public void printInfo()
    {
        System.out.println("Amount of threads running: " + getThreadCount() +".");
        System.out.println("Cache status: " + cache.getFilesStored() + " files stored.");
        System.out.println("Cache fail rate: " + cache.getFailRate()*100 + "%");
    }
}
