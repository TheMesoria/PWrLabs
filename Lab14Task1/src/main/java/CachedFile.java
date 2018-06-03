import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CachedFile {
    private String fileName;
    private String file;

    public CachedFile(String fileName) {
        this.fileName = fileName;
        loadFile(fileName);
    }

    private void loadFile(String fileName) {
        this.file = getFile("files/" + fileName);
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            System.out.println(scanner.hasNextLine());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    public String getFileName() {
        return fileName;
    }

    public String getFile() {
        return file;
    }
}
