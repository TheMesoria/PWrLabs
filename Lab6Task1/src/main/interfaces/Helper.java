package main.interfaces;

import main.FileChunk;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Helper extends Remote
{
    boolean acceptFileChunk(FileChunk fileChunk) throws RemoteException;
    FileChunk getFileChunk(String name) throws RemoteException;
    void closeConnection() throws RemoteException, NotBoundException;
}
