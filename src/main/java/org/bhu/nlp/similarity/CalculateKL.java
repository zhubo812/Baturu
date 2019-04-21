package org.bhu.nlp.similarity;
import java.io.BufferedReader;
//import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bhu.nlp.similarity.bean.Entity;



/***
 * 
 * 类描述：计算两个文本间的相对熵
 * 
 * @author Jackie
 *
 */
public class CalculateKL {

	/**
	 * 
	 * @param arg0 待计算相对熵的文本1
	 * @param arg1 待计算相对熵的文本2
	 * @return 返回相对熵值
	 */
	public double getRelativeEntropy(String arg0, String arg1){
		ArrayList<Entity> enList1 = new ArrayList<Entity>();
		ArrayList<Entity> enList2 = new ArrayList<Entity>();
		
		enList1 = CalcuProbWordLine(arg0);
		enList2 = CalcuProbWordLine(arg1);
		
		return CalKL(enList1, enList2);
	}
	
	public double getRelativeEntropy(List<String> arg0, List<String> arg1){
		ArrayList<Entity> enList1 = new ArrayList<Entity>();
		ArrayList<Entity> enList2 = new ArrayList<Entity>();
		
		enList1 = CalcuProbWordSet(arg0);
		enList2 = CalcuProbWordSet(arg1);
		
		return CalKL(enList1, enList2);
	}

	/********
	 * this function read in a string from disk file*
	 * @throws FileNotFoundException
	 * @param path 输入文件路径
	 * @return 本文数据流
	 ***/

	public String GetFileText(String path) throws FileNotFoundException,
			IOException {
		InputStreamReader inStreamReader = new InputStreamReader(
				new FileInputStream(path), "UTF-8");
		// String strFile1=
		BufferedReader bufReader = new BufferedReader(inStreamReader);
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			sb.append(line + "　");
		}
		inStreamReader.close();
		bufReader.close();
		String strFile = sb.toString();

		return strFile;

	}


	/**
	 * 
	 * @param path 输入文本路径
	 * @throws FileNotFoundException，IOException
	 * @return 返回过滤后的文本
	 */
	public String CutTextSingleCharacter(String path)
			throws FileNotFoundException, IOException {
		String text = GetFileText(path);
		String proText = null;
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5\\uF900-\\uFA2D]");
		Matcher m = pattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		Boolean flag = m.find();
		while (flag) {
			int start = m.start();
			int end = m.end();
			sb.append(text.substring(start, end) + "|");
			// System.out.println(text.substring(start,end));
			flag = m.find();
		}
		proText = sb.toString();
		return proText;
	}

	
	
	
	private ArrayList<Entity> CalcuProbWordSet(Collection<String> wordset){ // 以词为单位计算相对熵
//		String result = CutText(path);
		// 以字为单位计算相对熵
		// String result=CutTextSingleCharacter(path);
//		String[] words = StringUtils.split(wordsLine);

		ArrayList<Entity> enList = new ArrayList<Entity>();
		for (String w : wordset) {
			w = w.trim();
			Entity en = new Entity();
			en.setWord(w);
			en.setpValue(1);
			enList.add(en);
			// System.out.println(w);
		}

		float total = enList.size();
		for (int i = 0; i < enList.size() - 1; i++) {

			if (!enList.get(i).getWord().isEmpty()) {
				for (int j = i + 1; j < enList.size(); j++) {
					if (enList.get(i).getWord().equals(enList.get(j).getWord())) {
						enList.get(i).setpValue(enList.get(i).getpValue()+1);
//						enList.get(i).pValue++;
						enList.get(j).setpValue(0);
						enList.get(j).setWord("");
					}
				}
			}
		}
		for (int i = enList.size() - 1; i >= 0; i--) {
			if (enList.get(i).getpValue() < 1.0)
				enList.remove(i);
		}
		for (int i = 0; i < enList.size(); i++) {
			enList.get(i).setpValue(enList.get(i).getpValue() / total);
		}

		return enList;
	}
	
	private ArrayList<Entity> CalcuProbWordLine(String wordsLine){ // 以词为单位计算相对熵
//		String result = CutText(path);
		// 以字为单位计算相对熵
		// String result=CutTextSingleCharacter(path);
		String[] words = wordsLine.split(" ");

		ArrayList<Entity> enList = new ArrayList<Entity>();
		for (String w : words) {
			w = w.trim();
			Entity en = new Entity();
			en.setWord(w);
			en.setpValue(1);
			enList.add(en);
			// System.out.println(w);
		}

		float total = enList.size();
		for (int i = 0; i < enList.size() - 1; i++) {

			if (!enList.get(i).getWord().isEmpty()) {
				for (int j = i + 1; j < enList.size(); j++) {
					if (enList.get(i).getWord().equals(enList.get(j).getWord())) {
						enList.get(i).setpValue(enList.get(i).getpValue()+1);
//						enList.get(i).pValue++;
						enList.get(j).setpValue(0);
						enList.get(j).setWord("");
					}
				}
			}
		}
		for (int i = enList.size() - 1; i >= 0; i--) {
			if (enList.get(i).getpValue() < 1.0)
				enList.remove(i);
		}
		for (int i = 0; i < enList.size(); i++) {
			enList.get(i).setpValue(enList.get(i).getpValue() / total);
		}

		return enList;
	}

	/* 用于计算两段文本的相对熵 */
	private static float CalKL(ArrayList<Entity> p, ArrayList<Entity> q) {
		float kl = 0;

		float infinity = 10000000;// 无穷大
		double accretion = infinity;// 设置熵增加量的初始值为无穷大。
		// 从q中找出与p中相对应词的概率，如果找到了，就将accretion的值更新，并累加到相对熵上面；如果没找到，则增加了为无穷大
		for (int i = 0; i < p.size(); i++) {
			if (q.size() != 0) {
				for (int j = q.size() - 1; j >= 0; j--) {
					if (p.get(i).getWord().equals(q.get(j).getWord())) {
						accretion = p.get(i).getpValue()
								* Math.log(p.get(i).getpValue() / q.get(j).getpValue());
						// q.remove(j);
						break;

					}
				}

				kl += accretion;
				accretion = infinity;
			}

		}
		return kl;
	}

}
