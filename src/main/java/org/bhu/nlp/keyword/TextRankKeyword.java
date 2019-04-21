package org.bhu.nlp.keyword;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.bhu.nlp.utils.MaxHeap;

/**
 * 基于TextRank算法的关键字提取，适用于单文档
 */

public class TextRankKeyword {
	/**
	 * 提取多少个关键词
	 */

	private int nKeyword = 10;
	/**
	 * 阻尼系数（DampingFactor），一般取值为0.85
	 */

	private final static float d = 0.85f;
	/**
	 * 最大迭代次数
	 */
	private final static int max_iter = 200;
	private final static float min_diff = 0.001f;

	/**
	 * 提取关键词
	 * 
	 * @param words 文档内容
	 *            
	 * @param size 希望提取几个关键词
	 *            
	 * @return 关键词列表
	 */
	public List<String> getKeywordList(List<String> words, int size) {
		TextRankKeyword textRankKeyword = new TextRankKeyword();
		textRankKeyword.nKeyword = size;

		return textRankKeyword.getKeyword(words);
	}

	/**
	 * 提取关键词
	 * 
	 * @param words 待处理文本的词集
	 * @return 关键词结果
	 */
	private List<String> getKeyword(List<String> words) {
		Set<Map.Entry<String, Float>> entrySet = getTermAndRank(words, nKeyword).entrySet();
		List<String> result = new ArrayList<String>(entrySet.size());
		for (Map.Entry<String, Float> entry : entrySet) {
			result.add(entry.getKey());
		}
		return result;
	}



	/**
	 * 返回分数最高的前size个分词结果和对应的rank
	 * 
	 * @param words 待处理文本的词集
	 * @param size 关键词个数
	 * @return 关键词列表
	 */
	private Map<String, Float> getTermAndRank(List<String> words, Integer size) {
		Map<String, Float> map = getRank(words);
		Map<String, Float> result = new LinkedHashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size,
				new Comparator<Map.Entry<String, Float>>() {
					@Override
					public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				}).addAll(map.entrySet()).toList()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * 使用已经分好的词来计算rank
	 * 
	 * @param wordList 待处理文本的词集
	 * @return rank值
	 */
	private Map<String, Float> getRank(List<String> wordList) {
		// System.out.println(wordList);
		Map<String, Set<String>> words = new TreeMap<String, Set<String>>();
		Queue<String> que = new LinkedList<String>();
		for (String w : wordList) {
			if (!words.containsKey(w)) {
				words.put(w, new TreeSet<String>());
			}
			que.offer(w);
			if (que.size() > 5) {
				que.poll();
			}

			for (String w1 : que) {
				for (String w2 : que) {
					if (w1.equals(w2)) {
						continue;
					}

					words.get(w1).add(w2);
					words.get(w2).add(w1);
				}
			}
		}
		// System.out.println(words);
		Map<String, Float> score = new HashMap<String, Float>();
		for (int i = 0; i < max_iter; ++i) {
			Map<String, Float> m = new HashMap<String, Float>();
			float max_diff = 0;
			for (Map.Entry<String, Set<String>> entry : words.entrySet()) {
				String key = entry.getKey();
				Set<String> value = entry.getValue();
				m.put(key, 1 - d);
				for (String element : value) {
					int size = words.get(element).size();
					if (key.equals(element) || size == 0)
						continue;
					m.put(key, m.get(key) + d / size * (score.get(element) == null ? 0 : score.get(element)));
				}
				max_diff = Math.max(max_diff, Math.abs(m.get(key) - (score.get(key) == null ? 0 : score.get(key))));
			}
			score = m;
			if (max_diff <= min_diff)
				break;
		}

		return score;
	}
}
