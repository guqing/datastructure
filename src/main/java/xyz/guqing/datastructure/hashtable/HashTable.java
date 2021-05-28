package xyz.guqing.datastructure.hashtable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashTable<K, V> {
	@SuppressWarnings("rawtypes")
	private Entry[] table;
	private int size;
	// table表中的元素超过threshold阈值就扩容
	private int threshold;
	private float loadFactor;
	private transient volatile Set<K> keySet;
	private transient volatile Collection<V> values;

	/**
	 * 能分配数组的最大长度,0x7fffffff即2的31次方-1
	 */
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	public HashTable() {
		this(11, 0.75f);
	}

	public HashTable(int initialCapacity, float loadFactor) {
		// 检查参数的合法性
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Illegal Capacity:" + initialCapacity);
		}
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal loadFactor:" + loadFactor);
		}

		this.loadFactor = loadFactor;
		if (initialCapacity == 0) {
			// 如果初始化容量为0创建大小为1的数组
			initialCapacity = 1;
		}
		// 创建Entry数组
		table = new Entry[initialCapacity];
		// 计算阈值,默认11 * 0.75 = 8,避免创建时传入的initialCapacity超过MAX_ARRAY_SIZE
		threshold = (int) Math.min(initialCapacity * loadFactor, MAX_ARRAY_SIZE + 1);
	}

	// 在jdk中Hashtable通过在方法上加上synchronized来保证线程安全,照猫画虎
	@SuppressWarnings("unchecked")
	public synchronized V put(K key, V value) {
		if (value == null) {
			// 如果值为null不允许存放
			throw new NullPointerException();
		}
		Entry<K,V>[] tab = table;
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
			// 判断新加入的key是否在table中存在,存在则更新value
			if ((e.hash == hash) && e.key.equals(key)) {
				V old = e.value;
				e.value = value;
				// 插入后返回旧值
				return old;
			}
		}

		// 判断是否超过阈值，超过就扩容
		if (size >= threshold) {
			// 扩容并重新计算hash
			rehash();
			tab = table;
			index = (hash & 0x7FFFFFFF) % tab.length;
		}

		Entry<K, V> e = tab[index];
		// 插入元素时直接插入在链表头部
		tab[index] = new Entry<K, V>(hash, key, value, e);
		size++;
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized V get(K key) {
		if (key == null) {
			throw new NullPointerException();
		}
		Entry[] tab = table;
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % table.length;
		Entry<K, V> entry = tab[index];
		while (entry != null) {
			if ((entry.hash == hash) && entry.key.equals(key)) {
				// 插入后返回旧值
				return entry.value;
			}
			entry = entry.next;
		}

		return null;
	}

	public int size() {
		return size;
	}

	public synchronized boolean isEmpty() {
        return size == 0;
    }
	
	@SuppressWarnings("unchecked")
	private void rehash() {
		Entry<?, ?>[] oldTable = table;

		int oldCapacity = table.length;
		
		/*if(oldCapacity == MAX_ARRAY_SIZE) {
			 //如果oldCapacity已经达到极限直接返回不在扩容
			 return;
		 }*/
		
		// 扩容至原来长度的2倍+1
		int newCapacity = (oldCapacity << 1) + 1;
		if (newCapacity > MAX_ARRAY_SIZE) {
			// 将判断写在这里可以减少一次判断,外条件不符合时就不会判断里面的条件
			if (oldCapacity == MAX_ARRAY_SIZE) {
				// 如果oldCapacity已经达到极限直接返回不在扩容
				return;
			}
			newCapacity = MAX_ARRAY_SIZE;
		}
		
		Entry<?, ?>[] newTable = new Entry<?, ?>[newCapacity];

		//重新计算扩容阈值
		threshold = (int) Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE + 1);
		table = newTable;

		for (int i = 0; i < oldCapacity; i++) {
			Entry<K, V> old = (Entry<K, V>) oldTable[i];
			while (old != null) {
				// 将old保存起来
				Entry<K, V> e = old;
				//往下寻找
				old = old.next;
				// 重新计算index,将原来元素的位置放在新的index处
				int index = (e.hash & 0x7FFFFFFF) % newCapacity;
				e.next = (Entry<K, V>) newTable[index];
				newTable[index] = e;
			}
		}
	}

	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public synchronized V remove(K key) {
		if (!contains(key)) {
			return null;
		}
		Entry<?,?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        @SuppressWarnings("unchecked")
        Entry<K,V> e = (Entry<K,V>)tab[index];
        for(Entry<K,V> prev = null ; e != null ; prev = e, e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                size--;
                V oldValue = e.value;
                e.value = null;
                return oldValue;
            }
        }
        return null;
	}
	
	public synchronized void clear() {
        Entry<?,?> tab[] = table;
        for (int index = tab.length; --index >= 0; ) {
        	tab[index] = null;
        }
        keySet = null;
        values = null;
        size = 0;
    }
	
	public Set<K> keySet() {
        if (keySet==null) {
        	keySet = Collections.synchronizedSet(getKeySet());
        }
        return keySet;
    }
	
	private synchronized Set<K> getKeySet() {
		Set<K> keySet = new HashSet<>();
		@SuppressWarnings("unchecked")
		Entry<K,V>[] tab = (Entry<K,V>[])table;
		for(int i=0; i<tab.length; i++) {
			Entry<K, V> entry = tab[i];
			while (entry != null) {
				if (entry.key != null) {
					keySet.add(entry.key);
				}
				entry = entry.next;
			}
		}
		return keySet;
	}
	
	public Collection<V> values() {
        if (values==null)
            values = Collections.synchronizedCollection(getValues());
        return values;
    } 
	
	private synchronized Collection<V> getValues() {
		List<V> values = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Entry<K,V>[] tab = (Entry<K,V>[])table;
		for(int i=0; i<tab.length; i++) {
			Entry<K, V> entry = tab[i];
			while (entry != null) {
				if (entry.key != null) {
					values.add(entry.value);
				}
				entry = entry.next;
			}
		}
		return values;
	}
	
	private static class Entry<K, V> {
		int hash;
		K key;
		V value;
		// entry是一个链表所以需要next指针
		Entry<K, V> next;

		public Entry(int hash, K key, V value, Entry<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	public static void main(String[] args) {
		// hash值相同分两个key:Aa BB都是2112
		HashTable<String, String> hashTable = new HashTable<>();
		hashTable.put("name", "张三");
		hashTable.put("sex", "男");
		hashTable.put("age", "18");
		hashTable.put("birthday", "9月18日");
		
		hashTable.remove("name");
		
		Set<String> keySet = hashTable.keySet();
		System.out.println(keySet);
		System.out.println(hashTable.values());
		
		hashTable.clear();
		System.out.println("size:" + hashTable.size() + ", values:" + hashTable.values());
	}
}
