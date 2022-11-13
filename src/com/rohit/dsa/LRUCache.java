package com.rohit.dsa;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    Map<Integer, Node> cache;
    int capacity;
    Node left;
    Node right;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap();
        left = new Node(0,0);
        right = new Node(0,0);

        this.left.next = this.right;
        this.right.prev = this.left;
    }

    public int get(int key) {
        if (cache.containsKey(key)) { // If exists, remove and insert it in the linked list
            Node node = cache.get(key);
            remove(node);
            insert(node);
            return node.val;
        }
        else {
            return -1;
        }
    }

    private void insert(Node node) {
        Node prev = this.right.prev;
        Node next = this.right;

        prev.next = node;
        next.prev = node;

        node.next = next;
        node.prev = prev;

    }

    private void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) { // If exists, remove it from the linked list
            remove(cache.get(key));
        }
        cache.put(key, new Node(key, val));
        insert(cache.get(key));

        if (cache.size() > capacity) { // if the size of cache exceeds the capacity, remove least recently used value from linked list and cache
            Node lru = this.left.next;
            remove(lru);
            cache.remove(lru.key);
        }
    }

    class Node {
        int key;
        int val;

        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}



/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */