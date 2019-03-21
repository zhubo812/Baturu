package org.bhu.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileWriter{
	private final String TABLE = "\t";
	private final String LINE = "\n";
	private final byte[] TABBYTE = TABLE.getBytes();
	private final byte[] LINEBYTE = LINE.getBytes();
	private String path;
	private String encoding;
	private BufferedWriter writer;
	
	public FileWriter(String path, String encoding){
		this.path = path;
		this.encoding = encoding;
		getBuffWriter();
	}
	
	public FileWriter(String path){
		this.path = path;
		this.encoding = "utf-8";
		getBuffWriter();
	}
	 
	public FileWriter(String path, boolean addition){
		this.path = path;
		this.encoding = "utf-8";
	}
	 
	/**
	 * 按行写出，换行符为�?�\n�?
	 * @param line 待写出的字符�?
	 */
	public void writeLine(String line){
		try {
			writer.write(String.format("%s\n", line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLine(){
		try {
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeLine(Integer i){
		try {
			writer.write(String.format("%s\n", i));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按行写出，换行符为�?�\r\n�?
	 * @param line 待写出的字符�?
	 * @param flag 是否使用“\r\n”换行符
	 */
	public void writeLine(String line, boolean flag){
		try {
			writer.write(String.format("%s\r\n", line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 写入文本
	 * @param line 待写出的字符�?
	 */
	public void write(String line){
		try {
			writer.write(String.format("%s", line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void getBuffWriter(String outPath, String charset) {
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(outPath)), Charset.forName(charset)));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 实例化BuffWriter
	 */
	private synchronized void getBuffWriter() {
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(this.path)), Charset.forName(this.encoding)));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向文本追加字符串
	 * @param str 待追加的字符�?
	 */
	public void appendLine2File(String str) {
		OutputStreamWriter osw;
		try {
			// 同时创建新文�?
			osw = new OutputStreamWriter(new FileOutputStream(this.path, true),
					this.encoding);
			osw.write(String.format("%s\n", str));
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 向文本追加多行字符串,List中的每一个元素即�?行文�?
	 * @param list 存放待追加文本的列表
	 */
	public static void appendList2File(String path, List<String> list) {
		OutputStreamWriter osw;
		try {
			// 同时创建新文�?
			osw = new OutputStreamWriter(new FileOutputStream(path, true),
					"utf-8");
			for(String t : list){
				osw.write(String.format("%s\n", t));
				osw.flush();
			}
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向文本追加多行字符串，并指定输入路径
	 * @param path 文本输出路径
	 * @param str 待追加的字符�?
	 */
	public void appendLine2File(String path, String str) {
		OutputStreamWriter osw;
		try {
			// 同时创建新文�?
			osw = new OutputStreamWriter(new FileOutputStream(path, true),
					this.encoding);
			osw.write(String.format("%s\n", str));
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void appendLine2File(String path, String str, String encoding) {
		OutputStreamWriter osw;
		try {
			// 同时创建新文�?
			osw = new OutputStreamWriter(new FileOutputStream(path, true),
					encoding);
			osw.write(String.format("%s\n", str));
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * @param hm 待输出的Map
	 * 
	 */
	public <K, V> void writeMap(Map<K, V> hm){
		Iterator<Entry<K, V>> iterator = hm.entrySet().iterator();
		FileOutputStream fos = null;
		Entry<K, V> next = null;
		try {
			try {
				fos = new FileOutputStream(this.path);
				while (iterator.hasNext()) {
					next = iterator.next();
					fos.write(next.getKey().toString().getBytes());
					fos.write(TABBYTE);
					fos.write(next.getValue().toString().getBytes());
					fos.write(LINEBYTE);
				}
				fos.flush();
			} finally {
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 按照字符串的HashCode输入到文本中
	 * @param line 待输出的字符�?
	 * @param PathCode 字符串的hashcode
	 */
	public void outPutwithHashCode(String line,
			int PathCode) {
		try {
			File outDir = new File(this.path);
			if (outDir.isFile()) {
				System.out.println("请指定正确的输出路径");
				return;
			}
			if (!outDir.isDirectory())
				outDir.mkdir();
		} catch (Exception e1) {
			// e1.printStackTrace();
			System.out.println("输出路径错误");
		}
		String outFile = this.path + "/" + line.hashCode() / 1000;
		appendLine2File(outFile, line + "\t" + PathCode);
	}
	
	public void flush(){
		try {
			this.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Closes the stream, flushing it first.
	 */
	public void close(){
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
