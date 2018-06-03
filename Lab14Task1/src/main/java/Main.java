import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        Analyser analyser = new Analyser();
        registerWithJmxAgent(analyser);
        run(analyser);
    }

    private static void registerWithJmxAgent(Analyser analyser) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("pl.pwr.jmx:type=analyser");
        mbs.registerMBean(analyser, name);
    }

    private static void run(Analyser analyser) throws Exception{
//        while (true)
//        {
            Thread.sleep(100);
            CachedFile cachedFile = new CachedFile("file_1.txt");
            System.out.println(cachedFile.getFile().length());
            System.out.println(analyser.getThreadCount());
//        }
    }
}
