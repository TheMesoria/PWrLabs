package servlet;

import util.MessageWrapper;
import util.Worker;

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ServiceConfigurationError;

public class Servlet {
    String name;
    String zone;
    String master;
    String slavePort;
    Socket slave;
    ServerSocket serverSocket;
    LinkedList<Thread> threadLinkedList;
    LinkedList<Socket> registeredConnections;
    LinkedList<String> pendingOrdersLinkedList;
    HashMap<Socket,ObjectOutputStream> knownOOS = new HashMap<>();

    public void start() throws Exception {
        init();
        Thread thread = new Thread(this::server);
        thread.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        MessageWrapper mw = new MessageWrapper(
                "NONE",
                "NONE",
                String.valueOf(serverSocket.getLocalPort()),
                "NONE",
                ""
        );
        Thread.sleep(10000);

        while(true)
        {
            System.out.println("Message value: ");
            String string = br.readLine();
            System.out.println("Type: ");
            String type = br.readLine();
            mw.setType(type.toUpperCase());

            if(mw.getType().toUpperCase().equals("ZONE"))
            {
                System.out.println("Give me target zone: ");
                String tmp = br.readLine();
                mw.setTarget(tmp);
            }else if(mw.getType().toUpperCase().equals("PRIVATE"))
            {
                System.out.println("Give me target node: ");
                String tmp = br.readLine();
                mw.setTarget(tmp);
            }

            mw.generateId();
            mw.setMsg(string);
            handleSOAPMessage(mw,null);
        }

    }

    public void server()
    {
        try{
            while (true) {
                Socket socket = serverSocket.accept();
                knownOOS.putIfAbsent(socket,new ObjectOutputStream(socket.getOutputStream()));
                System.out.println("New node detected!");
                MessageWrapper messageWrapper = new MessageWrapper(
                        "SERVER-R",
                        String.valueOf(socket.getPort()),
                        String.valueOf(serverSocket.getLocalPort()),
                        "",
                        ""
                );
                messageWrapper.generateId();
                pendingOrdersLinkedList.add(messageWrapper.getId());
                Worker.sendMessage(messageWrapper.getMessage(), knownOOS.get(socket));
                Thread thread = new Thread (() -> handleCommunication(socket));
                thread.start();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void handleNewSocketConnection(int port, boolean isRegistered) throws Exception {
        threadLinkedList.add(new Thread(() -> handleCommunication(port, isRegistered)));
    }


    private void handleCommunication(Socket socket){
        try
        {
            ObjectOutputStream oos = knownOOS.get(socket);
            if(oos==null) knownOOS.put(socket,new ObjectOutputStream(socket.getOutputStream()));
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String inc;

            while (true) {
                inc = (String) ois.readObject();
                MessageWrapper messageWrapper = new MessageWrapper(
                        Worker.getSoapMessageFromString(inc)
                );
                handleSOAPMessage(messageWrapper, socket);

            }
        } catch (EOFException e)
        {
            System.out.println("Host ended session! -> "+socket.getPort());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void handleCommunication(int port, boolean isRegistered) {
        try {
            Socket socket = new Socket("localhost", port);
            if (isRegistered) registeredConnections.add(socket);
            handleCommunication(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSOAPMessage(MessageWrapper msg, Socket socket) throws Exception {
        switch (msg.getType()) {
            case "SERVER-R":

                System.out.println("Received server request.");

                msg.setType("SERVER-A");
                String target = msg.getTarget();
                msg.setTarget(msg.getSource());
                msg.setSource(target);

                System.out.println(msg.getTarget()+"," +master);
                if (msg.getTarget().equals(master)) {
                    System.out.println("Detected master.");
                    msg.setMsg("NO");
                } else if(slavePort!=null && slavePort.equals(String.valueOf(socket.getPort()))) {
                    System.out.println("Detected slave!");
                    slave = socket;
                    msg.setMsg("CLOSE");
                } else
                {
                    System.out.println("Detected friend.");
                    msg.setMsg("YES");
                    Worker.sendMessage(msg.getMessage(), knownOOS.get(socket));
                    return;
                }
                ObjectOutputStream oos = knownOOS.get(slave);
                if(oos==null) System.out.println("There is null as socket...");
                Worker.sendMessage(msg.getMessage(), knownOOS.get(slave));

                break;
            case "SERVER-A":
                System.out.println("Received an answer.");
                System.out.println(msg.getSource() + " reported: " + msg.getMsg());
                if(msg.getMsg().equals("YES"))
                {
                    System.out.println("Adding friend.");
                    registeredConnections.add(socket);
                }
                else if(msg.getMsg().equals("NO"))
                    slave=socket;
                else if(msg.getMsg().equals("CLOSE"))
                    System.out.println("Detected a zone enclose!");
                break;
            case "BROADCAST":
                handleBroadcast(msg, socket);
                break;
            case "ZONE":
                handleZone(msg,socket);
                break;
            case "PRIVATE":
                handlePrivate(msg,socket);
                break;
        }
    }

    private void handleZone(MessageWrapper msg, Socket socket) throws Exception {
        if(stopMessage(msg, socket)) return;

        if(msg.getTarget().toUpperCase().equals(zone))
        {
            System.out.println("Detected zone message!");
            System.out.println("Broadcasting to slaves!");
            msg.setType("BROADCAST");
        }else
        {
            System.out.println("Incoming broadcast... passing.");
        }

        send(msg,socket);
    }

    private void handlePrivate(MessageWrapper msg, Socket socket) throws Exception
    {
        if(stopMessage(msg, socket)) return;


        if(msg.getTarget().equals(String.valueOf(serverSocket.getLocalPort())))
        {
            System.out.println("Detected Message: "+msg.getMsg());
        }
        else
        {
            System.out.println("Passing private Message...");
        }

        send(msg,socket);
    }

    private void handleBroadcast(MessageWrapper msg, Socket socket) throws Exception
    {
        if(stopMessage(msg,socket)) return;


        System.out.println("Incoming broadcast from: "+msg.getSource());
        if(socket!=null && serverSocket.getLocalPort()==Integer.parseInt(msg.getSource()))
        {
            System.out.println("Self Broadcast detected. Killing.");
            return;
        }
        System.out.println(msg.getMsg());

        send(msg,socket);
    }

    private boolean stopMessage(MessageWrapper msg, Socket socket)
    {
        for (String id:pendingOrdersLinkedList)
        {

            if(id.equals(msg.getId())) {
                System.out.println("Broken retransmission... STOPPING");
                return true;
            }

        }
        pendingOrdersLinkedList.add(msg.getId());


        System.out.println("Incoming broadcast from: "+msg.getSource());
        if(socket!=null && serverSocket.getLocalPort()==Integer.parseInt(msg.getSource()))
        {
            System.out.println("Self Broadcast detected. Killing.");
            return true;
        }

        return false;
    }

    private void send(MessageWrapper msg, Socket socket) throws Exception
    {
        if(slave!=null)
            Worker.sendMessage(msg.getMessage(), knownOOS.get(slave));

        for(Socket regSocket : registeredConnections)
        {
            if(socket == regSocket)
            {
                System.out.print("Sending to friend: " + regSocket.getPort());
                System.out.println(" | Stopping resend - sender!");
            }else
            {
                System.out.println("Sending to friend: " + regSocket.getPort());
                Worker.sendMessage(msg.getMessage(),knownOOS.get(regSocket));
            }
        }
    }

    private void init() {
        for (Thread thread : threadLinkedList) thread.start();
    }
}
