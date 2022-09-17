package ru.academits.smolenskaya.graph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Graph {
    private final int[][] edges;

    public Graph(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size = " + size + ": size must be > 0");
        }

        edges = new int[size][size];
    }

    public void setEdge(int node1, int node2) {
        if (node1 < 0 || node1 >= edges.length) {
            throw new IllegalArgumentException("Node1 = " + node1 + ": node1 must be >= 0 and < " + edges.length);
        }

        if (node2 < 0 || node2 >= edges.length) {
            throw new IllegalArgumentException("Node2 = " + node2 + ": node2 must be >= 0 and < " + edges.length);
        }

        if (node1 != node2) {
            edges[node1][node2] = 1;
            edges[node2][node1] = 1;
        }
    }

    public void widthTraverse(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                Queue<Integer> queue = new LinkedList<>();

                queue.add(i);

                while (!queue.isEmpty()) {
                    int j = queue.remove();

                    if (!visited[j]) {
                        consumer.accept(j);

                        visited[j] = true;

                        for (int k = j + 1; k < edges.length; k++) {
                            if (edges[j][k] != 0 && !visited[k]) {
                                queue.add(k);
                            }
                        }
                    }
                }
            }
        }
    }

    public void depthTraverse(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                Deque<Integer> stack = new LinkedList<>();

                stack.addLast(i);

                while (!stack.isEmpty()) {
                    int j = stack.removeLast();

                    if (!visited[j]) {
                        consumer.accept(j);

                        visited[j] = true;

                        for (int k = j + 1; k < edges.length; k++) {
                            if (edges[j][k] != 0 && !visited[k]) {
                                stack.addLast(k);
                            }
                        }
                    }
                }
            }
        }
    }
}