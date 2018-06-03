import javafx.scene.paint.Stop;

import java.util.Random;

public class Runner implements Runnable {
    Analyser mainAnalyser;
    CachedFile cachedFile;
    Boolean stop = false;
    String name;

    public Runner(Analyser analyser, String name)
    {
        mainAnalyser = analyser;
    }

    @Override
    public void run() {
        while (!stop)
        {

        }
    }

    public void killMe()
    {
        stop = true;
    }

    String generateNumber(int start, int stop)
    {
        return Integer.toString(new Random().nextInt(30)+1);
    }
}
