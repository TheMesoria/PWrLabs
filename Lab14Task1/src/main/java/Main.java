import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Scanner;

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

    private static void run(Analyser analyser) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Boolean done = false;
        while (!done) {

            String res = scanner.nextLine();
            switch (res) {
                case "p":
                    analyser.printInfo();
                    break;
                case "d":
                    analyser.setThreadCount(0);
                    done = true;
                    break;
                default:
                    break;
            }
        }

    }
}
