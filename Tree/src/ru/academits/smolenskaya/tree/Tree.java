package ru.academits.smolenskaya.tree;

import java.util.Comparator;
import java.util.LinkedList;
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

    private int compare(T node1, T node2) {
        if (node1 == null) {
            if (node2 == null) {
                return 0;
            }

            return -1;
        }

        if (node2 == null) {
            return 1;
        }

        if (comparator != null) {
            return comparator.compare(node1, node2);
        }

        //noinspection unchecked
        Comparable<T> comparableNode = (Comparable<T>) node1;

        return comparableNode.compareTo(node2);
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

                    break;
                }
            } else {
                if (node.getRight() != null) {
                    node = node.getRight();
                } else {
                    node.setRight(new TreeNode<>(data));

                    ++size;

                    break;
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
        if (compare(data, root.getData()) == 0) {
            return null;
        }

        TreeNode<T> node = root;

        while (true) {
            if (compare(data, node.getData()) < 0) {
                if (node.getLeft() == null) {
                    return null;
                }

                if (compare(data, node.getLeft().getData()) == 0) {
                    return node;
                }

                node = node.getLeft();
            } else {
                if (node.getRight() == null) {
                    return null;
                }

                if (compare(data, node.getRight().getData()) == 0) {
                    return node;
                }

                node = node.getRight();
            }
        }
    }

    private void replaceDeletedNode(TreeNode<T> parentNode, TreeNode<T> node, TreeNode<T> newNode) {
        if (node == null) {
            return;
        }

        if (newNode != null && newNode.getLeft() == null && newNode.getRight() == null) {
            newNode.setLeft(node.getLeft());
            newNode.setRight(node.getRight());
        }

        if (parentNode == null) {
            root = newNode;

            return;
        }

        if (parentNode.getLeft() == node) {
            parentNode.setLeft(newNode);
        } else if (parentNode.getRight() == node) {
            parentNode.setRight(newNode);
        }
    }

    public boolean delete(T data) {
        if (root == null) {
            return false;
        }

        TreeNode<T> nodeParent = getNodeParent(data);
        TreeNode<T> node;

        if (nodeParent == null) {
            if (compare(data, root.getData()) != 0) {
                return false;
            }

            node = root;
        } else {
            node = getNode(nodeParent, data);
        }

        if (node.getLeft() == null) {
            if (node.getRight() == null) {
                replaceDeletedNode(nodeParent, node, null);

                --size;

                return true;
            }

            TreeNode<T> rightNode = node.getRight();
            node.setRight(null);

            replaceDeletedNode(nodeParent, node, rightNode);

            --size;

            return true;
        }

        if (node.getRight() == null) {
            TreeNode<T> leftNode = node.getLeft();
            node.setLeft(null);

            replaceDeletedNode(nodeParent, node, leftNode);

            --size;

            return true;
        }

        TreeNode<T> minimalNodeParent = node;
        TreeNode<T> minimalNode = node.getRight();

        while (minimalNode.getLeft() != null) {
            minimalNodeParent = minimalNode;
            minimalNode = minimalNode.getLeft();
        }

        TreeNode<T> rightNode = minimalNode.getRight();
        minimalNode.setRight(null);

        replaceDeletedNode(minimalNodeParent, minimalNode, rightNode);

        replaceDeletedNode(nodeParent, node, minimalNode);

        --size;

        return true;
    }

    public void widthTraverse(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        LinkedList<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (queue.size() > 0) {
            TreeNode<T> node = queue.remove(0);

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

        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.add(root);

        while (stack.size() > 0) {
            TreeNode<T> node = stack.remove(stack.size() - 1);

            consumer.accept(node.getData());

            if (node.getRight() != null) {
                stack.add(node.getRight());
            }

            if (node.getLeft() != null) {
                stack.add(node.getLeft());
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