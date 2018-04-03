import main.FileChunk;
import main.HelperImplementation;
import main.ServerImplementation;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main
{
    public static void main(String[] args) {
        if(args.length==0) return;
        try
        {
            if(args[0].compareTo("server")==0) new ServerImplementation().Start();
            if(args[0].compareTo("helper")==0) new HelperImplementation().Start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
}
