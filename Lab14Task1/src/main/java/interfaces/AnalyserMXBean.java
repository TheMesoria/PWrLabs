package interfaces;

public interface AnalyserMXBean {
    void setThreadCount(int threadCount);
    int getThreadCount();
    void setCacheSize(int cacheSize);
    int getCacheSize();
}
