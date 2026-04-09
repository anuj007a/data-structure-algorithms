package com.wraith.collection.framework.key.valye.data.structure;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class KeyValueDataStructuresDemo {

    public static void main(String[] args) {
        KeyValueDataStructuresDemo demo = new KeyValueDataStructuresDemo();

        demo.hashMapExample();
        demo.linkedHashMapExample();
        demo.treeMapExample();

        demo.finalTipsForBeginners();
    }

    // ─────────────────────────────────────────────────────────────
    // 1) HashMap
    // ─────────────────────────────────────────────────────────────
    private void hashMapExample() {
        title("HASHMAP");

        explain(
                "Hash-table backed Map; stores key-value pairs with no guaranteed order.",
                "O(1) average-case get/put; fastest general-purpose key-value lookup.",
                "Caches, frequency counters, lookup tables, session stores.",
                "Use put/get/remove/containsKey/getOrDefault/putIfAbsent/computeIfAbsent.",
                "put O(1) avg, get O(1) avg, remove O(1) avg, worst-case O(n) on hash collisions.",
                "Fastest key-value map; null keys and null values are allowed.",
                "No ordering guarantee; iteration order can change on resize.",
                "Not thread-safe; use ConcurrentHashMap in multi-threaded contexts."
        );

        Map<String, Integer> wordCount = new HashMap<>();

        // put & putIfAbsent
        wordCount.put("apple", 3);
        wordCount.put("banana", 5);
        wordCount.put("cherry", 2);
        wordCount.putIfAbsent("apple", 99);           // ignored – key already exists
        wordCount.putIfAbsent("date", 7);             // inserted – new key

        System.out.println("Map after puts          : " + wordCount);

        // get & getOrDefault
        System.out.println("get('apple')            : " + wordCount.get("apple"));
        System.out.println("get('unknown', 0)       : " + wordCount.getOrDefault("unknown", 0));

        // containsKey / containsValue
        System.out.println("containsKey('banana')   : " + wordCount.containsKey("banana"));
        System.out.println("containsValue(2)        : " + wordCount.containsValue(2));

        // computeIfAbsent – useful for grouping / accumulation
        Map<String, Integer> freq = new HashMap<>();
        String[] words = {"cat", "dog", "cat", "bird", "dog", "cat"};
        for (String w : words) {
            freq.merge(w, 1, Integer::sum);           // elegant frequency count
        }
        System.out.println("Frequency map           : " + freq);

        // computeIfAbsent – multi-value map pattern
        Map<String, java.util.List<String>> groups = new HashMap<>();
        groups.computeIfAbsent("fruits", k -> new java.util.ArrayList<>()).add("apple");
        groups.computeIfAbsent("fruits", k -> new java.util.ArrayList<>()).add("mango");
        groups.computeIfAbsent("veggies", k -> new java.util.ArrayList<>()).add("carrot");
        System.out.println("Groups                  : " + groups);

        // Iterating entries
        System.out.println("Iterating wordCount     :");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        // remove
        wordCount.remove("cherry");
        System.out.println("After remove('cherry')  : " + wordCount);

        // size & isEmpty
        System.out.println("size                    : " + wordCount.size());
        System.out.println("isEmpty                 : " + wordCount.isEmpty());

        // Null key and null value support
        Map<String, String> nullDemo = new HashMap<>();
        nullDemo.put(null, "nullKey");
        nullDemo.put("nullVal", null);
        System.out.println("Null key/value support  : " + nullDemo);
    }

    // ─────────────────────────────────────────────────────────────
    // 2) LinkedHashMap
    // ─────────────────────────────────────────────────────────────
    private void linkedHashMapExample() {
        title("LINKEDHASHMAP");

        explain(
                "Hash-table + doubly-linked list; maintains insertion order (or access order).",
                "O(1) average get/put like HashMap, but iteration is always in a predictable order.",
                "JSON-like ordered maps, LRU caches, maintaining insertion sequence.",
                "Same API as HashMap; pass accessOrder=true in constructor for LRU behaviour.",
                "put O(1) avg, get O(1) avg; slightly more memory than HashMap due to linked list.",
                "Predictable iteration order; simple LRU cache with removeEldestEntry override.",
                "Slightly higher memory overhead than HashMap.",
                "Null keys allowed (one); do not confuse insertion-order with sorted order."
        );

        // ── Insertion-order demo ──────────────────────────────────
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice", 90);
        scores.put("Bob", 85);
        scores.put("Charlie", 92);
        scores.put("Diana", 78);

        System.out.println("Insertion order         : " + scores);

        // Overwriting a key does NOT change its position
        scores.put("Bob", 95);
        System.out.println("After update Bob=95     : " + scores);

        // getOrDefault / remove
        System.out.println("get('Charlie')          : " + scores.get("Charlie"));
        scores.remove("Diana");
        System.out.println("After remove('Diana')   : " + scores);

        // ── Access-order (LRU) demo ───────────────────────────────
        // accessOrder=true means most-recently-accessed moves to the tail
        Map<String, Integer> accessOrdered = new LinkedHashMap<>(16, 0.75f, true);
        accessOrdered.put("X", 1);
        accessOrdered.put("Y", 2);
        accessOrdered.put("Z", 3);

        System.out.println("Access-order before get : " + accessOrdered);
        accessOrdered.get("X");                       // X accessed → moves to tail
        System.out.println("After get('X')          : " + accessOrdered);

        // ── Simple bounded LRU Cache ──────────────────────────────
        final int CAPACITY = 3;
        Map<Integer, String> lruCache = new LinkedHashMap<>(CAPACITY, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > CAPACITY;
            }
        };

        lruCache.put(1, "one");
        lruCache.put(2, "two");
        lruCache.put(3, "three");
        System.out.println("LRU cache (full)        : " + lruCache);

        lruCache.get(1);                              // 1 accessed → becomes most recent
        lruCache.put(4, "four");                      // evicts least-recently-used (2)
        System.out.println("LRU after get(1)+put(4) : " + lruCache);  // 2 evicted
    }

    // ─────────────────────────────────────────────────────────────
    // 3) TreeMap
    // ─────────────────────────────────────────────────────────────
    private void treeMapExample() {
        title("TREEMAP");

        explain(
                "Red-Black tree backed NavigableMap; keys always stored in sorted order.",
                "Need sorted keys or range/navigation queries on a map.",
                "Leaderboards, word dictionaries, range lookups, scheduling by time.",
                "Use put/get/remove + firstKey/lastKey/floorKey/ceilingKey/subMap/headMap/tailMap.",
                "put O(log n), get O(log n), remove O(log n).",
                "Sorted key iteration; rich navigation API (floor, ceiling, range views).",
                "Slower than HashMap; does NOT allow null keys.",
                "Keys must be Comparable or a Comparator must be supplied; null key → NPE."
        );

        // ── Natural (ascending) order ─────────────────────────────
        TreeMap<String, Integer> cityPopulation = new TreeMap<>();
        cityPopulation.put("Mumbai", 20_667_656);
        cityPopulation.put("Delhi", 32_941_309);
        cityPopulation.put("Bangalore", 13_193_000);
        cityPopulation.put("Chennai", 11_235_000);
        cityPopulation.put("Hyderabad", 10_268_000);

        System.out.println("Sorted by city name     : " + cityPopulation);

        // Navigation APIs
        System.out.println("firstKey()              : " + cityPopulation.firstKey());
        System.out.println("lastKey()               : " + cityPopulation.lastKey());
        System.out.println("floorKey('Delhi')       : " + cityPopulation.floorKey("Delhi"));
        System.out.println("ceilingKey('Chennai')   : " + cityPopulation.ceilingKey("Chennai"));
        System.out.println("lowerKey('Hyderabad')   : " + cityPopulation.lowerKey("Hyderabad"));
        System.out.println("higherKey('Bangalore')  : " + cityPopulation.higherKey("Bangalore"));

        // Range views
        System.out.println("headMap (< 'Delhi')     : " + cityPopulation.headMap("Delhi"));
        System.out.println("tailMap (>= 'Hyderabad'): " + cityPopulation.tailMap("Hyderabad"));
        System.out.println("subMap [Bangalore,Delhi): " + cityPopulation.subMap("Bangalore", "Delhi"));

        // firstEntry / lastEntry / pollFirstEntry / pollLastEntry
        System.out.println("firstEntry()            : " + cityPopulation.firstEntry());
        System.out.println("lastEntry()             : " + cityPopulation.lastEntry());

        // ── Reverse (descending) order via Comparator ─────────────
        TreeMap<String, Integer> descMap = new TreeMap<>(Collections.reverseOrder());
        descMap.put("Mumbai", 20_667_656);
        descMap.put("Delhi", 32_941_309);
        descMap.put("Bangalore", 13_193_000);

        System.out.println("Descending order        : " + descMap);

        // ── Integer key example: price buckets ────────────────────
        TreeMap<Integer, String> priceBucket = new TreeMap<>();
        priceBucket.put(100, "Budget");
        priceBucket.put(500, "Mid-range");
        priceBucket.put(1000, "Premium");
        priceBucket.put(5000, "Luxury");

        int queryPrice = 750;
        System.out.println("Price buckets           : " + priceBucket);
        System.out.println("Bucket for " + queryPrice + "  : " + priceBucket.floorEntry(queryPrice));
        System.out.println("Next bucket after " + queryPrice + ": " + priceBucket.ceilingEntry(queryPrice));
    }

    // ─────────────────────────────────────────────────────────────
    // Tips
    // ─────────────────────────────────────────────────────────────
    private void finalTipsForBeginners() {
        title("BEGINNER CHOICE GUIDE – KEY-VALUE MAPS");
        divider();
        System.out.println("1) Use HashMap      : fastest get/put when order doesn't matter.");
        System.out.println("2) Use LinkedHashMap: when you need insertion-order or an LRU cache.");
        System.out.println("3) Use TreeMap       : when keys must be sorted or range queries are needed.");
        System.out.println("4) Always program to the Map interface: Map<K,V> map = new HashMap<>();");
        System.out.println("5) Prefer getOrDefault / merge / computeIfAbsent over manual null checks.");
        System.out.println("6) HashMap & LinkedHashMap allow one null key; TreeMap does NOT.");
        System.out.println("7) For thread safety use ConcurrentHashMap (never synchronize a HashMap manually).");
        divider();
    }

    // ─────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────
    private void divider() {
        System.out.println("--------------------------------------------------");
    }

    private void title(String heading) {
        System.out.println("\n=== " + heading + " ===");
    }

    private void explain(String what, String why, String where, String how,
                         String complexity, String pros, String cons, String pitfalls) {
        divider();
        System.out.println("What       : " + what);
        System.out.println("Why        : " + why);
        System.out.println("Where      : " + where);
        System.out.println("How        : " + how);
        System.out.println("Complexity : " + complexity);
        System.out.println("Pros       : " + pros);
        System.out.println("Cons       : " + cons);
        System.out.println("Pitfalls   : " + pitfalls);
        divider();
    }
}

