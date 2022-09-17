package ru.academits.smolenskaya.tree_main;

import ru.academits.smolenskaya.shapes.*;
import ru.academits.smolenskaya.tree.Tree;

import java.util.Comparator;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.insert(8);
        tree.insert(3);
        tree.insert(null);
        tree.insert(10);
        tree.insert(14);
        tree.insert(13);
        tree.insert(null);
        tree.insert(null);
        tree.insert(1);
        tree.insert(6);
        tree.insert(4);
        tree.insert(7);

        System.out.printf("Размер дерева: %d%n", tree.size());

        Consumer<Integer> printNumber = number -> System.out.printf("%d, ", number);

        System.out.print("Обход дерева в ширину: ");
        tree.widthTraverse(printNumber);

        System.out.println();

        System.out.print("Обход дерева в глубину: ");
        tree.depthTraverse(printNumber);

        System.out.println();

        System.out.print("Рекурсивный обход дерева в глубину: ");
        tree.recursiveDepthTraverse(printNumber);

        System.out.println();

        Integer data = 10;
        System.out.printf("Элемент %d принадлежит дереву: %s%n", data, tree.contains(data));

        data = null;

        System.out.printf("Элемент %d удален из дерева: %s%n", data, tree.delete(data));
        System.out.printf("Элемент %d удален из дерева: %s%n", data, tree.delete(data));
        System.out.printf("Элемент %d удален из дерева: %s%n", data, tree.delete(data));
        System.out.printf("Элемент %d удален из дерева: %s%n", data, tree.delete(data));

        data = 1;
        System.out.printf("Элемент %d удален из дерева: %s%n", data, tree.delete(data));

        System.out.print("Рекурсивный обход дерева в глубину: ");
        tree.recursiveDepthTraverse(printNumber);

        System.out.println();
        System.out.printf("Размер дерева: %d%n", tree.size());

        Tree<Integer> emptyTree = new Tree<>();
        System.out.printf("Размер пустого дерева: %d", emptyTree.size());

        System.out.println();

        Tree<Shape> shapesTree = new Tree<>(Comparator.comparingDouble(Shape::getArea));

        shapesTree.insert(new Circle(10));
        shapesTree.insert(new Rectangle(5, 5));
        shapesTree.insert(new Circle(12));
        shapesTree.insert(new Square(25));

        Consumer<Shape> printShape = shape -> System.out.printf("%s; ", shape);

        System.out.println();

        System.out.println("Обход дерева фигур в глубину: ");
        shapesTree.depthTraverse(printShape);
    }
}