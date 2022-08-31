package ru.academits.smolenskaya.tree_main;

import ru.academits.smolenskaya.tree.Tree;

public class Main {
    public static void printArray(Object[] array) {
        for (Object element : array) {
            System.out.print(element + ", ");
        }
    }

    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>(8);
        tree.insert(3);
        tree.insert(10);
        tree.insert(14);
        tree.insert(13);
        tree.insert(1);
        tree.insert(6);
        tree.insert(4);
        tree.insert(7);

        System.out.printf("Размер дерева: %d%n", tree.getSize());

        Object[] array1 = tree.getBreadthFirstTraverseArray();
        System.out.print("Обход дерева в ширину: ");
        printArray(array1);

        System.out.println();

        Object[] array2 = tree.getDepthTraverseArray();
        System.out.print("Обход дерева в глубину: ");
        printArray(array2);

        System.out.println();

        Object[] array3 = tree.getRecursiveDepthTraverseArray();
        System.out.print("Рекурсивный обход дерева в глубину: ");
        printArray(array3);

        System.out.println();

        Integer data = 10;
        System.out.printf("Элемент %d принадлежит дереву: %s%n", data, tree.isExists(data));

        data = 8;
        System.out.printf("Элемент %d удален из дерева: %s%n", data, tree.delete(8));

        System.out.print("Рекурсивный обход дерева в глубину: ");
        Object[] array4 = tree.getRecursiveDepthTraverseArray();
        printArray(array4);

        System.out.println();
        System.out.printf("Размер дерева: %d%n", tree.getSize());

        Tree<Integer> emptyTree = new Tree<>();
        System.out.printf("Размер пустого дерева: %d", emptyTree.getSize());
    }
}