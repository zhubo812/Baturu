package org.bhu.nlp.similarity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bhu.io.FileReader;
import org.bhu.io.FileWriter;
import org.junit.Test;

public class SimilarityDemo {
	CosineSimilar similar = new CosineSimilar();
	
	@Test
	public void phrasseSimilarityDemo(){
		CosineSimilar similar = new CosineSimilar();
		String path = "F:/workspace/data/js.txt";
		FileReader reader = new FileReader(path);
		List<String> list = reader.read2List();
		List<String> phr = new ArrayList<String>();
		//TokenizeUtils tu = new TokenizeUtils();
		//tu.initUsrDic();
		StringBuilder sb = new StringBuilder();
		sb.append(" ").append("\t");
		for(String item : list){
			sb.append(item).append("\t");
			//phr.add(tu.getWordsLine_Usr(item));
		}
		sb.append("\n");
		for(int i=0;i< phr.size();i++){
			sb.append(phr.get(i).replace(" ", "")).append("\t");
			for(int j=0;j<phr.size();j++){
				
//				double similarity = similar.getSimilarityByWord(Arrays.asList(StringUtils.split(phr.get(i))), Arrays.asList(StringUtils.split(phr.get(j))));
				double similarity = similar.getSimilarity(phr.get(i), phr.get(j));
				sb.append(similarity).append("\t");
			}
			sb.append("\n");
		}
		
		FileWriter writer = new FileWriter("F:/workspace/data/js.rs.txt");
		writer.write(sb.toString());
		writer.close();
		System.out.println("ok");
	}
	

	@Test
	public void TitleCosineSimilarDemo(){
		String filePath = "C:/Users/E430/Desktop/title.txt";
		FileReader reader = new FileReader(filePath);
		List<String> titles = reader.read2List();
		CosineSimilar similar = new CosineSimilar();
		String [][] matrix = new String[titles.size()+1][titles.size()+1];
		StringBuffer sb = new StringBuffer();
		List<String> resultLines = new ArrayList<String>();
		for(int i =0; i < titles.size(); i++){
			sb.append(titles.get(i));
			sb.append("\t");
			for(int j=0; j<titles.size(); j++){
				double result = similar.getSimilarity(titles.get(i), titles.get(j));
				sb.append(result);
				sb.append("\t");
			}
			resultLines.add(sb.toString());
			sb.delete(0, sb.length());
		}
		FileWriter writer = new FileWriter("C:/Users/E430/Desktop/titleResult.txt");
		for(String sline : resultLines){
//			System.out.println(sline);
			writer.writeLine(sline);
		}
		writer.close();
	}

	@Test
	public void demo(){
		String wordfile1 = "/home/jackie/Untitled Folder/mh_words.txt";
//		String wordfile2 = "/home/jackie/Untitled Folder/ebola_words.txt";
		String wordfile2 = "/home/jackie/Untitled Folder/zz_words.txt";
		FileReader reader = new FileReader(wordfile1);
		FileReader senreader = new FileReader(wordfile2);
		List<String> wordset1 = reader.read2List();
		List<String> wordset2 = senreader.read2List();
		double similarity = similar.getSimilarityByWord(wordset1, wordset2);
		System.out.println(similarity);
	}
	
	
	@SuppressWarnings("unused")
	private void CosineSimilarDemo(){
		String wordfile = "/home/jackie/output/topicword";
		String sentencesfile = "/home/jackie/output/sentences";
		String outPath = "/home/jackie/output/result";
		FileReader reader = new FileReader(wordfile);
		FileReader senreader = new FileReader(sentencesfile);
		List<String> wordset = reader.read2List();
		List<String> sentences = senreader.read2List();
		new CosineSimilar().getSetSimilarity(wordset, sentences,outPath);
	}
	
	
	@Test
	public void CosineSimilarListDemo(){
		String[] words1= {"日本","男子","间谍","被捕","活动","从事"};
		String title = "6名日本男子在华从事间谍活动被拘";
		String title2 = "6 名 日本 男子 在 华 从事 间谍 活动 被 拘";
		String[] words2 = title2.split(" ");

		System.out.println(similar.getSetSimilarity(Arrays.asList(words1), title));
		System.out.println(similar.getSimilarityByWord(Arrays.asList(words1), Arrays.asList(words2)));
	
	}
	
}
