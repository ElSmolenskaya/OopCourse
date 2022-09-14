package ru.academits.smolenskaya.graph;

import java.util.LinkedList;
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

    @SuppressWarnings("unused")
    public void removeEdge(int node1, int node2) {
        if (node1 < 0 || node1 >= edges.length) {
            throw new IllegalArgumentException("Node1 = " + node1 + ": node1 must be >= 0 and < " + edges.length);
        }

        if (node2 < 0 || node2 >= edges.length) {
            throw new IllegalArgumentException("Node2 = " + node2 + ": node2 must be >= 0 and < " + edges.length);
        }

        edges[node1][node2] = 0;
        edges[node2][node1] = 0;
    }

    public void widthTraverse(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        for (int unvisitedNode = 0; unvisitedNode < visited.length; unvisitedNode++) {
            if (!visited[unvisitedNode]) {
                LinkedList<Integer> queue = new LinkedList<>();
                queue.add(unvisitedNode);

                while (queue.size() > 0) {
                    Integer node = queue.remove(0);

                    if (!visited[node]) {
                        consumer.accept(node);

                        visited[node] = true;

                        for (int relatedNode = node + 1; relatedNode < edges.length; relatedNode++) {
                            if (edges[node][relatedNode] != 0 && !visited[relatedNode]) {
                                queue.add(relatedNode);
                            }
                        }
                    }
                }
            }
        }
    }

    public void depthTraverse(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        for (int unvisitedNode = 0; unvisitedNode < visited.length; unvisitedNode++) {
            if (!visited[unvisitedNode]) {
                LinkedList<Integer> stack = new LinkedList<>();

                stack.add(unvisitedNode);

                while (stack.size() > 0) {
                    Integer node = stack.remove(stack.size() - 1);

                    if (!visited[node]) {
                        consumer.accept(node);

                        visited[node] = true;

                        for (int relatedNode = node + 1; relatedNode < edges.length; relatedNode++) {
                            if (edges[node][relatedNode] != 0 && !visited[relatedNode]) {
                                stack.add(relatedNode);
                            }
                        }
                    }
                }
            }
        }
    }
}