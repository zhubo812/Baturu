package org.bhu.nlp.keyword;


import java.util.ArrayList;
import java.util.List;

import org.bhu.io.FileReader;
import org.bhu.nlp.keyword.TextRankKeyword;
import org.junit.Test;

public class TextRandKeywordDemo {

	@Test
	public void demo() {
		FileReader reader = new FileReader("data/1.txt.sg.txt");
		TextRankKeyword trk = new TextRankKeyword();
		List<String> lines = reader.read2List();
		List<String> words = new ArrayList<String>();
		for(String line : lines) {
			String[] items = line.split(" ");
			for(String word : items) {
				if(word.trim().length()==0)continue;
				words.add(word);
			}
			
		}
		
		int size =10;
		List<String> keywords = trk.getKeywordList(words, size);
		System.out.println(keywords);
		
	}
}
