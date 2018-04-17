import com.sun.source.tree.Scope;
import util.Worker;

import javax.xml.soap.SOAPMessage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Servlet {
    String name;
    int parent;
    ServerSocket activeServerSocket;
    Socket slaveSocket=null;
    LinkedList<Socket> buddieSocketList=new LinkedList<>();

    public Servlet(String[] args) {
        setup(args);
    }


    public void start()
    {
        try
        {
            System.out.println("Server started as: "+activeServerSocket.getInetAddress()+":"+activeServerSocket.getLocalPort());
            while(true)
            {
                Socket socket = activeServerSocket.accept();
                System.out.println("Connected: "+socket.getInetAddress()+":"+socket.getPort());
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void handleMessages()
    {

    }
    public void checkBuddy(Socket socket) throws Exception
    {
        SOAPMessage msg = Worker.createMessage("BRO",Integer.toString(activeServerSocket.getLocalPort()));
        ObjectOutputStream objectInputStream = new ObjectOutputStream(socket.getOutputStream());
        Worker.sendMessage(msg, objectInputStream);
    }
    void setup(String[] args)
    {
        for(int i = 0; i<args.length; i++)
        {
            if(args[i].toLowerCase().equals("--port"))
            {
                try
                {
                    activeServerSocket = new ServerSocket(Integer.parseInt(args[++i]),100);
                }catch (Exception e){e.printStackTrace();}
            }
            else if(args[i].toLowerCase().equals("--parent"))
            {
                parent=Integer.parseInt(args[++i]);
            }
            else if(args[i].toLowerCase().equals("--name"))
            {
                name=args[++i];
            }
            else if(args[i].toLowerCase().equals("--conn"))
            {
                try
                {
                    buddieSocketList.add(
                            new Socket(InetAddress.getByName("localhost"),
                                    Integer.parseInt(args[++i])
                            ));
                } catch (Exception e) {e.printStackTrace();}
            }
        }
    }
}
