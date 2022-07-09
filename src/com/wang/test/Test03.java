package com.wang.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ThreadPoolExecutor;

public class Test03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strs = str.split(" ");
        String zhong = strs[1], hou = strs[0];

        String rootVal = hou.substring(hou.length() - 1);
        int k = zhong.lastIndexOf(rootVal);
        String zhongL = zhong.substring(0, k);
        String zhongR = zhong.substring(k + 1);
        String houL = hou.substring(0, zhongL.length());
        String houR = hou.substring(zhongL.length(), hou.length() - 1);

        Node root = new Node();
        root.value = rootVal;
        root.left = null;
        root.right = null;

        updateLeft(root, zhongL, houL);
        updateRight(root, zhongR, houR);

        Queue<Node> queue = new LinkedList<>();
        System.out.print(root.value);
        if (root.left != null) {
            queue.add(root.left);
        }
        if (root.right != null) {
            queue.add(root.right);
        }
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    private static void updateLeft(Node root, String zhong, String hou) {
        if (zhong.length() == 0) {
            return;
        } else if (zhong.length() == 1) {
            Node leftNode = new Node();
            leftNode.value = zhong;
            root.left = leftNode;
            return;
        } else {
            String leftVal = hou.substring(hou.length() - 1);
            int k = zhong.lastIndexOf(leftVal);
            String zhongL = zhong.substring(0, k);
            String zhongR = zhong.substring(k + 1);
            String houL = hou.substring(0, zhongL.length());
            String houR = hou.substring(zhongL.length(), hou.length() - 1);

            Node leftNode = new Node();
            leftNode.value = leftVal;
            root.left = leftNode;

            updateLeft(leftNode, zhongL, houL);
            updateRight(leftNode, zhongR, houR);
        }
    }

    private static void updateRight(Node root, String zhong, String hou) {
        if (zhong.length() == 0) {
            return;
        } else if (zhong.length() == 1) {
            Node rightNode = new Node();
            rightNode.value = zhong;
            root.right = rightNode;
            return;
        } else {
            String rightVal = hou.substring(hou.length() - 1);
            int k = zhong.lastIndexOf(rightVal);
            String zhongL = zhong.substring(0, k);
            String zhongR = zhong.substring(k + 1);
            String houL = hou.substring(0, zhongL.length());
            String houR = hou.substring(zhongL.length(), hou.length() - 1);

            Node rightNode = new Node();
            rightNode.value = rightVal;
            root.right = rightNode;

            updateLeft(rightNode, zhongL, houL);
            updateRight(rightNode, zhongR, houR);
        }
    }

    public static class Node {
        public String value;
        public Node left;
        public Node right;
    }
}
