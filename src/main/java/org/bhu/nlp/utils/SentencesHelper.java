package org.bhu.nlp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本断句
 * 
 * @author Jackie
 * 
 */
 
public class SentencesHelper {
	
	public static List<String> toSentenceList(String content) {
		return toSentenceList(content.toCharArray());
	}
	
	public static List<String> toSentenceListNoChar(String content) {
		return toSentenceListNoChar(content.toCharArray());
	}

	private static List<String> toSentenceList(char[] chars) {

		StringBuilder sb = new StringBuilder();

		List<String> sentences = new ArrayList<String>();

		for (int i = 0; i < chars.length; i++) {
			if (sb.length() == 0 && (Character.isWhitespace(chars[i]) || chars[i] == ' ')) {
				continue;
			}

			sb.append(chars[i]);
			switch (chars[i]) {
			case '.':
				if (i < chars.length - 1 && chars[i + 1] > 128) {
					insertIntoList(sb, sentences);
					sb = new StringBuilder();
				}
				break;
			case '…':
				insertIntoList(sb, sentences);
				sb = new StringBuilder("…");
				break;
			case '\t':
			case '。':
			case ';':
			case '；':
			case '!':
			case '！':
			case '?':
			case '？':
			case '、':
			case ' ':
			case '\n':
			case '\r':
				insertIntoList(sb, sentences);
				sb = new StringBuilder();
				break;
			}
		}

		if (sb.length() > 0) {
			insertIntoList(sb, sentences);
		}

		return sentences;
	}

	private static void insertIntoList(StringBuilder sb, List<String> sentences) {
		String content = sb.toString().trim();
		if (content.length() > 0) {
			sentences.add(content);
		}
	}
	
	private static List<String> toSentenceListNoChar(char[] chars) {

		StringBuilder sb = new StringBuilder();

		List<String> sentences = new ArrayList<String>();

		for (int i = 0; i < chars.length; i++) {
			if (sb.length() == 0 && (Character.isWhitespace(chars[i]) || chars[i] == ' ')) {
				continue;
			}

			
			switch (chars[i]) {
			case '.':
				if (i < chars.length - 1 && chars[i + 1] > 128) {
					insertIntoList(sb, sentences);
					sb = new StringBuilder();
				}
				break;
			case '…':
			case '\t':
			case ',':
			case '，':
			case '。':
			case ';':
			case '"':
			case '、':
			case '：':
			case ':':
			case '；':
			case '“':
			case '”':
			case '!':
			case '！':
			case '?':
			case '？':
			case ' ':
			case '\n':
			case '\r':
				insertIntoList(sb, sentences);
				sb = new StringBuilder();
				continue;
			}
			sb.append(chars[i]);
		}
		
		if (sb.length() > 0) {
			insertIntoList(sb, sentences);
		}

		return sentences;
	}



}
