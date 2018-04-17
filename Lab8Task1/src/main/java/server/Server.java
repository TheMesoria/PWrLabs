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
    private boolean done = false;
    private String name;
    private ServerSocket activeServerSocket;
    private LinkedList<Socket> propagationLayers = new LinkedList<>();
    private LinkedList<ObjectInputStream> propagationLayerReaders = new LinkedList<>();
    private Socket outputConnection=null;
    private Socket inputConnection=null;
    private Socket serverConnection=null;
    private ObjectInputStream inputListener;
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
                if(serverConnection.getLocalPort()==socket.getPort()){System.out.println("Server Listener detected.");continue;}

                System.out.println("Connected: "+socket.getInetAddress()+":"+socket.getPort());

                if(inputConnection==null && outputConnection==null) handleFirstNode(socket);
                else if(configured(socket))handleNode(socket);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private boolean configured(Socket socket)
    {
        try
        {
            handleFirstNode(socket);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Thread.sleep(1000);
            SOAPMessage soapMessage = Worker.createMessage("CFG",""); oos.flush();
            Worker.sendMessage(soapMessage,oos);

            String response,targetRes;
            while(true)
            {
                response = (String) ois.readObject();
                if(response==null)
                {
                    targetRes = Worker.getSoapMessageFromString(response)
                            .getSOAPBody().getChildElements()
                            .next().getValue();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void handleMessages()
    {
        String soapMessage=null;
        try
        {
            while (!done)
            {
                if(inputListener!=null)
                    soapMessage = (String) inputListener.readObject();
                if(soapMessage!=null)
                {
                    reactToMessage(Worker.getSoapMessageFromString(soapMessage));
                    soapMessage=null;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
//    public void sendConfRequest


    private void handleNode(Socket socket)
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            SOAPMessage soapMessage = Worker.createMessage("INTRD","");
            oos.flush();
            Worker.sendMessage(soapMessage,oos);

            String response,targetName;
            while(true)
            {
                response = (String) ois.readObject();
                if(response==null)
                {
                    targetName = Worker.getSoapMessageFromString(response)
                            .getSOAPBody().getChildElements()
                            .next().getValue();
                    break;
                }
            }

            SOAPMessage soapMessageChangeOutputPortTo = Worker.createMessage("CONN-OUT-"+targetName,
                    Integer.toString(socket.getPort())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleFirstNode(Socket socket) throws Exception
    {
        System.out.println("Configuring first node");
        inputConnection=socket;
        outputConnection=socket;

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());out.flush();
        System.out.println(serverConnection.getLocalPort());
        Worker.sendMessage(Worker.createMessage(
                "CONN-OUT",
                ""+serverConnection.getLocalPort()
                ),
                out
        );
        System.out.println("First node configured");
        out.reset();
    }
    private void reactToMessage(SOAPMessage msg) throws Exception
    {
        if(msg.getSOAPHeader().getValue().equals("INTRD-R"))
        {
            System.out.println("INCOMING INTRODUCTION: "+msg
                    .getSOAPBody()
                    .getChildElements()
                    .next()
                    .getValue());
        }
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

    void setup(String[] args)
    {
        for(int i = 0; i<args.length; i++)
        {
            if(args[i].toLowerCase().equals("--port"))
            {
                try
                {
                    activeServerSocket = new ServerSocket(Integer.parseInt(args[++i]),100);
                    serverConnection = new Socket("localhost",activeServerSocket.getLocalPort());
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
