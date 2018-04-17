import com.sun.source.tree.Scope;
import util.Worker;

import javax.xml.soap.SOAPMessage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.WriteAbortedException;
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
            Thread thread = new Thread(this::handleMessages);
            thread.start();
            System.out.println("Server started as: "+activeServerSocket.getInetAddress()+":"+activeServerSocket.getLocalPort());
            if(!buddieSocketList.isEmpty())
            {
                ObjectOutputStream oos = new ObjectOutputStream(buddieSocketList.getFirst().getOutputStream());
                Worker.sendMessage(Worker.createMessage("BRO",Integer.toString(activeServerSocket.getLocalPort())), oos);
                System.out.println("Confirming buddie");
            }
            while(true)
            {
                Socket socket = activeServerSocket.accept();
                slaveSocket=socket;
                System.out.println("Connected: "+socket.getInetAddress()+":"+socket.getPort());
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void handleMessages()
    {
        try
        {
            while(slaveSocket==null){Thread.sleep(1000);}
            SOAPMessage responseSOAP;
            ObjectInputStream objectInputStream = new ObjectInputStream(slaveSocket.getInputStream());
            String response=null;

            response=(String)objectInputStream.readObject();
            responseSOAP=Worker.getSoapMessageFromString(response);
            handleMessages(responseSOAP);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void checkBuddy(Socket socket) throws Exception
    {
        System.out.println("Responding to buddy call...");
        SOAPMessage msg = Worker.createMessage("BRO",Integer.toString(activeServerSocket.getLocalPort()));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        SOAPMessage responseSOAP;
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        String response=null;
        Worker.sendMessage(msg, objectOutputStream);

        response=(String)objectInputStream.readObject();
        responseSOAP=Worker.getSoapMessageFromString(response);

        System.out.println("Buddy call rejected.");
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
    void handleMessages(SOAPMessage msg)
    {
        try
        {
            switch(msg.getSOAPHeader().getValue())
            {
                case "BRO":
                    int port=Integer.parseInt(msg.getSOAPBody().getChildElements().next().getValue());
                    for(Socket socketVar:buddieSocketList)
                    {
                        if(socketVar.getLocalPort()==port)
                        {
                            Worker.sendMessage(
                                    Worker.createMessage(
                                            "BRO-R",
                                            "YES"
                                    )
                            );
                            System.out.println("Buddy call confirmed.");
                        }
                    }
                    break;

            }
        }
        catch(Exception e)
        {

        }
    }
}
