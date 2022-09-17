package ru.academits.smolenskaya.tree;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Tree<T> {
    private TreeNode<T> root;
    private int size;
    private Comparator<T> comparator;

    public Tree() {
    }

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private int compare(T data1, T data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data1 == null) {
            if (data2 == null) {
                return 0;
            }

            return -1;
        }

        if (data2 == null) {
            return 1;
        }

        //noinspection unchecked
        Comparable<T> comparableData = (Comparable<T>) data1;

        return comparableData.compareTo(data2);
    }

    public int size() {
        return size;
    }

    public void insert(T data) {
        if (root == null) {
            root = new TreeNode<>(data);

            ++size;

            return;
        }

        TreeNode<T> node = root;

        while (true) {
            if (compare(data, node.getData()) < 0) {
                if (node.getLeft() != null) {
                    node = node.getLeft();
                } else {
                    node.setLeft(new TreeNode<>(data));

                    ++size;

                    return;
                }
            } else {
                if (node.getRight() != null) {
                    node = node.getRight();
                } else {
                    node.setRight(new TreeNode<>(data));

                    ++size;

                    return;
                }
            }
        }
    }

    public boolean contains(T data) {
        return getNode(root, data) != null;
    }

    private TreeNode<T> getNode(TreeNode<T> root, T data) {
        if (root == null) {
            return null;
        }

        TreeNode<T> node = root;

        while (true) {
            int comparisonResult = compare(data, node.getData());

            if (comparisonResult == 0) {
                return node;
            }

            if (comparisonResult < 0) {
                if (node.getLeft() == null) {
                    return null;
                }

                node = node.getLeft();
            } else {
                if (node.getRight() == null) {
                    return null;
                }

                node = node.getRight();
            }
        }
    }

    private TreeNode<T> getNodeParent(T data) {
        TreeNode<T> nodeParent = null;
        TreeNode<T> node = root;

        while (true) {
            int comparingResult = compare(data, node.getData());

            if (comparingResult == 0) {
                return nodeParent;
            }

            nodeParent = node;

            if (comparingResult < 0) {
                node = nodeParent.getLeft();
            } else {
                node = nodeParent.getRight();
            }

            if (node == null) {
                return null;
            }
        }
    }

    private void replaceNode(TreeNode<T> replaceableNodeParent, TreeNode<T> replaceableNode, TreeNode<T> nodeReplacement) {
        if (replaceableNodeParent == null) {
            root = nodeReplacement;

            return;
        }

        if (replaceableNodeParent.getLeft() == replaceableNode) {
            replaceableNodeParent.setLeft(nodeReplacement);
        } else {
            replaceableNodeParent.setRight(nodeReplacement);
        }
    }

    public boolean delete(T data) {
        if (root == null) {
            return false;
        }

        TreeNode<T> deletingNodeParent = getNodeParent(data);
        TreeNode<T> deletingNode;

        if (deletingNodeParent == null) {
            if (compare(data, root.getData()) != 0) {
                return false;
            }

            deletingNode = root;
        } else {
            deletingNode = getNode(deletingNodeParent, data);
        }

        if (deletingNode.getLeft() == null && deletingNode.getRight() == null) {
            replaceNode(deletingNodeParent, deletingNode, null);

            --size;

            return true;
        }

        if (deletingNode.getLeft() == null) {
            replaceNode(deletingNodeParent, deletingNode, deletingNode.getRight());

            --size;

            return true;
        }

        if (deletingNode.getRight() == null) {
            replaceNode(deletingNodeParent, deletingNode, deletingNode.getLeft());

            --size;

            return true;
        }

        TreeNode<T> minimalNodeParent = deletingNode;
        TreeNode<T> minimalNode = deletingNode.getRight();

        while (minimalNode.getLeft() != null) {
            minimalNodeParent = minimalNode;
            minimalNode = minimalNode.getLeft();
        }

        replaceNode(minimalNodeParent, minimalNode, minimalNode.getRight());

        minimalNode.setLeft(deletingNode.getLeft());
        minimalNode.setRight(deletingNode.getRight());

        replaceNode(deletingNodeParent, deletingNode, minimalNode);

        --size;

        return true;
    }

    public void widthTraverse(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.remove();

            consumer.accept(node.getData());

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
    }

    public void depthTraverse(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<T>> stack = new LinkedList<>();
        stack.addLast(root);

        while (!stack.isEmpty()) {
            TreeNode<T> node = stack.removeLast();

            consumer.accept(node.getData());

            if (node.getRight() != null) {
                stack.addLast(node.getRight());
            }

            if (node.getLeft() != null) {
                stack.addLast(node.getLeft());
            }
        }
    }

    private void visitNode(TreeNode<T> node, Consumer<T> consumer) {
        consumer.accept(node.getData());

        if (node.getLeft() != null) {
            visitNode(node.getLeft(), consumer);
        }

        if (node.getRight() != null) {
            visitNode(node.getRight(), consumer);
        }
    }

    public void recursiveDepthTraverse(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        visitNode(root, consumer);
    }
}