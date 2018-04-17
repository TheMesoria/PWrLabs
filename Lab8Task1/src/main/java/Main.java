import client.Client;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.xml.namespace.QName;
import javax.xml.soap.*;

public class Main {

    public static void main(String[] args) {
        if (args.length==0)
        {
            System.err.println("No arguments.");
        }else
        {
            if(args[0].toLowerCase().equals("server"))
            {
                Server server = new Server(args);
                server.start();
            }else if (args[0].toLowerCase().equals("client"))
            {
                Client client = new Client(args);
                client.start();
            }else
            {
                System.err.println("Not known option.");
            }
        }
    }
}
