package com.wraith.collection.framework.non.linear.data.structure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class NonLinearDataStructuresDemo {

    public static void main(String[] args) {
        NonLinearDataStructuresDemo demo = new NonLinearDataStructuresDemo();

        demo.treeConceptExample();
        demo.treeMapExample();
        demo.treeSetExample();
        demo.graphExample();

        demo.finalTipsForBeginners();
    }

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

    // 1) Tree concept (simple binary tree)
    private void treeConceptExample() {
        title("TREE (CONCEPT)");

        explain(
                "Hierarchical non-linear structure with parent-child nodes.",
                "Models hierarchical data naturally.",
                "File systems, org charts, category trees, BST-based search.",
                "Create nodes and connect left/right children (binary tree).",
                "Traversal usually O(n); search in balanced BST ~O(log n).",
                "Natural representation for hierarchy and recursive problems.",
                "Can degrade to O(n) in unbalanced trees.",
                "Confusing tree traversal orders (pre/in/post) at beginner stage."
        );

        Node root = new Node(10);
        root.left = new Node(5);
        root.right = new Node(20);
        root.left.left = new Node(3);
        root.left.right = new Node(7);

        List<Integer> inOrder = new ArrayList<>();
        inOrderTraversal(root, inOrder);

        System.out.println("Tree in-order traversal = " + inOrder);
        System.out.println("Tree height = " + height(root));
    }

    // 2) TreeMap
    private void treeMapExample() {
        title("TREEMAP");

        explain(
                "Sorted key-value map backed by Red-Black Tree.",
                "Maintains keys in sorted order automatically.",
                "Leaderboards, sorted configurations, range queries.",
                "Use put/get and navigation methods like firstKey/higherKey.",
                "put/get/remove O(log n), firstKey/lastKey O(log n).",
                "Sorted map with rich navigation APIs.",
                "Slower than HashMap for pure key lookup workloads.",
                "Keys must be comparable or a Comparator must be provided."
        );

        TreeMap<Integer, String> rankMap = new TreeMap<>();
        rankMap.put(3, "Charlie");
        rankMap.put(1, "Alice");
        rankMap.put(2, "Bob");

        System.out.println("TreeMap (sorted by key) = " + rankMap);
        System.out.println("firstKey = " + rankMap.firstKey());
        System.out.println("lastKey = " + rankMap.lastKey());
        System.out.println("higherKey(2) = " + rankMap.higherKey(2));
    }

    // 3) TreeSet
    private void treeSetExample() {
        title("TREESET");

        explain(
                "Sorted set backed by Red-Black Tree.",
                "Keeps unique elements in sorted order.",
                "Sorted unique IDs, ranking bands, ordered distinct values.",
                "Use add/remove/contains and navigation like first/tailSet.",
                "add/remove/contains O(log n).",
                "Unique + sorted collection with neat range operations.",
                "Slower than HashSet for simple membership checks.",
                "Duplicates are silently ignored; order is natural/comparator-based."
        );

        TreeSet<Integer> scoreSet = new TreeSet<>();
        scoreSet.add(30);
        scoreSet.add(10);
        scoreSet.add(20);
        scoreSet.add(10); // duplicate ignored

        System.out.println("TreeSet (sorted + unique) = " + scoreSet);
        System.out.println("first = " + scoreSet.first());
        System.out.println("last = " + scoreSet.last());
        System.out.println("tailSet(20) = " + scoreSet.tailSet(20));
    }

    // 4) Graph (Adjacency list + BFS + DFS)
    private void graphExample() {
        title("GRAPH");

        explain(
                "A set of vertices (nodes) connected by edges.",
                "Represents network-style relationships.",
                "Social network, maps/routes, recommendation engines, dependencies.",
                "Use adjacency list and traverse with BFS/DFS.",
                "Adjacency list traversal BFS/DFS: O(V + E).",
                "Very expressive for real-world relationships.",
                "Can be complex to reason about in cyclic graphs.",
                "Forgetting visited set in BFS/DFS can cause repeated traversal/loops."
        );

        // Undirected graph via adjacency list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        addUndirectedEdge(graph, 1, 2);
        addUndirectedEdge(graph, 1, 3);
        addUndirectedEdge(graph, 2, 4);
        addUndirectedEdge(graph, 3, 4);
        addUndirectedEdge(graph, 4, 5);

        System.out.println("Graph adjacency list = " + graph);
        System.out.println("BFS from 1 = " + bfs(graph, 1));

        List<Integer> dfsOrder = new ArrayList<>();
        dfs(1, graph, new HashSet<>(), dfsOrder);
        System.out.println("DFS from 1 = " + dfsOrder);
    }

    private void addUndirectedEdge(Map<Integer, List<Integer>> graph, int a, int b) {
        graph.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        graph.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
    }

    private List<Integer> bfs(Map<Integer, List<Integer>> graph, int start) {
        List<Integer> order = new ArrayList<>();
        if (!graph.containsKey(start)) {
            return order;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            order.add(node);

            for (int neighbor : graph.getOrDefault(node, List.of())) {
                if (visited.add(neighbor)) {
                    queue.offer(neighbor);
                }
            }
        }
        return order;
    }

    private void dfs(int node, Map<Integer, List<Integer>> graph, Set<Integer> visited, List<Integer> order) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);
        order.add(node);

        for (int neighbor : graph.getOrDefault(node, List.of())) {
            dfs(neighbor, graph, visited, order);
        }
    }

    private void inOrderTraversal(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, result);
        result.add(node.value);
        inOrderTraversal(node.right, result);
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private void finalTipsForBeginners() {
        title("BEGINNER CHOICE GUIDE (NON-LINEAR)");
        divider();
        System.out.println("1) Use TreeMap when you need sorted key-value pairs.");
        System.out.println("2) Use TreeSet when you need sorted unique values.");
        System.out.println("3) Use Graph when relationships are many-to-many.");
        System.out.println("4) Always track visited nodes for graph traversal.");
        System.out.println("5) Remember: HashMap/HashSet are usually faster than TreeMap/TreeSet for plain lookup.");
        divider();
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }
}