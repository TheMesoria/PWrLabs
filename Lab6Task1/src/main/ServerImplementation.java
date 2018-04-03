package main;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class ServerImplementation extends UnicastRemoteObject implements Server
{
	public ServerImplementation() throws RemoteException
	{
	}

	public void Start() throws RemoteException, AlreadyBoundException
	{
		System.out.println("main.Server ready.");
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("Server", this);
		try
		{
			System.in.read();
			Helper helper = (Helper) registry.lookup("TMP");
			helper.getFileChunk();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void registerNode(String nodeName) throws RemoteException
	{
		
	}

	@Override
	public void uploadFile(String path) throws RemoteException
	{

	}

	@Override
	public void getPeersForFile(String name) throws RemoteException
	{

	}
}
