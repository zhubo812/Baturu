package org.bhu.nlp.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 用map做的计数器.
 * 
 * @param <T> 泛型
 * @author Jackie
 */
public class Counter<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<T, Double> hm = null;

	public Counter() {
		hm = new HashMap<T, Double>();
	}

	public Counter(HashMap<T, Double> hm) {
		this.hm = hm;
	}

	public Counter(int initialCapacity) {
		hm = new HashMap<T, Double>(initialCapacity);
	}

	/**
	 * 增加一个元素
	 * 
	 * @param t 词或其他统计单位
	 * @param n 频次
	 */
	public void add(T t, double n) {
		Double value = null;
		if ((value = hm.get(t)) != null) {
			hm.put(t, value + n);
		} else {
			hm.put(t, Double.valueOf(n));
		}
	}

	/**
	 * 兼容旧的api
	 * 
	 * @param t 词或其他统计单位
	 * @param n 频次
	 */
	public void add(T t, int n) {
		add(t, (double) n);
	}

	/**
	 * 计数增加.默认为1
	 * 
	 * @param t 词或其他统计单位
	 */
	public void add(T t) {
		this.add(t, 1);
	}

	/**
	 * map的大小
	 * 
	 * @return 统计词的数量
	 */
	public int size() {
		return hm.size();
	}

	/**
	 * 删除一个元素
	 * 
	 * @param t 词或其他统计单位
	 */
	public void remove(T t) {
		hm.remove(t);
	}

	/**
	 * 得道内部的map
	 * 
	 * @return 返回存储容器
	 */
	public HashMap<T, Double> get() {
		return this.hm;
	}

	/**
	 * 将map序列化为词典格式
	 * 
	 * @return 返回词典
	 */
	public String getDic() {
		Iterator<Entry<T, Double>> iterator = this.hm.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		Entry<T, Double> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			sb.append(next.getKey());
			sb.append("\t");
			sb.append(next.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * 批量增加
	 * 
	 * @param collection 词的集合
	 */
	public void addAll(Collection<T> collection) {
		for (T t : collection) {
			this.add(t);
		}
	}

	/**
	 * 批量增加
	 * @param collection 待添加元素集合
	 * @param weight 元素值
	 */
	public void addAll(Collection<T> collection, double weight) {
		for (T t : collection) {
			this.add(t, weight);
		}
	}

	/**
	 * 批量增加
	 * @param map 待添加元素集合
	 */
	public void addAll(Map<T, Double> map) {
		for (Entry<T, Double> e : map.entrySet()) {
			this.add(e.getKey(), e.getValue());
		}
	}
}
