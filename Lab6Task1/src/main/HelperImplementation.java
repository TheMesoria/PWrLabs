package main;

import main.interfaces.Helper;
import main.interfaces.Server;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HelperImplementation extends UnicastRemoteObject implements Helper
{
	Registry registry;
	String name;

	public HelperImplementation() throws RemoteException
	{
		collection_ = new HashMap<>();
	}

	private HashMap<String, FileChunk> collection_;

	@Override
	public boolean acceptFileChunk(FileChunk fileChunk) throws RemoteException
	{
		System.out.println("Attempting file save: " + fileChunk.getFileName_());
		if (collection_.containsKey(fileChunk.getFileName_())) return false;

		collection_.putIfAbsent(fileChunk.getFileName_(), fileChunk);
		File file = new File(name+"/"+new Random().nextInt());
		System.out.println(file.getPath());
		try
		{
			file.createNewFile();
			System.out.println(file.getAbsolutePath());
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(fileChunk.getContent_().toString());
			bw.flush();
		} catch (Exception e)
		{
			System.out.println(""+e.getMessage());
			System.out.println("Error");
			return false;
		}
		return true;
	}

	@Override
	public void closeConnection() throws RemoteException, NotBoundException
	{
		System.out.println("Requested connection close for this helper.");
		registry.unbind(name);
		UnicastRemoteObject.unexportObject(this, true);
	}

	@Override
	public FileChunk getFileChunk(String name) throws RemoteException
	{
		System.out.println("Attempting read on: " + name);
		if (!collection_.containsKey(name)) return null;
		return collection_.get(name);
	}

	public void Start() throws IOException, AlreadyBoundException, NotBoundException
	{
		System.out.println("Helper ready");
		registry = LocateRegistry.getRegistry("localhost", 1099);
		Random random = new Random();
		name = "Helper" + Integer.toString((int) random.nextLong());
		registry.bind(name, this);
		Server server = (Server) registry.lookup("Server");
		server.registerNode(name);

		new File(name).mkdir();

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
					server.uploadFile(name);
					break;
				case '2':
					System.out.println("File path?");
					name = buffer.readLine();
					name = buffer.readLine();
					System.out.println(name);
					ArrayList<FileChunk> fileChunkArrayList=server.read(name);
					int shown=0,known=fileChunkArrayList.size();
					while(shown!=known)
					{
						for(FileChunk fileChunk:fileChunkArrayList)
							if(fileChunk.getChunkId_()==shown)
								System.out.println(fileChunk.getContent_());
						shown++;
					}
					break;
				case '3':
					System.out.println("File path?");
					name = buffer.readLine();
					name = buffer.readLine();
					System.out.println(name);
					server.getPeersForFile(name);
					break;
				case 'q':
					done = true;
					break;
			}
		}
	}
}
