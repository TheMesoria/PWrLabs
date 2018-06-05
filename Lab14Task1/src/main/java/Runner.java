import javafx.scene.paint.Stop;

import java.util.Random;

public class Runner implements Runnable {
    Analyser mainAnalyser;
    CachedFile cachedFile;
    Boolean stop = false;
    String name;

    public Runner(Analyser analyser, String name) {
        mainAnalyser = analyser;
        this.name = name;
    }

    @Override
    public void run() {
        while (!stop) {
            String file = getFile();
            char lookingFor = generateNumber(0, 9).toCharArray()[0];
            int result = countNumber(file, lookingFor);
        }

    }

    private String getFile() {
        String file = "file_" + generateNumber(1, 30) + ".txt";
        return mainAnalyser.getCache().getFile(file).getFile();
    }

    private int countNumber(String file, char number) {
//        System.out.println(file);
        int counter = 0;
        char[] dump = file.toCharArray();
        for (char var : dump) {
            if (var == number) {
//                System.out.println("Var: "+var+", number: " + number );
                counter++;
            }
        }
        return counter;
    }

    public void killMe() {
        stop = true;
    }

    String generateNumber(int start, int stop) {
        return Integer.toString(new Random().nextInt(stop) + start);
    }
}
