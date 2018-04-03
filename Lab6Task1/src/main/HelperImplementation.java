package main;

import main.interfaces.Helper;
import main.interfaces.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

public class HelperImplementation extends UnicastRemoteObject implements Helper
{
	Registry registry;
	String name;

	public HelperImplementation() throws RemoteException
	{
		collection_=new HashMap<>();
	}

	private HashMap<String, FileChunk> collection_;

	@Override
	public boolean acceptFileChunk(FileChunk fileChunk) throws RemoteException
	{
		System.out.println("Attempting file save: "+fileChunk.getFileName_());
		if(collection_.containsKey(fileChunk.getFileName_())) return false;

		collection_.putIfAbsent(fileChunk.getFileName_(),fileChunk);
		return true;
	}

	@Override
	public void closeConnection() throws RemoteException, NotBoundException
	{
		System.out.println("Requested connection close for this helper.");
		registry.unbind(name);
		UnicastRemoteObject.unexportObject(this,true);
	}

	@Override
	public FileChunk getFileChunk(String name) throws RemoteException
	{
		System.out.println("Attempting read on: "+name);
		if(!collection_.containsKey(name)) return null;

		return collection_.get(name);
	}

	public void Start() throws RemoteException, AlreadyBoundException, NotBoundException
	{
		System.out.println("Helper ready");
		registry = LocateRegistry.getRegistry("localhost", 1099);
		Random random = new Random();
		name = "Helper"+Integer.toString((int)random.nextLong());
		registry.bind(name,this);
		Server server = (Server)registry.lookup("Server");
		server.registerNode(name);
	}
}
