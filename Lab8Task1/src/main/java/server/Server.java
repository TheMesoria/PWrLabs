package server;

import util.Worker;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    private String name;
    private ServerSocket activeServerSocket;
    private LinkedList<Socket> propagationLayers = new LinkedList<>();
    private Socket outputConnection=null;
    private Socket inputConnection=null;
    public Server(String[] args)
    {
        setup(args);
        System.out.println("Server up and ready.");
    }
    public void start()
    {
        System.out.println("Server started as: "+activeServerSocket.getInetAddress()+":"+activeServerSocket.getLocalPort());
        Thread thread = new Thread(this::handleMessages);
        thread.start();
        try
        {
            while(true)
            {
                Socket socket = activeServerSocket.accept();
                System.out.println("Connected: "+socket.getInetAddress()+":"+socket.getPort());

                if(inputConnection==null && outputConnection==null) handleFirstNode(socket);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void handleMessages()
    {
        System.out.println("woohoo");
    }


    private void handleFirstNode(Socket socket) throws Exception
    {
        inputConnection=socket;
        outputConnection=socket;

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        sendMessage(Worker.createMessage(
                "CONN-OUT",
                ""+activeServerSocket.getLocalPort()
                ),
                out
        );
        sendMessage(Worker.createMessage(
                "CONN-IN",
                ""+activeServerSocket.getLocalPort()
                ),
                out
        );
    }
    private void reactToMessage(SOAPMessage msg) throws Exception
    {
        if(msg.getSOAPHeader().getValue().equals("MSG-"+name))
        {
            System.out.println("Private Message: "+msg
                    .getSOAPBody()
                    .getChildElements()
                    .next()
                    .getValue()
            );
        }
        else if(msg.getSOAPHeader().getValue().equals("MSG-CC"))
        {
            System.out.println("Message to circle: "+msg
                    .getSOAPBody()
                    .getChildElements()
                    .next()
                    .getValue()
            );
//            sendMessage(msg);
        }
        else if(msg.getSOAPHeader().getValue().equals("MSG"))
        {
            System.out.println("INCOMING BROADCAST: "+msg
                    .getSOAPBody()
                    .getChildElements()
                    .next()
                    .getValue()
            );
//            sendMessage(msg);
        }
        else if(msg.getSOAPHeader().getValue().equals("KILL"))
        {
            System.out.println("REQUESTED EXIT.");
            System.exit(0);
        }
    }

    private void sendMessage(SOAPMessage msg, ObjectOutputStream out) throws IOException, SOAPException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        msg.writeTo(baos);
        String strMsg = new String(baos.toByteArray());
        System.out.println(strMsg);
        out.writeObject(strMsg);
        out.flush();
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
            else if(args[i].toLowerCase().equals("--name"))
            {
                name=args[++i];
            }
            else if(args[i].toLowerCase().equals("--conn"))
            {
                try
                {
                    propagationLayers.add(
                            new Socket(InetAddress.getByName("localhost"),
                                    Integer.parseInt(args[++i])
                            ));
                } catch (Exception e) {e.printStackTrace();}
            }
        }
    }
}
