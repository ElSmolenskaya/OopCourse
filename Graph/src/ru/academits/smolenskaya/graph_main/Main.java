package ru.academits.smolenskaya.graph_main;

import ru.academits.smolenskaya.graph.Graph;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(7);

        graph.setEdge(0, 1);
        graph.setEdge(1, 2);
        graph.setEdge(1, 3);
        graph.setEdge(1, 4);
        graph.setEdge(1, 5);
        graph.setEdge(2, 6);
        graph.setEdge(4, 5);
        graph.setEdge(5, 6);

        System.out.println("Обход графа в глубину:");
        Consumer<Integer> printNumber = number -> System.out.printf("%d, ", number);
        graph.depthTraverse(printNumber);

        System.out.println();

        System.out.println("Обход графа в ширину:");
        graph.widthTraverse(printNumber);
    }
}
