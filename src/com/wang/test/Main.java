package com.wang.test;

import netscape.security.UserTarget;

import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("干嘛");
        Scanner scanner = new Scanner(System.in);

        int rootVal = scanner.nextInt();

        Node root = new Node();
        root.value = rootVal;

        Queue<Node> creator = new LinkedList<>();
        creator.add(root);
        while (!creator.isEmpty()) {
            Node node = creator.poll();
            System.out.println("请输入" + node.value + "的结点列表以-1结束");
            int curVal = scanner.nextInt();
            ArrayList<Node> subNodes = new ArrayList<>();
            while (curVal != -1) {
                Node subNode = new Node();
                subNode.value = curVal;
                subNode.subNodes = null;
                subNodes.add(subNode);
                creator.add(subNode);
                curVal = scanner.nextInt();
            }
            node.subNodes = subNodes;
        }

        //       1
        //    2     3
        //   6  7  4   5
        //1 2 3 6 7 4 5
        //1 2 6 7 3 4 5

        // 构建数据


        bfs(root);
        System.out.println();
        dfs(root);
    }

    private static void bfs(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            print(node.value);
            ArrayList<Node> subNodes = node.subNodes;
            if (subNodes != null) {
                for (Node subNode : subNodes) {
                    queue.add(subNode);
                }
            }
        }
    }

    private static void dfs(Node root) {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            print(node.value);
            ArrayList<Node> subNodes = node.subNodes;
            if (subNodes != null) {
                for (int i = subNodes.size() - 1; i >= 0 ; i--) {
                    stack.push(subNodes.get(i));
                }
            }
        }
    }

    private static void print(int value) {
        System.out.print(value);
    }

    static public class Node {
        public int value;
        public ArrayList<Node> subNodes;
    }
}
