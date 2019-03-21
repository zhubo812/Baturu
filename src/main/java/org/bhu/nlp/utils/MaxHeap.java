package org.bhu.nlp.utils;

import java.util.*;

/**
 * �ù̶����������ȶ���ģ������ѣ����ڽ����topN�������
 *
 * @author hankcs
 */
public class MaxHeap<E>
{
    /**
     * ���ȶ���
     */
    private PriorityQueue<E> queue;
    /**
     * �ѵ��������
     */
    private int maxSize;

    /**
     * ��������
     * @param maxSize �������ٸ�Ԫ��
     * @param comparator �Ƚ�������������ʹ��o1-o2��������С��ʹ��o2-o1�����޸� e.compareTo(peek) �ȽϹ���
     */
    public MaxHeap(int maxSize, Comparator<E> comparator)
    {
        if (maxSize <= 0)
            throw new IllegalArgumentException();
        this.maxSize = maxSize;
        this.queue = new PriorityQueue<E>(maxSize, comparator);
    }

    /**
     * ���һ��Ԫ��
     * @param e Ԫ��
     * @return �Ƿ���ӳɹ�
     */
    public boolean add(E e)
    {
        if (queue.size() < maxSize)
        { // δ�ﵽ���������ֱ�����
            queue.add(e);
            return true;
        }
        else
        { // ��������
            E peek = queue.peek();
            if (queue.comparator().compare(e, peek) > 0)
            { // ����Ԫ���뵱ǰ�Ѷ�Ԫ�رȽϣ�������С��Ԫ��
                queue.poll();
                queue.add(e);
                return true;
            }
        }
        return false;
    }

    /**
     * ������Ԫ��
     * @param collection
     */
    public MaxHeap<E> addAll(Collection<E> collection)
    {
        for (E e : collection)
        {
            add(e);
        }

        return this;
    }

    /**
     * תΪ�����б��Ի��Բ���
     * @return
     */
    public List<E> toList()
    {
        ArrayList<E> list = new ArrayList<E>(queue.size());
        while (!queue.isEmpty())
        {
            list.add(0, queue.poll());
        }

        return list;
    }
}
