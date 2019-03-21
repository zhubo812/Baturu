package org.bhu.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FastReader {

	private byte[] bytes;
	private String content = null;
	
	public FastReader(String path){
		try {
			iniReader(path);
			toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] readLines(){
		return this.content.split("\n");
	}
	
	public String[] readLines(String separator){
		return this.content.split(separator);
	}
	
	private void iniReader(String path) throws IOException{
		FileInputStream fis = new FileInputStream(path);
		FileChannel channel = fis.getChannel();
		int fileSize = (int) channel.size();
		ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
		channel.read(byteBuffer);
		byteBuffer.flip();
		this.bytes = byteBuffer.array();
		byteBuffer.clear();
		channel.close();
		fis.close();
	}
	
	
	public String read2End(){
		return this.content;
	}
	
	@Override
	public String toString(){
		try {
			if(content == null){
				content = new String(bytes,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
}
