package ru.academits.smolenskaya.graph_main;

import ru.academits.smolenskaya.graph.Graph;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        int size = 11;

        double[][] edges = new double[size][size];

        edges[0][1] = 1;
        edges[1][2] = 1;
        edges[1][3] = 1;
        edges[1][4] = 1;
        edges[1][5] = 1;
        edges[2][6] = 1;
        edges[4][5] = 1;

        Graph graph = new Graph(edges);

        graph.setEdge(5, 6, 1);

        System.out.println("Обход графа в глубину:");
        Consumer<Integer> printNumber = number -> System.out.printf("%d, ", number);
        graph.traverseInDepth(printNumber);

        System.out.println();

        System.out.println("Рекурсивный обход графа в глубину:");
        graph.recursiveTraverseInDepth(printNumber);

        System.out.println();

        System.out.println("Обход графа в ширину:");
        graph.traverseInWidth(printNumber);
    }
}
