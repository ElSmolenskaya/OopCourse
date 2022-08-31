package ru.academits.smolenskaya.tree;

import java.util.ArrayList;

public class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;
    private int size;

    public Tree() {
        root = null;
    }

    public Tree(T data) {
        checkData(data);

        root = new TreeNode<>(data);

        ++size;
    }

    private void checkData(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data = null: data must be different from null");
        }
    }

    public int getSize() {
        return size;
    }

    public void insert(T data) {
        checkData(data);

        if (root == null) {
            root = new TreeNode<>(data);

            ++size;

            return;
        }

        TreeNode<T> node = root;

        while (true) {
            if (data.equals(node.getData())) {
                throw new IllegalArgumentException("Data = " + data + " : the same node have already exists in the tree");
            }

            if (data.compareTo(node.getData()) < 0) {
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

    public boolean isExists(T data) {
        checkData(data);

        return getNode(root, data) != null;
    }

    private TreeNode<T> getNode(TreeNode<T> root, T data) {
        if (root == null) {
            return null;
        }

        TreeNode<T> node = root;

        while (true) {
            if (data.compareTo(node.getData()) == 0) {
                return node;
            }

            if (data.compareTo(node.getData()) < 0) {
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
        if (root == null || data == null || data.compareTo(root.getData()) == 0) {
            return null;
        }

        TreeNode<T> node = root;

        while (true) {
            if (data.compareTo(node.getData()) < 0) {
                if (node.getLeft() == null) {
                    return null;
                }

                if (data.compareTo(node.getLeft().getData()) == 0) {
                    return node;
                }

                node = node.getLeft();
            } else {
                if (node.getRight() == null) {
                    return null;
                }

                if (data.compareTo(node.getRight().getData()) == 0) {
                    return node;
                }

                node = node.getRight();
            }
        }
    }

    private void setNode(TreeNode<T> parentNode, TreeNode<T> node, TreeNode<T> newNode) {
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
        checkData(data);

        if (root == null) {
            return false;
        }

        TreeNode<T> nodeParent = getNodeParent(data);
        TreeNode<T> node;

        if (nodeParent == null) {
            node = root;
        } else {
            node = getNode(nodeParent, data);
        }

        if (node.getLeft() == null) {
            if (node.getRight() == null) {
                setNode(nodeParent, node, null);

                node.clear();

                --size;

                return true;
            }

            TreeNode<T> newNode = node.getRight();
            node.setRight(null);

            setNode(nodeParent, node, newNode);

            node.clear();

            --size;

            return true;
        }

        if (node.getRight() == null) {
            TreeNode<T> newNode = node.getLeft();
            node.setLeft(null);

            setNode(nodeParent, node, newNode);

            node.clear();

            --size;

            return true;
        }

        TreeNode<T> minimalNodeParent = node;
        TreeNode<T> minimalNode = node.getRight();

        while (minimalNode.getLeft() != null) {
            minimalNodeParent = minimalNode;
            minimalNode = minimalNode.getLeft();
        }

        TreeNode<T> newNode = minimalNode.getRight();
        minimalNode.setRight(null);

        setNode(minimalNodeParent, minimalNode, newNode);

        setNode(nodeParent, node, minimalNode);

        node.clear();

        --size;

        return true;
    }

    public Object[] getBreadthFirstTraverseArray() {
        if (root == null) {
            return null;
        }

        Object[] resultArray = new Object[size];

        ArrayList<TreeNode<T>> queue = new ArrayList<>();
        queue.add(root);

        int i = 0;

        while (queue.size() > 0) {
            TreeNode<T> node = queue.remove(0);

            resultArray[i] = node.getData();

            ++i;

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }

        return resultArray;
    }

    public Object[] getDepthTraverseArray() {
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

    private int getLastArrayIndex(TreeNode<T> node, Object[] array, int i) {
        array[i] = node.getData();

        ++i;

        if (node.getLeft() != null) {
            i = getLastArrayIndex(node.getLeft(), array, i);
        }

        if (node.getRight() != null) {
            i = getLastArrayIndex(node.getRight(), array, i);
        }

        return i;
    }

    public Object[] getRecursiveDepthTraverseArray() {
        if (root == null) {
            return null;
        }

        Object[] resultArray = new Object[size];

        getLastArrayIndex(root, resultArray, 0);

        return resultArray;
    }
}