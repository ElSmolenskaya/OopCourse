package ru.academits.smolenskaya.graph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

@SuppressWarnings("ClassCanBeRecord")
public class Graph {
    private final double[][] edges;

    public Graph(double[][] edges) {
        if (edges.length == 0) {
            throw new IllegalArgumentException("Edges.length = " + edges.length + ": nodes count must be > 0");
        }

        if (edges[0].length == 0) {
            throw new IllegalArgumentException("Edges[0].length = " + edges[0].length + ": nodes count must be > 0");
        }

        this.edges = new double[edges.length][edges[0].length];

        for (int i = 0; i < edges.length; i++) {
            this.edges[i] = Arrays.copyOf(edges[i], edges[i].length);
        }
    }

    @SuppressWarnings("unused")
    public void setEdge(int nodeIndex1, int nodeIndex2, double edgeWeight) {
        if (nodeIndex1 < 0 || nodeIndex1 >= edges.length) {
            throw new IllegalArgumentException("NodeIndex1 = " + nodeIndex1 + ": nodeIndex1 must be >= 0 and < " + edges.length);
        }

        if (nodeIndex2 < 0 || nodeIndex2 >= edges.length) {
            throw new IllegalArgumentException("NodeIndex2 = " + nodeIndex2 + ": nodeIndex2 must be >= 0 and < " + edges.length);
        }

        if (nodeIndex1 != nodeIndex2) {
            edges[nodeIndex1][nodeIndex2] = edgeWeight;
        }
    }

    public void traverseInWidth(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                queue.add(i);

                while (!queue.isEmpty()) {
                    int nodeIndex1 = queue.remove();

                    if (!visited[nodeIndex1]) {
                        consumer.accept(nodeIndex1);

                        visited[nodeIndex1] = true;

                        for (int nodeIndex2 = 0; nodeIndex2 < edges.length; nodeIndex2++) {
                            if (edges[nodeIndex1][nodeIndex2] != 0 && !visited[nodeIndex2]) {
                                queue.add(nodeIndex2);
                            }
                        }
                    }
                }
            }
        }
    }

    public void traverseInDepth(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                stack.addLast(i);

                while (!stack.isEmpty()) {
                    int nodeIndex1 = stack.removeLast();

                    if (!visited[nodeIndex1]) {
                        consumer.accept(nodeIndex1);

                        visited[nodeIndex1] = true;

                        for (int nodeIndex2 = edges.length - 1; nodeIndex2 >= 0; nodeIndex2--) {
                            if (edges[nodeIndex1][nodeIndex2] != 0 && !visited[nodeIndex2]) {
                                stack.addLast(nodeIndex2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void visitNode(int nodeIndex, boolean[] visited, Consumer<Integer> consumer) {
        consumer.accept(nodeIndex);

        visited[nodeIndex] = true;

        for (int nextNodeIndex = 0; nextNodeIndex < edges.length; nextNodeIndex++) {
            if (edges[nodeIndex][nextNodeIndex] != 0 && !visited[nextNodeIndex]) {
                visitNode(nextNodeIndex, visited, consumer);
            }
        }
    }

    public void recursiveTraverseInDepth(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visitNode(i, visited, consumer);
            }
        }
    }
}