package main.interfaces;

import main.FileChunk;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Server extends Remote
{
    void registerNode(String nodeName) throws RemoteException;
    void uploadFile(String path) throws RemoteException, NotBoundException, FileNotFoundException;
    void getPeersForFile(String name) throws RemoteException, NotBoundException;
    ArrayList<FileChunk> read(String name) throws RemoteException, NotBoundException;
}
