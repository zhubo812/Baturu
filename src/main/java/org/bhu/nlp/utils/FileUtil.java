package org.bhu.nlp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class FileUtil {
	
	protected static final String SOURCE_PATH = "/org/cucbst4j/source/";
	/**
	 * 返回一个文件夹下所有文件，包括子文件夹中的文件
	 * @param inPath 待处理文件夹的路径
	 * @return 文本集合
	 */
	public static List<String> getAllFiles(String inPath) {
		List<String> fileList = new ArrayList<String>();
		getFile(inPath, fileList);
		return fileList;
	}
	
	public static Queue<String> getAllFiles2Queue(String inPath){
		Queue<String> queue = new LinkedList<String>(); 
		getFile(inPath, queue);
		return queue;
	}
	

	/**
	 * 获取文件夹下的文件并加入List
	 * @param path  待处理文件夹的路径
	 * @param collection 储存文件的列表
	 */
	private static void getFile(String path, Collection<String> collection) {
		File file = new File(path);
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				collection.add(array[i].getPath());
			} else if (array[i].isDirectory()) {
				getFile(array[i].getPath(), collection);
			}
		}
	}

	private static void getFile(String path, List<String> list) {
		File file = new File(path);
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile()) {
				list.add(array[i].getPath());
			} else if (array[i].isDirectory()) {
				getFile(array[i].getPath(), list);
			}
		}
	}
	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */
	public static void deleteFiles(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				if (!temp.renameTo(temp)) {
					System.out.println("someone use it now.");
				}
				temp.delete();
				System.out.println("delete file " + temp.getAbsolutePath());
			}
			if (temp.isDirectory()) {
				deleteFiles(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}


	/**
	 * 删除文件夹
	 * @param folderPath 文件夹完整绝对路径
	 */
	private static void delFolder(String folderPath) {
		try {
			deleteFiles(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param file 待计数的文本的File对象
	 * @return 返回文件内容的总行数。 
	 */
    public static int getTotalLines(File file){
        int lines = 0;
		try {
			java.io.FileReader in = new java.io.FileReader(file);
			LineNumberReader reader = new LineNumberReader(in);
			String s = reader.readLine();
			lines = 0;
			while (s != null) {
			    lines++;
			    s = reader.readLine();
			}
			reader.close();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return lines;
    }
    

    
    /***
     * 返回分词行的所有分词单位数组
     * @param line 待处理的分词后的字符串行
     * @return  word/cate
     */
    public String[] getWords(String line){
		String[] array = null;
		array = StringUtils.split(line);
		return array;
	}
    
    public static BufferedReader getSourceReader(String dicName) {
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(
				FileUtil.class.getResourceAsStream(SOURCE_PATH + dicName),
				Charset.defaultCharset()));
		return reader;
	}
	

	

	/***
	 * 
	 * @param reader 输入BufferedReader
	 * @return 返回姓氏概率HASH
	 */
	public static Hashtable<String, Double> getNameProHash(BufferedReader reader){
		Hashtable<String, Double> hash = new Hashtable<String, Double>();
		String[] array;
		try {
			for (String line; (line = reader.readLine()) != null;) {
				if (line.trim().length() == 0 || line.trim().startsWith("//"))
					continue;
				array = StringUtils.split(line);
				if(array.length != 2)continue;
				if(!hash.containsKey(array[0]))
					hash.put(array[0], Double.valueOf(array[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	
	public static Hashtable<String, Double> getNameProHash(String dicName){
		Hashtable<String, Double> hash = new Hashtable<String, Double>();
		BufferedReader reader = getSourceReader(dicName);
		String[] array;
		try {
			for (String line; (line = reader.readLine()) != null;) {
				if (line.trim().length() == 0 || line.trim().startsWith("//"))
					continue;
				array = StringUtils.split(line);
				if(array.length != 2)continue;
				if(!hash.containsKey(array[0]))
					hash.put(array[0], Double.valueOf(array[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	
	/***
	 * 
	 * @param inFile 输入文本路径
	 * @param outFile 输出文本路径
	 * @return 判断输入输出路径是否相同的结果
	 */
	public static boolean checkPath(String inFile, String outFile) {
		if (inFile.trim() == outFile.trim())
			return true;
		return false;
	}

	

	public static String getNumStr(String inPut) {
		String numStr = "";
		if (inPut.trim().length() == 0)
			return inPut;
		if (Character.isDigit(inPut.charAt(0))) {
			for (int i = 0; i < inPut.length(); i++) {
				if (Character.isDigit(inPut.charAt(i))) {
					numStr += inPut.charAt(i);
					if (i == inPut.length() - 1)
						return numStr;
				} else {
					if (numStr.length() == 0)
						continue;
					else {
						return numStr;
					}
				}
			}
		} else {
			return numStr;
		}
		return inPut;
	}

	public static String getLetterStr(String inPut) {
		String alpha = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
		char[] chAlpha = alpha.toCharArray();
		String letterStr = "";
		if (inPut.trim().length() == 0)
			return inPut;
		if (StringUtils.containsAny(String.valueOf(inPut.charAt(0)), chAlpha)) {
			for (int i = 0; i < inPut.length(); i++) {
				if (StringUtils.containsAny(String.valueOf(inPut.charAt(i)),
						chAlpha)) {
					letterStr += inPut.charAt(i);
					if (i == inPut.length() - 1)
						return letterStr;
				} else {
					if (letterStr.length() == 0)
						continue;
					else {
						return letterStr;
					}
				}
			}
		} else {
			return letterStr;
		}
		return inPut;
	}

	public String ToSBC(String inStr) {
		char c[] = inStr.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);
			}
		}
		return new String(c);
	}



	

	/**
	 * 
	 * 
	 * @param filePath 输入文本路径
	 * @return 获取编码，默认返回GBK
	 * @throws IOException io错误
	 */
	public static String getFileChart(String filePath) throws IOException {
		String code = "GBK";

		InputStream inputStream = new FileInputStream(filePath);
		byte[] head = new byte[3];
		inputStream.read(head);
		inputStream.close();

		if (head[0] == -1 && head[1] == -2) {
			code = "UTF-16";
		} else if (head[0] == -2 && head[1] == -1) {
			code = "Unicode";
		} else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
			code = "UTF-8";
		}
		return code;
	}
	
	protected int getWordsNum(String line){
		return StringUtils.split(line).length;
	}

	public static Set<String> getSourceDicSet(String dicName){
		Set<String> set = new HashSet<String>();
		BufferedReader reader = getSourceReader(dicName);
		
		try {
			String item;
			for(String line; (line = reader.readLine()) != null;){
				item = line.trim();
				if(item.length() == 0 || item.startsWith("//"))continue;
				set.add(item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}
	
	public static Set<String> getSourceSet(String line){
		Set<String> set = new HashSet<String>();
		String[] array = StringUtils.split(line, "|");
		for(String item : array){
			set.add(item.trim());
		}
		return set;
	}
	
	

    
}
