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
        System.out.println("My name is: " + name);
        while (!stop) {
            String file = getFile();
            char lookingFor = generateNumber(0, 9).toCharArray()[0];
            int result = countNumber(file, lookingFor);
            mainAnalyser.printInfo();
            System.out.println(
                    "Runner with name: " + name +
                            " was looking for '" + lookingFor +
                            "', and found astonishing " + result +
                            " of them!\n");
        }
        System.out.println(name + " is no more.");

    }

    private String getFile() {
        String file = "file_" + generateNumber(1, 30) + ".txt";
        System.out.println(file);
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
