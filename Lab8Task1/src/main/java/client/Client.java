package client;

import util.Worker;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.charset.Charset;

public class Client {
    private String name;
    boolean configured=false;
    boolean done=false;

    Socket parent; // Will not receive
    Socket child; // Will not send

    ObjectInputStream input;
    ObjectOutputStream output;

    public Client(String[] args)
    {
        setup(args);
    }
    public void start()
    {
        try
        {
            String soapMessage;
            while (!done)
            {
                if(!configured && child!=null && parent!=null)
                {
                    System.out.println("Configured.");
                }
                soapMessage = (String) input.readObject();
                if(soapMessage!=null)
                {
                    reactToMessage(Worker.getSoapMessageFromString(soapMessage));
                    soapMessage=null;
                }
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void reactToMessage(SOAPMessage msg) throws Exception
    {
        if(msg.getSOAPHeader().getValue().equals("CONN-IN"))
        {
            System.out.println("Detected configuration changes...");
            System.out.println("localhost:"+msg.getSOAPBody().getChildElements().next().getValue());
            parent = new Socket(
                    InetAddress.getByName("localhost"),
                    Integer.parseInt(msg.getSOAPBody().getChildElements().next().getValue())
            );

            input = new ObjectInputStream(parent.getInputStream());
        }
        else if(msg.getSOAPHeader().getValue().equals("CONN-OUT-"+name))
        {
            System.out.println("Detected configuration changes...");
            child = new Socket(
                    InetAddress.getByName("localhost"),
                    Integer.parseInt(msg.getSOAPBody().getChildElements().next().getValue())
            );

            output = new ObjectOutputStream(child.getOutputStream());
        }
        else if(msg.getSOAPHeader().getValue().length()>9 &&
                msg.getSOAPHeader().getValue().substring(0,9).equals("CONN-OUT-"))
        {
            System.out.println("Configuration request send to child.");
        }
        else if(msg.getSOAPHeader().getValue().equals("CONN-OUT"))
        {
            System.out.println("Detected configuration changes...");
            child = new Socket(
                    InetAddress.getByName("localhost"),
                    Integer.parseInt(msg.getSOAPBody().getChildElements().next().getValue())
            );

            output = new ObjectOutputStream(child.getOutputStream());
        }
        else if(msg.getSOAPHeader().getValue().equals("MSG-"+name))
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
            sendMessage(msg);
        }
        else if(msg.getSOAPHeader().getValue().equals("MSG"))
        {
            System.out.println("INCOMING BROADCAST: "+msg
                    .getSOAPBody()
                    .getChildElements()
                    .next()
                    .getValue()
            );
            sendMessage(msg);
        }
        else if(msg.getSOAPHeader().getValue().equals("INTRD"))
        {
            System.out.println("Introduction requested.");
            sendMessage(Worker.createMessage("INTRD-R",name));
        }
        else if(msg.getSOAPHeader().getValue().equals("CFG"))
        {
            System.out.println("Configuration request.");
            if(configured) sendMessage(Worker.createMessage("CFG-R","YES"));
            else sendMessage(Worker.createMessage("CFG-R","NO"));
        }
        else if(msg.getSOAPHeader().getValue().equals("KILL"))
        {
            System.out.println("REQUESTED EXIT.");
            try
            {
                sendMessage(msg);
            }
            catch(Exception e)
            {
                System.err.println("Connection already closed.");
            }
            finally
            {
                System.exit(0);
            }
        }
    }
    private void sendMessage(SOAPMessage msg) throws Exception
    {
       Worker.sendMessage(msg,output);
    }

    private void setup(String[] args)
    {
        for(int i = 0; i<args.length; i++)
        {
            if(args[i].toLowerCase().equals("--port"))
            {
                System.out.println("Requested connection at: localhost:"+args[i+1]);
                try
                {
                   parent = new Socket(
                           InetAddress.getByName("localhost"),
                           Integer.parseInt(args[++i])
                   );
                   input=new ObjectInputStream(parent.getInputStream());
                }catch (Exception e)
                {
                    System.err.println("Critical error occurred.");
                    System.exit(-1);
                }
                System.out.println("Connection established.");
                System.out.println("Awaiting configuration...");
            }
            else if(args[i].toLowerCase().equals("--name"))
            {
                name=args[++i];
            }
        }
    }

}
