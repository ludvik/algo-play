package home.ludvik.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyHashTable<K, V> {

    public class HashNode<K, V>{
        public K key;
        public V value;
        public HashNode next;
        public HashNode prev;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;

            next = null;
            prev = null;
        }
    }

    private final HashNode<K, V>[] nodeArray;

    public MyHashTable(int capacity) {
        this.nodeArray = (HashNode<K, V>[])new HashNode[capacity];
    }

    public Integer put(K key, V value){

        int pos = key.hashCode() % nodeArray.length;
        HashNode node = nodeArray[pos];

        // empty bucket, put k,v in.
        if(node.key == null){
            node.key = key;
            node.value = value;
            return pos;
        }

        while(node.next != null){
            node = node.next;
        }
        HashMap



    }

    public V get(K key){

    }
}
