package ru.academits.smolenskaya.tree_main;

import ru.academits.smolenskaya.tree.Tree;

public class Main {
    public static void printIntegerArray(Object[] array) {
        for (Object element : array) {
            System.out.print((Integer) element + ", ");
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

        Object[] array1 = tree.getBreadthFirstTraverseResult();
        System.out.print("Обход дерева в ширину: ");
        printIntegerArray(array1);

        System.out.println();

        Object[] array2 = tree.getDepthTraverseResult();
        System.out.print("Обход дерева в глубину: ");
        printIntegerArray(array2);

        System.out.println();

        Object[] array3 = tree.getRecursiveDepthTraverseResult();
        System.out.print("Рекурсивный обход дерева в глубину: ");
        printIntegerArray(array3);


    }
}
