package com.wraith.collection.framework.set.data.structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetDataStructuresDemo {

    public static void main(String[] args) {
        SetDataStructuresDemo demo = new SetDataStructuresDemo();

        demo.hashSetExample();
        demo.linkedHashSetExample();
        demo.treeSetExample();

        demo.finalTipsForBeginners();
    }

    // ─────────────────────────────────────────────────────────────
    // 1) HashSet
    // ─────────────────────────────────────────────────────────────
    private void hashSetExample() {
        title("HASHSET");

        explain(
                "Hash-table backed Set; stores unique elements with no guaranteed order.",
                "O(1) average add/remove/contains; fastest general-purpose uniqueness check.",
                "Deduplication, visited-node tracking, membership tests, tag systems.",
                "Use add/remove/contains/size/isEmpty/iterator; Set operations via addAll/retainAll/removeAll.",
                "add O(1) avg, remove O(1) avg, contains O(1) avg, worst-case O(n) on hash collisions.",
                "Fastest Set; null element allowed; great for duplicate removal.",
                "No ordering guarantee; iteration order can change between JVM runs.",
                "Not thread-safe; override equals+hashCode in custom objects or contains/add won't work correctly."
        );

        Set<String> fruits = new HashSet<>();

        // add
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("apple");               // duplicate – silently ignored
        fruits.add("date");

        System.out.println("Set after adds          : " + fruits);
        System.out.println("Size (no duplicates)    : " + fruits.size());

        // contains / remove
        System.out.println("contains('banana')      : " + fruits.contains("banana"));
        System.out.println("contains('grape')       : " + fruits.contains("grape"));
        fruits.remove("cherry");
        System.out.println("After remove('cherry')  : " + fruits);

        // Null element support
        fruits.add(null);
        System.out.println("After add(null)         : " + fruits);
        fruits.remove(null);

        // isEmpty
        System.out.println("isEmpty                 : " + fruits.isEmpty());

        // ── Deduplication pattern ─────────────────────────────────
        String[] words = {"cat", "dog", "cat", "bird", "dog", "cat"};
        Set<String> unique = new HashSet<>(Arrays.asList(words));
        System.out.println("Deduplicated words      : " + unique);

        // ── Set operations ────────────────────────────────────────
        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(Arrays.asList(3, 4, 5, 6, 7));

        // Union
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("Union A ∪ B             : " + union);

        // Intersection
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("Intersection A ∩ B      : " + intersection);

        // Difference
        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("Difference A - B        : " + difference);

        // Iterating
        System.out.println("Iterating fruits        :");
        for (String fruit : fruits) {
            System.out.println("  " + fruit);
        }
    }

    // ─────────────────────────────────────────────────────────────
    // 2) LinkedHashSet
    // ─────────────────────────────────────────────────────────────
    private void linkedHashSetExample() {
        title("LINKEDHASHSET");

        explain(
                "Hash-table + doubly-linked list backed Set; maintains insertion order with uniqueness.",
                "O(1) average add/remove/contains like HashSet, but iteration is always in insertion order.",
                "Ordered deduplication, preserving insertion sequence, maintaining unique history.",
                "Same API as HashSet; just replace new HashSet<>() with new LinkedHashSet<>().",
                "add O(1) avg, remove O(1) avg, contains O(1) avg; slightly more memory than HashSet.",
                "Predictable insertion-order iteration; still O(1) for core operations.",
                "Slightly higher memory overhead than HashSet due to linked list.",
                "Null element allowed (one); do not confuse insertion-order with sorted order."
        );

        Set<String> history = new LinkedHashSet<>();
        history.add("homepage");
        history.add("products");
        history.add("cart");
        history.add("products");           // duplicate – silently ignored, position unchanged
        history.add("checkout");

        System.out.println("Insertion order kept    : " + history);
        System.out.println("Size (no duplicates)    : " + history.size());

        // contains / remove
        System.out.println("contains('cart')        : " + history.contains("cart"));
        history.remove("products");
        System.out.println("After remove('products'): " + history);

        // Re-inserting a removed element appends to the tail
        history.add("products");
        System.out.println("After re-add('products'): " + history);

        // ── Ordered deduplication pattern ─────────────────────────
        String[] events = {"login", "view", "login", "purchase", "view", "logout"};
        Set<String> orderedUnique = new LinkedHashSet<>(Arrays.asList(events));
        System.out.println("Ordered unique events   : " + orderedUnique);

        // ── Iterator usage ────────────────────────────────────────
        System.out.println("Iterator over history   :");
        Iterator<String> it = history.iterator();
        while (it.hasNext()) {
            System.out.println("  " + it.next());
        }

        // ── Convert back to list to preserve order ────────────────
        java.util.List<String> orderedList = new java.util.ArrayList<>(history);
        System.out.println("Converted to List       : " + orderedList);
    }

    // ─────────────────────────────────────────────────────────────
    // 3) TreeSet
    // ─────────────────────────────────────────────────────────────
    private void treeSetExample() {
        title("TREESET");

        explain(
                "Red-Black tree backed NavigableSet; elements always stored in sorted order.",
                "Need sorted unique elements or range/navigation queries on a set.",
                "Leaderboards, auto-complete, range queries, scheduling tokens.",
                "Use add/remove/contains + first/last/floor/ceiling/subSet/headSet/tailSet.",
                "add O(log n), remove O(log n), contains O(log n).",
                "Sorted iteration; rich navigation API (floor, ceiling, range views); unique elements.",
                "Slower than HashSet; does NOT allow null elements.",
                "Elements must be Comparable or a Comparator must be supplied; null element → NPE."
        );

        // ── Natural (ascending) order ─────────────────────────────
        TreeSet<String> cities = new TreeSet<>();
        cities.add("Mumbai");
        cities.add("Delhi");
        cities.add("Bangalore");
        cities.add("Chennai");
        cities.add("Hyderabad");
        cities.add("Delhi");              // duplicate – silently ignored

        System.out.println("Sorted cities           : " + cities);
        System.out.println("Size (no duplicates)    : " + cities.size());

        // Navigation APIs
        System.out.println("first()                 : " + cities.first());
        System.out.println("last()                  : " + cities.last());
        System.out.println("floor('Delhi')          : " + cities.floor("Delhi"));
        System.out.println("ceiling('Chennai')      : " + cities.ceiling("Chennai"));
        System.out.println("lower('Hyderabad')      : " + cities.lower("Hyderabad"));
        System.out.println("higher('Bangalore')     : " + cities.higher("Bangalore"));

        // Range views
        System.out.println("headSet (< 'Delhi')     : " + cities.headSet("Delhi"));
        System.out.println("tailSet (>= 'Hyderabad'): " + cities.tailSet("Hyderabad"));
        System.out.println("subSet [Bangalore,Delhi): " + cities.subSet("Bangalore", "Delhi"));

        // pollFirst / pollLast – retrieve and remove
        TreeSet<String> pollDemo = new TreeSet<>(cities);
        System.out.println("pollFirst()             : " + pollDemo.pollFirst());
        System.out.println("pollLast()              : " + pollDemo.pollLast());
        System.out.println("After polls             : " + pollDemo);

        // ── Reverse (descending) order via Comparator ─────────────
        TreeSet<String> descSet = new TreeSet<>(Collections.reverseOrder());
        descSet.addAll(Arrays.asList("Mumbai", "Delhi", "Bangalore"));
        System.out.println("Descending order        : " + descSet);

        // ── Integer example: unique sorted scores ─────────────────
        TreeSet<Integer> scores = new TreeSet<>();
        int[] rawScores = {85, 92, 78, 92, 65, 85, 100};
        for (int s : rawScores) scores.add(s);

        System.out.println("Unique sorted scores    : " + scores);
        System.out.println("Top score               : " + scores.last());
        System.out.println("Scores >= 85            : " + scores.tailSet(85));
        System.out.println("Scores in [78, 92)      : " + scores.subSet(78, 92));

        // ── Custom Comparator: case-insensitive alphabetical ──────
        TreeSet<String> caseInsensitive = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        caseInsensitive.add("Zebra");
        caseInsensitive.add("apple");
        caseInsensitive.add("MANGO");
        caseInsensitive.add("Apple");    // duplicate under CASE_INSENSITIVE_ORDER – ignored
        System.out.println("Case-insensitive set    : " + caseInsensitive);
    }

    // ─────────────────────────────────────────────────────────────
    // Tips
    // ─────────────────────────────────────────────────────────────
    private void finalTipsForBeginners() {
        title("BEGINNER CHOICE GUIDE – SETS");
        divider();
        System.out.println("1) Use HashSet       : fastest add/contains/remove when order doesn't matter.");
        System.out.println("2) Use LinkedHashSet : when you need insertion-order preserved uniqueness.");
        System.out.println("3) Use TreeSet       : when elements must be sorted or range queries are needed.");
        System.out.println("4) Always program to the Set interface: Set<E> set = new HashSet<>();");
        System.out.println("5) Override equals() + hashCode() in custom objects for correct HashSet/LinkedHashSet behaviour.");
        System.out.println("6) HashSet & LinkedHashSet allow one null; TreeSet does NOT allow null.");
        System.out.println("7) For thread safety use Collections.synchronizedSet() or CopyOnWriteArraySet.");
        System.out.println("8) Use Set operations (addAll/retainAll/removeAll) for union/intersection/difference.");
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

