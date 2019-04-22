package org.hub.nlp.sentence;

import org.bhu.nlp.sentence.SentencesHelper;
import org.junit.Test;

public class TestDemo {

	
	@Test
	public void SentenceDemo() {
		SentencesHelper stu = new SentencesHelper();
		String line = "再见，第四套人民币！4月30日结束集中兑换。";
		System.out.println(stu.toSentenceList(line));
		
	}
}
