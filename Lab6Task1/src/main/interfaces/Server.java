package main.interfaces;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote
{
    void registerNode(String nodeName) throws RemoteException;
    void uploadFile(String path) throws RemoteException, NotBoundException, FileNotFoundException;
    void getPeersForFile(String name) throws RemoteException, NotBoundException;
}
