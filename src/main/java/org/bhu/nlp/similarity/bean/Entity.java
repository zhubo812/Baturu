package org.bhu.nlp.similarity.bean;

/**
 * 类描述：此实体作为每个字符的一个单位
 * @author Jackie 
 *
 */
public class Entity {
	String word;// 存储字符
	float pValue;// 存储该字符对应的概率值

	public Entity() {
		pValue = 0;
		word = "";

	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public float getpValue() {
		return pValue;
	}

	public void setpValue(float pValue) {
		this.pValue = pValue;
	}

}
