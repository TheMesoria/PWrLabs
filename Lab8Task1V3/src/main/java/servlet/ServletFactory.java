package servlet;

import java.net.ServerSocket;
import java.util.LinkedList;

public class ServletFactory {
    static public Servlet createServlet(String []args) throws Exception
    {
        Servlet servlet = new Servlet();
        servlet.threadLinkedList = new LinkedList<>();
        servlet.registeredConnections = new LinkedList<>();
        servlet.pendingOrdersLinkedList = new LinkedList<>();

        for(int i=0;i<args.length;i++)
        {
            switch (args[i]) {
                case "--name":
                    servlet.name = args[++i];
                    break;
                case "--circle":
                    servlet.zone = args[++i];
                    break;
                case "--friend":
                    servlet.handleNewSocketConnection(Integer.parseInt(args[++i]), true);
                    break;
                case "--port":
                    servlet.serverSocket = new ServerSocket(Integer.parseInt(args[++i]));
                    break;
                case "--master":
                    servlet.master=args[++i];
                    System.out.println(servlet.master);
                    servlet.handleNewSocketConnection(Integer.parseInt(servlet.master),false);
                    break;
            }
        }
        return servlet;
    }
}
