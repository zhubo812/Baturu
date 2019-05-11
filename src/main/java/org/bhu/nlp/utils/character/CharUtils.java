package org.bhu.nlp.utils.character;

public class CharUtils {

	public static String filterEmoji(String src) {
		StringBuilder buf = new StringBuilder();
		int len = src.length();
		for (int i = 0; i < len; i++) {
			char codePoint = src.charAt(i);
			if (isEmojiCharacter(codePoint)) {
//				continue;
				buf.append(codePoint);
			} else {
				System.out.println(String.format("remove char %s", codePoint));
			}
		}
		if (buf.length() > 0) {
			return buf.toString();
		}
		return src;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
}
