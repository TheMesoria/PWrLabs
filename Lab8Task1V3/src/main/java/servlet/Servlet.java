package servlet;

import util.MessageWrapper;
import util.Worker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
                "BROADCAST",
                "NONE",
                String.valueOf(serverSocket.getLocalPort()),
                "NONE",
                ""
        );
        mw.generateId();

        while(true)
        {
            String string = br.readLine();
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
                }
                ObjectOutputStream oos = knownOOS.get(socket);
                if(oos==null) System.out.println("There is null as socket...");
                Worker.sendMessage(msg.getMessage(), knownOOS.get(socket));

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
                for (String id:pendingOrdersLinkedList)
                {
                    if(id.equals(msg.getId())) {
                        System.out.println("Broken retransmission... STOPPING");
                        return;
                    }

                }
                pendingOrdersLinkedList.add(msg.getId());

                System.out.println("Incoming broadcast from: "+msg.getSource());
                if(socket!=null && serverSocket.getLocalPort()==Integer.parseInt(msg.getSource()))
                {
                    System.out.println("Self Broadcast detected. Killing.");
                    break;
                }
                System.out.println(msg.getMsg());

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

                break;
        }
    }

    private void init() {
        for (Thread thread : threadLinkedList) thread.start();
    }
}
