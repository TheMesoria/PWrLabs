package main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelperImplementation extends UnicastRemoteObject implements Helper
{
	public HelperImplementation() throws RemoteException
	{
	}

	@Override
	public void acceptFileChunk() throws RemoteException
	{

	}

	@Override
	public void getFileChunk() throws RemoteException
	{
		System.out.println("Fuck off");
	}

	public void Start() throws RemoteException
	{
		System.out.println("main.Helper ready");
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		registry.rebind("TMP", this);
	}
}
