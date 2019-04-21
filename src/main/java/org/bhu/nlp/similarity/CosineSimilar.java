package org.bhu.nlp.similarity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bhu.io.FileWriter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

 
/**
 * 类描述： 计算文本余弦相似度
 * @author Jackie
 *
 */
public class CosineSimilar {
	private static BiMap<String, Integer> wordset = HashBiMap.create();
	private static int vCounter = 0;

	/***
	 * 适用于利用词集（多个词）查找与其相似的句子， wordset中每个元素表示词集中各个词连成的字符串 sentences中每个元素是一个句子
	 * outPath表示结果输出的路径
	 * 
	 * @param wordset 词集合
	 * @param sentences 句子结合
	 * @param outPath 输出路径
	 */
	public void getSetSimilarity(List<String> wordset, List<String> sentences,
			String outPath) {
		FileWriter writer = new FileWriter(outPath, "utf-8");
		for (String sen : sentences) {
			String outLine = "";
			double result = 0.0;
			System.out.println(sen);
			for (String ws : wordset) {
				result = getSimilarity(ws, sen);
				outLine += result + "\t";
			}
			outLine = outLine.trim();
			writer.write(sen + "\t" + outLine + "\n");
		}
		writer.close();
	}

	


	/**
	 *    
	 * @param words1 词集1
	 * @param words2 词集2
	 * @return 计算两个词集的相似度
	 */
	public Double getSimilarityByWord(List<String> words1, List<String> words2){
		Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();

		// 将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中
		for (String word : words1) {
				int wordIndex = getID(word);
				if (wordIndex != -1) {
					int[] fq = AlgorithmMap.get(wordIndex);
					if (fq != null && fq.length == 2) {
						fq[0]++;
					} else {
						fq = new int[2];
						fq[0] = 1;
						fq[1] = 0;
						AlgorithmMap.put(wordIndex, fq);
					}
				}
			}
		
		
		for (String word : words2) {
			int wordIndex = getID(word);
			if (wordIndex != -1) {
				int[] fq = AlgorithmMap.get(wordIndex);
				if (fq != null && fq.length == 2) {
					fq[1]++;
				} else {
					fq = new int[2];
					fq[0] = 0;
					fq[1] = 1;
					AlgorithmMap.put(wordIndex, fq);
				}
			}
		}
		
		Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
		Double sqdoc1 = 0.0;
		Double sqdoc2 = 0.0;
		Double denominator = 0.0;
		while (iterator.hasNext()) {
			int[] c = AlgorithmMap.get(iterator.next());
			denominator += c[0] * c[1];
			sqdoc1 += c[0] * c[0];
			sqdoc2 += c[1] * c[1];
		}
		if(Math.sqrt(sqdoc1 * sqdoc2)==0){
			return 0.0;
		}

		return denominator / Math.sqrt(sqdoc1 * sqdoc2);
	}
	
	public Double getSetSimilarity(List<String> wordset, String sentence) {
		String sline = "";
		for (String ws : wordset) {
			sline += ws;
		}
		return getSimilarity(sline, sentence);
	}

	public Double getSimilarity(String doc1, String doc2) {
		if (doc1 != null && doc1.trim().length() > 0 && doc2 != null
				&& doc2.trim().length() > 0) {

			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();

			// 将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中
			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				if (isHanZi(d1)) {
					int charIndex = getGB2312Id(d1);
					if (charIndex != -1) {
						int[] fq = AlgorithmMap.get(charIndex);
						if (fq != null && fq.length == 2) {
							fq[0]++;
						} else {
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}

			for (int i = 0; i < doc2.length(); i++) {
				char d2 = doc2.charAt(i);
				if (isHanZi(d2)) {
					int charIndex = getGB2312Id(d2);
					if (charIndex != -1) {
						int[] fq = AlgorithmMap.get(charIndex);
						if (fq != null && fq.length == 2) {
							fq[1]++;
						} else {
							fq = new int[2];
							fq[0] = 0;
							fq[1] = 1;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}

			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			Double sqdoc1 = 0.0;
			Double sqdoc2 = 0.0;
			Double denominator = 0.0;
			while (iterator.hasNext()) {
				int[] c = AlgorithmMap.get(iterator.next());
				denominator += c[0] * c[1];
				sqdoc1 += c[0] * c[0];
				sqdoc2 += c[1] * c[1];
			}

			return denominator / Math.sqrt(sqdoc1 * sqdoc2);
		} else {
			throw new NullPointerException(
					" the Document is null or have not cahrs!!");
		}
	}

	public boolean isHanZi(char ch) {
		// 判断是否汉字
		return (ch >= 0x4E00 && ch <= 0x9FA5);

	}

	/**
	 * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，
	 * 
	 * @param ch 输入的GB2312中文字符或者ASCII字符(128个)
	 * @return ch在GB2312中的位置，-1表示该字符不认识
	 */
	public static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				// 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
				return -1;
			}
			int b0 = (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161
			int b1 = (buffer[1] & 0x0FF) - 161; // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
			return (short) (b0 * 94 + b1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 
	 * @Title: calculateCos
	 * @Description: 计算余弦相似性
	 * @param  first 集合1
	 * @param  second 集合2
	 * @return Double 返回相似度
	 */
	@SuppressWarnings("unused")
	private Double calculateCos(LinkedHashMap<String, Integer> first,
			LinkedHashMap<String, Integer> second) {

		List<Map.Entry<String, Integer>> firstList = new ArrayList<Map.Entry<String, Integer>>(
				first.entrySet());
		List<Map.Entry<String, Integer>> secondList = new ArrayList<Map.Entry<String, Integer>>(
				second.entrySet());
		// 计算相似度
		double vectorFirstModulo = 0.00;// 向量1的模
		double vectorSecondModulo = 0.00;// 向量2的模
		double vectorProduct = 0.00; // 向量积
		int secondSize = second.size();
		for (int i = 0; i < firstList.size(); i++) {
			if (i < secondSize) {
				vectorSecondModulo += secondList.get(i).getValue()
						.doubleValue()
						* secondList.get(i).getValue().doubleValue();
				vectorProduct += firstList.get(i).getValue().doubleValue()
						* secondList.get(i).getValue().doubleValue();
			}
			vectorFirstModulo += firstList.get(i).getValue().doubleValue()
					* firstList.get(i).getValue().doubleValue();
		}
		return vectorProduct
				/ (Math.sqrt(vectorFirstModulo) * Math.sqrt(vectorSecondModulo));
	}
	
	private int getID(String word){
		Integer id = null;
		id = wordset.get(word);
		if (id == null) {
			id = vCounter;
			wordset.put(word, vCounter);
			vCounter++;
		}
		return id;
	}
}









