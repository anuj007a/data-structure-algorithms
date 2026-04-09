package com.wraith.bfs;

import java.util.*;

public class BFSGraph {

    public static void bfs(int start, List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
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

        bfs(0, graph);
    }
}
