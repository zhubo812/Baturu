package org.bhu.nlp.similarity;

import org.junit.Test;

public class CalculateKLTest {

	@Test
	public void getRelativeEntropyTest(){
		String str1 = "《《小团圆》 究竟 泄 了 张爱玲 什么 “ 秘密 ” ？ 》";
		String str2 = "《 《小团圆》 ： 张爱玲 的 一个 梦 sd df 34 sf g》";
		
		CalculateKL ck = new CalculateKL();
		double value = ck.getRelativeEntropy(str1, str2);
		System.out.println(value);
	}
	
}
