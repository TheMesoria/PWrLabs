package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Helper extends Remote
{
    void acceptFileChunk() throws RemoteException;
    void getFileChunk() throws RemoteException;
}
