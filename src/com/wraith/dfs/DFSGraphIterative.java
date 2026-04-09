package com.wraith.dfs;

import java.util.*;

public class DFSGraphIterative {

    static void dfsIterative(int start, List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visited[node]) continue;
            visited[node] = true;
            System.out.print(node + " ");
            // Push neighbors in reverse order to match recursive DFS
            List<Integer> neighbors = graph.get(node);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                int nei = neighbors.get(i);
                if (!visited[nei]) {
                    stack.push(nei);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(List.of(1, 2)); // 0
        graph.add(List.of(3));    // 1
        graph.add(List.of(4));    // 2
        graph.add(List.of());     // 3
        graph.add(List.of());     // 4

        System.out.println("DFS (Iterative):");
        dfsIterative(0, graph);
    }
}
