package com.wraith.dfs;

import java.util.*;

public class DFSGraph {

    static void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }

    public static void main(String[] args) {
        // Build graph
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(List.of(1, 2)); // 0
        graph.add(List.of(3));    // 1
        graph.add(List.of(4));    // 2
        graph.add(List.of());     // 3
        graph.add(List.of());     // 4

        boolean[] visited = new boolean[graph.size()];

        System.out.println("DFS (Recursive):");
        dfs(0, graph, visited);
    }
}
