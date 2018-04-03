package main;

import main.interfaces.Helper;
import main.interfaces.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

	private void read(String name) throws RemoteException, NotBoundException
	{
		ArrayList<FileChunk> fileChunkArrayList = new ArrayList<>();
		for (String node : nodes_)
		{
			Helper helper = (Helper) registry.lookup(node);
			FileChunk fileChunk = helper.getFileChunk(name);
			if (fileChunk != null)
			{
				fileChunkArrayList.add(fileChunk);
			}
		}
		int shown=0,known=fileChunkArrayList.size();
		while(shown!=known)
		{
			for(FileChunk fileChunk:fileChunkArrayList)
				if(fileChunk.getChunkId_()==shown)
					System.out.println(fileChunk.getContent_());
			shown++;
		}
	}

	public void Start() throws RemoteException, AlreadyBoundException
	{
		try
		{
			boolean done = false;
			String name;
			BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
			while (!done)
			{
				System.out.println("1. Load File.");
				System.out.println("2. Read File.");
				System.out.println("3. Show conatiners for:");
				int input = buffer.read();
				switch (input)
				{
					case '1':
						System.out.println("File path?");
						name = buffer.readLine();
						name = buffer.readLine();
						System.out.println(name);
						uploadFile(name);
						break;
					case '2':
						System.out.println("File path?");
						name = buffer.readLine();
						name = buffer.readLine();
						System.out.println(name);
						read(name);
						break;
					case '3':
						System.out.println("File path?");
						name = buffer.readLine();
						name = buffer.readLine();
						System.out.println(name);
						getPeersForFile(name);
						break;
					case 'q':
						done = true;
						break;
				}
			}


			UnicastRemoteObject.unexportObject(this, true);
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
	public void uploadFile(String path) throws RemoteException, NotBoundException, FileNotFoundException
	{
		FileChunk fileChunk = new FileChunk(path);
		FileChunk[] fileChunks = fileChunk.splitInto(nodes_.size());
		for (int i = 0; i < nodes_.size(); i++)
		{
			Helper helper = (Helper) registry.lookup(nodes_.get(i));
			if (helper.acceptFileChunk(fileChunks[i]))
			{
				System.out.println("Succesful send to: " + nodes_.get(i));
			} else
			{
				System.out.println("Fail send to: " + nodes_.get(i));
			}
		}
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
