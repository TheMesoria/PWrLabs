package main;

import main.interfaces.Helper;
import main.interfaces.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerImplementation extends UnicastRemoteObject implements Server
{
	public ServerImplementation() throws RemoteException, AlreadyBoundException
	{
		System.out.println("main.interfaces.Server ready.");
		registry = LocateRegistry.createRegistry(1099);
		registry.bind("Server", this);
		nodes_ = new ArrayList<>();
	}

	Registry registry;
	ArrayList<String> nodes_;

	public void Start() throws RemoteException, AlreadyBoundException
	{
		try
		{
			System.in.read();
			System.out.println(Arrays.toString(registry.list()));
			Helper helper = (Helper) registry.lookup(nodes_.get(0));
			FileChunk fileChunk = new FileChunk("/home/black/test.elm");
			if(helper.acceptFileChunk(fileChunk))
			{
				System.out.println("File accepted.");
			}
			else
			{
				System.out.println("File rejected");
			}

			FileChunk fileChunkRec = helper.getFileChunk("/home/black/test.elm");
			if(fileChunk!=null)
			{
				System.out.println(fileChunk.getContent_());
			}
			for(String name : nodes_)
			{
				Helper closer = (Helper) registry.lookup(name);
			}
			UnicastRemoteObject.unexportObject(this,true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void registerNode(String nodeName) throws RemoteException
	{
		System.out.println("Registration attempt on: " + nodeName);
		nodes_.add(nodeName);
	}

	@Override
	public void uploadFile(String path) throws RemoteException
	{

	}

	@Override
	public void getPeersForFile(String name) throws RemoteException, NotBoundException
	{
		System.out.println("Known nodes:");
		System.out.println(nodes_);

		for (String node : nodes_)
		{
			Helper helper = (Helper) registry.lookup(node);
			FileChunk fileChunk = helper.getFileChunk(name);
			if (fileChunk != null)
			{
				System.out.println(node + ", contains:");
				System.out.println(fileChunk.getContent_());
			}
		}
	}
}
