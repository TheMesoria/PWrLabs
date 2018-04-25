import java.io.*;
import java.util.LinkedList;

public class Crypto {

    public static final int NUMBER_OF_LETTERS_IN_ALPHABET = 'z' - 'a' + 1 ;
    public static final int LETTER_VALUE = 'a';

    public static String encode(String message, int key) {
        key %= NUMBER_OF_LETTERS_IN_ALPHABET;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            result.append((char) ((message.charAt(i) % LETTER_VALUE + key) % NUMBER_OF_LETTERS_IN_ALPHABET + LETTER_VALUE));
        }

        return result.toString();
    }

    public static String decode(String message, int key) {
        key %= NUMBER_OF_LETTERS_IN_ALPHABET;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            result.append((char) ((message.charAt(i) % LETTER_VALUE - key) % NUMBER_OF_LETTERS_IN_ALPHABET + LETTER_VALUE));
        }

        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        if(args.length==0)
        {
            System.out.println("Missing arguments.");
            System.out.println("Suggested:");
            System.out.println("--hide <file name>");
            System.out.println("--discover <file name>");
        } else if(args[0].toLowerCase().equals("--hide"))
        {
            String file = args[1];
            String line="";
            LinkedList<String> save = new LinkedList<>();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine())!=null)
            {
                save.add(encode(line,3));
            }

            PrintWriter output = new PrintWriter(args[1]);
            for(String s : save)
            {
                System.out.println(s);
                output.println(s);
                output.flush();
            }
            output.close();

        } else if(args[0].toLowerCase().equals("--discover"))
        {
            String file = args[1];

            String line="";
            LinkedList<String> save = new LinkedList<>();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine())!=null)
            {
                save.add(decode(line,3));
            }

            PrintWriter output = new PrintWriter(args[1]);
            for(String s : save)
            {
                System.out.println(s);
                output.println(s);
                output.flush();
            }
            output.close();

        } else
        {
            System.out.println("Unknown command.");
        }
    }
}
