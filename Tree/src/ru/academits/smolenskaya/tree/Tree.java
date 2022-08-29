package ru.academits.smolenskaya.tree;

import java.util.ArrayList;

public class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;
    private int size;

    public Tree(T data) {
        this.root = new TreeNode<>(data);

        ++size;
    }

    public int getSize() {
        return size;
    }



    public void insert(T data) {
        if (root == null) {
            root = new TreeNode<>(data);

            ++size;

            return;
        }

        TreeNode<T> currentNode = root;

        while (true) {
            if (data.equals(currentNode.getData())) {
                throw new IllegalArgumentException("data = " + data + " : the same node have already exists in the tree");
            }

            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(new TreeNode<>(data));

                    ++size;

                    break;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(new TreeNode<>(data));

                    ++size;

                    break;
                }
            }
        }
    }

    public Object[] getBreadthFirstTraverseResult() {
        if (root == null) {
            return null;
        }

        Object[] resultArray = new Object[size];

        ArrayList<TreeNode<T>> list = new ArrayList<>();
        list.add(root);

        int i = 0;

        while (list.size() > 0) {
            TreeNode<T> node = list.remove(0);

            resultArray[i] = node.getData();

            ++i;

            if (node.getLeft() != null) {
                list.add(node.getLeft());
            }

            if (node.getRight() != null) {
                list.add(node.getRight());
            }
        }

        return resultArray;
    }

    public Object[] getDepthTraverseResult() {
        if (root == null) {
            return null;
        }

        Object[] resultArray = new Object[size];

        ArrayList<TreeNode<T>> stack = new ArrayList<>();
        stack.add(root);

        int i = 0;

        while (stack.size() > 0) {
            TreeNode<T> node = stack.remove(stack.size() - 1);

            resultArray[i] = node.getData();

            ++i;

            if (node.getRight() != null) {
                stack.add(node.getRight());
            }

            if (node.getLeft() != null) {
                stack.add(node.getLeft());
            }
        }

        return resultArray;
    }

    private int visit(TreeNode<T> node, Object[] array, int i) {
        array[i] = node.getData();

        ++i;

        if (node.getLeft() != null) {
            i = visit(node.getLeft(), array, i);
        }

        if (node.getRight() != null) {
            i = visit(node.getRight(), array, i);
        }

        return i;
    }

    public Object[] getRecursiveDepthTraverseResult() {
        if (root == null) {
            return null;
        }

        Object[] resultArray = new Object[size];

        visit(root, resultArray, 0);

        return resultArray;
    }
}
