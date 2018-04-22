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
    Socket slave;
    ServerSocket serverSocket;
    LinkedList<Thread> threadLinkedList;
    LinkedList<Socket> registeredConnections;
    LinkedList<String> pendingOrdersLinkedList;
    HashMap<Socket,ObjectOutputStream> knownOOS = new HashMap<>();
    HashMap<Socket,ObjectInputStream> knownOIS = new HashMap<>();

    public void start() throws Exception {
        init();
        Thread thread = new Thread(this::server);
        thread.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        MessageWrapper mw = new MessageWrapper(
                "BROADCAST",
                "NONE",
                "NONE",
                "NONE",
                ""
        );
        mw.generateId();

        while(true)
        {
            String string = br.readLine();
            mw.setMsg(string);
            Worker.sendMessage(mw.getMessage(),knownOOS.get(slave));
        }

    }

    public void server()
    {
        try{
            while (true) {
                Socket socket = serverSocket.accept();
                knownOOS.putIfAbsent(socket,new ObjectOutputStream(socket.getOutputStream()));
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
                handleCommunication(socket);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void handleNewSocketConnection(int port, boolean isRegistered) throws Exception {
        threadLinkedList.add(new Thread(() -> handleCommunication(port, isRegistered)));
    }

    private void handleCommunication(Socket socket) throws Exception {
        knownOIS.putIfAbsent(socket, new ObjectInputStream(socket.getInputStream()));
        knownOOS.putIfAbsent(socket, new ObjectOutputStream(socket.getOutputStream()));
        ObjectInputStream ois = knownOIS.get(socket);

        while (true) {
            MessageWrapper messageWrapper = new MessageWrapper(
                    Worker.getSoapMessageFromString((String) ois.readObject())
            );

            handleSOAPMessage(messageWrapper, socket);
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

                if (msg.getTarget().equals(master)) {
                    System.out.println("Detected master.");
                    msg.setMsg("NO");
                } else {
                    System.out.println("Detected friend.");
                    msg.setMsg("YES");
                }
                System.out.println(msg);
                Worker.sendMessage(msg.getMessage(), knownOOS.get(socket));

                break;
            case "SERVER-A":
                System.out.println("Received an answer.");
                System.out.println(msg.getSource() + " reported: " + msg.getMsg());
                if(msg.getMsg().equals("YES"))
                    registeredConnections.add(socket);
                else
                    slave=socket;
                break;
            case "BROADCAST":

                System.out.println("Incoming broadcast from: "+msg.getSource());
                if(serverSocket.getLocalPort()==Integer.getInteger(msg.getSource()))
                {
                    System.out.println("Self Broadcast detected. Killing.");
                    break;
                }
                System.out.println(msg.getMsg());

                Worker.sendMessage(msg.getMessage(), knownOOS.get(slave));

                for(Socket regSocket : registeredConnections)
                {
                    if(socket == regSocket)
                    {
                        System.out.println("Stopping resend.");
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
