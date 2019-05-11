package org.bhu.nlp.utils.string;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringHelper {
	/**  
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)  
     */    
    static final char DBC_CHAR_START = 33; // 半角!    
    
    /**  
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)  
     */    
    static final char DBC_CHAR_END = 126; // 半角~    
    
    /**  
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281  
     */    
    static final char SBC_CHAR_START = 65281; // 全角！    
    
    /**  
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374  
     */    
    static final char SBC_CHAR_END = 65374; // 全角～    
    
    /**  
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移  
     */    
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔    
    
    /**  
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理  
     */    
    static final char SBC_SPACE = 12288; // 全角空格 12288    
    
    /**  
     * 半角空格的值，在ASCII中为32(Decimal)  
     */    
    static final char DBC_SPACE = ' '; // 半角空格    
    
    private static int[] filter = new int[128];
	private static int[] filterEnd = new int[128];
    
	public static boolean isChinsesStr(String str) {
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (!isChinese(ch[i]))
				return false;
		}
		return true;
	}

	public static boolean noChinese(String str){
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (isChinese(ch[i]))
				return false;
		}
		return true;
	}
	
	public static boolean isChinese(char c) {
		int v = c;
		return (v >= 19968 && v <= 171941);
	}
	
	public static int GetChineseNumber(String text){
		int num = 0;
		char[] ch = text.toCharArray();
		for(char c : ch){
			if(isChinese(c)){
				num++;
			}
		}
		
		return num;
	}
	
	public static List<String> getWords(String segmentDoc){
		List<String> words = new ArrayList<String>();
		String[] items = StringUtils.split(segmentDoc);
		for(String item : items){
			words.add(getWord(item));
		}
		return words;
	}
	
	public static String getWord(String segmentWord){
		int index = segmentWord.indexOf("/");
		if(index<0)return segmentWord;
		return segmentWord.substring(0, index);
	}
	
	
	/**
     * 半角字符--全角字符转换    
     * 只处理空格，!到˜之间的字符，忽略其他  
     * @param src 待处理字符串 
     * @return src 处理结果字符串
     */    
    public static String bj2qj(String src) {    
        if (src == null) {    
            return src;    
        }    
        StringBuilder buf = new StringBuilder(src.length());    
        char[] ca = src.toCharArray();    
        for (int i = 0; i < ca.length; i++) {    
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代    
                buf.append(SBC_SPACE);    
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符    
                buf.append((char) (ca[i] + CONVERT_STEP));    
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理    
                buf.append(ca[i]);    
            }    
        }    
        return buf.toString();    
    }    
    
    /**   
     * 全角字符--半角字符转换    
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他 
     * @param src 待处理字符串 
     * @return src 处理结果字符串
     */    
    public static String qj2bj(String src) {    
        if (src == null) {    
            return src;    
        }    
        StringBuilder buf = new StringBuilder(src.length());    
        char[] ca = src.toCharArray();    
        for (int i = 0; i < src.length(); i++) {    
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内    
                buf.append((char) (ca[i] - CONVERT_STEP));    
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格    
                buf.append(DBC_SPACE);    
            } else { // 不处理全角空格，全角！到全角～区间外的字符    
                buf.append(ca[i]);    
            }    
        }    
        return buf.toString();    
    }   
    
    /**
	 * 判断是否为空格
	 * @param chars 待判断的char[]数组
	 * @return boolean
	 */
	public boolean isBlank(char[] chars) {
		int strLen;
		if (chars == null || (strLen = chars.length) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(chars[i]) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断是否为数字串
	 * @param str 待判断的字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 *判断是否包含数字
	 * @param str 待判断的字符串
	 * @return boolean
	 */
	public static boolean containsNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (Character.isDigit(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 去除html标签
	 * 
	 * @param input 待处理的字符串文本
	 * @return String
	 */
	public static String rmHtmlTag(String input) {
		if (isBlank(input)) {
			return "";
		}
		int length = input.length();
		int tl = 0;
		StringBuilder sb = new StringBuilder();
		char c = 0;
		for (int i = 0; i < length; i++) {
			c = input.charAt(i);

			if (c > 127) {
				sb.append(c);
				continue;
			}

			switch (filter[c]) {
			case -1:
				break;
			case 0:
				sb.append(c);
				break;
			case 1:
				if (sb.length() > 0 && sb.charAt(sb.length() - 1) != c)
					sb.append(c);
				do {
					i++;
				} while (i < length && input.charAt(i) == c);

				if (i < length || input.charAt(length - 1) != c)
					i--;
				break;
			default:
				tl = filter[c] + i;
				int tempOff = i;
				boolean flag = false;
				char end = (char) filterEnd[c];
				for (i++; i < length && i < tl; i++) {
					c = input.charAt(i);
					if (c > 127)
						continue;
					if (c == end) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					i = tempOff;
					sb.append(input.charAt(i));
				}
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param cs 待判断的字符序列
	 * @return boolean
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
