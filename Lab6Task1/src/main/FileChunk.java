package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileChunk implements Serializable
{
	private String fileName_;
	private int amountOfChunks_;
	private int chunkId_;

	private ArrayList<String> content_;

	public FileChunk()
	{
		content_ = new ArrayList<>();
	}

	public FileChunk(String path) throws FileNotFoundException
	{
		this();
		fileName_=path;
		chunkId_=1;
		amountOfChunks_=1;
		Scanner s = new Scanner(new File(path));
		while (s.hasNext())
		{
			content_.add(s.next());
		}
		s.close();
	}

	public FileChunk[] splitInto(int amount) // rValue
	{
		FileChunk[] fileChunks = new FileChunk[amount];
		int size = content_.size()/amount;
		while(amount>0)
		{
			FileChunk fileChunk = new FileChunk();
			fileChunk.setAmountOfChunks_(amountOfChunks_);
			fileChunk.setFileName_(fileName_);
			fileChunk.setChunkId_(--amount);

			if(content_.size()<amount)fileChunk.setContent_(new ArrayList<>());
			fileChunk.setContent_(new ArrayList<>(content_.subList(
					amount*size,(amount+1)*size
			)));

			fileChunks[amount]=fileChunk;
		}

		int check = 0;
		while(fileChunks.length*size+check!=content_.size())
		{
			fileChunks[fileChunks.length-1]
					.getContent_()
					.add(content_.get(fileChunks.length*size+check++));
		}

		return fileChunks;
	}
	// Getters and Setters

	public int getAmountOfChunks_()
	{
		return amountOfChunks_;
	}

	public void setAmountOfChunks_(int amountOfChunks_)
	{
		this.amountOfChunks_ = amountOfChunks_;
	}

	public int getChunkId_()
	{
		return chunkId_;
	}

	public void setChunkId_(int chunkId_)
	{
		this.chunkId_ = chunkId_;
	}

	public List<String> getContent_()
	{
		return content_;
	}

	public void setContent_(ArrayList<String> content_)
	{
		this.content_ = content_;
	}

	public String getFileName_()
	{
		return fileName_;
	}

	public void setFileName_(String fileName_)
	{
		this.fileName_ = fileName_;
	}
}
