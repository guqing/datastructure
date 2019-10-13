package xyz.guqing.tree;

public class BTree<K extends Comparable<K>, V>  {
    // 每个b树节点的最大子节点数= M-1(必须是偶数且大于2)
    private static final int M = 4;

    // B-tree的根节点
    private Node root;
    // B-tree的高度
    private int height;
    // B树中键值对的数量
    private int n;

    // B树的辅助节点数据类型
    private static final class Node {
        // 子节点的数量
        private int m;
        // 子节点数组
        private Entry[] children = new Entry[M];

        // 创建一个包含k个子节点的节点
        private Node(int k) {
            m = k;
        }
    }

    // 内部节点:只使用key和next
    // 外部节点:只使用key和value
    @SuppressWarnings("rawtypes")
    private static class Entry {
		private Comparable key;
        private final Object val;
        // 帮助字段来遍历数组item
        private Node next;
        public Entry(Comparable key, Object val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * 初始化一棵空的B-tree.
     */
    public BTree() {
        root = new Node(0);
    }
 
    /**
     * 如果此符号表为空，则返回true
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 返回此符号表中的键值对的数目
     * @return 此符号表中的键值对的数目
     */
    public int size() {
        return n;
    }

    /**
     * 返回此B-Tree的高度(用于调试)
     *
     * @return B-tree的高度
     */
    public int height() {
        return height;
    }


    /**
     * 返回与给定键关联的值
     *
     * @param  key the key
     * @return the value 如果给定的键存在则返回否则返回null
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height);
    }

    @SuppressWarnings("unchecked")
	private V search(Node x, K key, int ht) {
        Entry[] children = x.children;

        // 外部节点
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) {
                	return (V) children[j].val;
                }
            }
        }

        // 内部节点
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key)) {
                	return search(children[j].next, key, ht-1);
                }
            }
        }
        
        return null;
    }


    /**
     * 将键-值对插入符号表，如果键已经在符号表中，则用新值覆盖旧值。如果该值为空，则有效地从符号表中删除键。
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = insert(root, key, val, height); 
        n++;
        if (u == null) {
        	return;
        }

        // 需要分割根节点
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node h, K key, V val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        // 外部节点
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // 内部节点
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht-1);
                    if (u == null){
                    	return null;
                    }
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) {
        	h.children[i] = h.children[i-1];
        }
        h.children[j] = t;
        h.m++;
        if (h.m < M) {
        	return null;
        } else  {
        	return split(h);
        }
    }

    // 将节点一分为二
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++) {
        	t.children[j] = h.children[M/2+j]; 
        }
        return t;    
    }

    /**
     * 返回此B树的字符串表示形式(用于调试)。
     *
     * @return 这个B树的字符串表示
     */
    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " " + children[j].val + "\n");
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) {
                	s.append(indent + "(" + children[j].key + ")\n");
                }
                s.append(toString(children[j].next, ht-1, indent + "     "));
            }
        }
        return s.toString();
    }


    // 比较函数,判断k1是否小于k2
    @SuppressWarnings({"unchecked", "rawtypes"})
	private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    // 比较k1和k2是否相等
    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }


    /**
     * 测试BTree的功能。
     */
    public static void main(String[] args) {
        BTree<String, String> st = new BTree<String, String>();

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu",    "128.112.128.15");
        st.put("www.yale.edu",         "130.132.143.21");
        st.put("www.simpsons.com",     "209.052.165.60");
        st.put("www.apple.com",        "17.112.152.32");
        st.put("www.amazon.com",       "207.171.182.16");
        st.put("www.ebay.com",         "66.135.192.87");
        st.put("www.cnn.com",          "64.236.16.20");
        st.put("www.google.com",       "216.239.41.99");
        st.put("www.nytimes.com",      "199.239.136.200");
        st.put("www.microsoft.com",    "207.126.99.140");
        st.put("www.dell.com",         "143.166.224.230");
        st.put("www.slashdot.org",     "66.35.250.151");
        st.put("www.espn.com",         "199.181.135.201");
        st.put("www.weather.com",      "63.111.66.11");
        st.put("www.yahoo.com",        "216.109.118.65");


        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
        System.out.println("apple.com:         " + st.get("www.apple.com"));
        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
        System.out.println("dell.com:          " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
        System.out.println(st);
        System.out.println();
    }

}