package ru.academits.smolenskaya.graph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Graph {
    private final double[][] edges;

    public Graph(double[][] edges) {
        if (edges.length == 0) {
            throw new IllegalArgumentException("Edges.length = " + edges.length + ": nodes count must be > 0");
        }

        for (int i = 0; i < edges.length; i++) {
            if (edges[i].length != edges.length) {
                throw new IllegalArgumentException("Edges[" + i + "].length = " + edges[i].length + " is not equal edges.length = " +
                        edges.length + ": matrix must be square and all rows must be of the same length");
            }
        }

        this.edges = new double[edges.length][edges[0].length];

        for (int i = 0; i < edges.length; i++) {
            this.edges[i] = Arrays.copyOf(edges[i], edges[i].length);
        }
    }

    @SuppressWarnings("unused")
    public Graph(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size = " + size + ": size must be > 0");
        }

        edges = new double[size][size];
    }

    @SuppressWarnings("unused")
    public void setEdge(int node1Index, int node2Index, double edgeWeight) {
        if (node1Index < 0 || node1Index >= edges.length) {
            throw new IndexOutOfBoundsException("Node1Index = " + node1Index + ": node1Index must be >= 0 and < " + edges.length);
        }

        if (node2Index < 0 || node2Index >= edges.length) {
            throw new IndexOutOfBoundsException("Node2Index = " + node2Index + ": node2Index must be >= 0 and < " + edges.length);
        }

        if (node1Index != node2Index) {
            edges[node1Index][node2Index] = edgeWeight;
        }
    }

    public void traverseInWidth(Consumer<Integer> consumer) {
        boolean[] visited = new boolean[edges.length];

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                queue.add(i);

                visited[i] = true;

                while (!queue.isEmpty()) {
                    int node1Index = queue.remove();

                    consumer.accept(node1Index);

                    for (int node2Index = 0; node2Index < edges.length; node2Index++) {
                        if (edges[node1Index][node2Index] != 0 && !visited[node2Index]) {
                            queue.add(node2Index);

                            visited[node2Index] = true;
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

                visited[i] = true;

                while (!stack.isEmpty()) {
                    int node1Index = stack.removeLast();

                    consumer.accept(node1Index);

                    for (int node2Index = edges.length - 1; node2Index >= 0; node2Index--) {
                        if (edges[node1Index][node2Index] != 0 && !visited[node2Index]) {
                            stack.addLast(node2Index);

                            visited[node2Index] = true;
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