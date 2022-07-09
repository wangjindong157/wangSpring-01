package com.wang.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interview001 {
    static class Category {
        /**
         * 分类id
         */
        Integer id;

        /**
         * 名称
         */
        String name;

        /**
         * 父级分类id
         */
        Integer parentId;

        static Category create(Integer id, String name, Integer parentId) {
            Category category = new Category();
            category.id = id;
            category.name = name;
            category.parentId = parentId;
            return category;
        }
    }

    static class TreeNode {
        /**
         * 节点数据
         */
        Category data;

        /**
         * 子节点列表
         */
        List<TreeNode> childNodes;

        static TreeNode create(Category data) {
            TreeNode node = new TreeNode();
            node.data = data;
            node.childNodes = new ArrayList<>();
            return node;
        }
    }

    public static void main(String[] args) {
        Interview001 interview001 = new Interview001();
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.create(0, "女装", null));
        categoryList.add(Category.create(1, "上装", 0));
        categoryList.add(Category.create(3, "衬衫", 1));
        categoryList.add(Category.create(4, "短袖衬衫", 3));
        categoryList.add(Category.create(5, "长袖衬衫", 3));
        categoryList.add(Category.create(9, "毛衣", 1));
        categoryList.add(Category.create(10, "外套", 1));
        categoryList.add(Category.create(11, "下装", 0));
        categoryList.add(Category.create(12, "裙子", 11));
        categoryList.add(Category.create(6, "T恤", 1));
        categoryList.add(Category.create(7, "长袖T", 6));
        categoryList.add(Category.create(8, "短袖T", 6));

        categoryList.add(Category.create(13, "短裙", 12));
        categoryList.add(Category.create(14, "长裙", 12));
        categoryList.add(Category.create(15, "裤子", 11));
        categoryList.add(Category.create(16, "长裤", 15));
        TreeNode root = interview001.buildTree(categoryList);
        interview001.printCategory(root);
    }

    /**
     * 按如下格式打印出分类树:
     * 女装
     *     上装
     *         衬衫
     *             短袖衬衫
     *             长袖衬衫
     *         T恤
     *             长袖T
     *             短袖T
     *         毛衣
     *         外套
     *     下装
     *         裙子
     *             短裙
     *             长裙
     *         裤子
     *             长裤
     */
    /**
     * 实现打印分类树逻辑
     * @param root
     */
    private void printCategory(TreeNode root) {
        System.out.println(root.data.name);
        for (TreeNode childNode : root.childNodes) {
            printDfs(childNode, 1);
        }
    }

    private void printDfs(TreeNode treeNode, int level) {
        for (int i = 0; i < level * 4; i++) {
            System.out.printf(" ");
        }
        System.out.println(treeNode.data.name);
        for (TreeNode childNode : treeNode.childNodes) {
            printDfs(childNode, level + 1);
        }
    }

    /**
     * 构建分类树
     * @param categoryList
     * @return
     */
    private TreeNode buildTree(List<Category> categoryList) {
        HashMap<Integer, List<TreeNode>> categoryHashMap = new HashMap<>();
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode treeNode = null;
        for (int i = 0; i < categoryList.size(); i++) {
            Category curCategory = categoryList.get(i);
            if (categoryList.get(i).parentId == null) {
                treeNode = TreeNode.create(curCategory);
                treeNodes.add(treeNode);
            } else {
                List<TreeNode> categories = categoryHashMap.get(curCategory.parentId);
                TreeNode curTreeNode = TreeNode.create(curCategory);
                treeNodes.add(curTreeNode);
                if (categories == null) {
                    categories = new ArrayList<>();
                    categories.add(curTreeNode);
                    categoryHashMap.put(curCategory.parentId, categories);
                } else {
                    categories.add(curTreeNode);
                    categoryHashMap.put(curCategory.parentId, categories);
                }
            }
        }

        for (int i = 0; i < treeNodes.size(); i++) {
            TreeNode curTreeNode = treeNodes.get(i);
            Integer id = curTreeNode.data.id;
            List<TreeNode> curTreeNodes = categoryHashMap.get(id);
            if (curTreeNodes != null) {
                curTreeNode.childNodes = curTreeNodes;
            }
        }
        return treeNode;
    }
}