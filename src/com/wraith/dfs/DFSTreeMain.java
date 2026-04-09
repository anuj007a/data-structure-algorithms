package com.wraith.dfs;

import java.util.Stack;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class DFSTreeMain {

    // 1️⃣ Preorder DFS (Root → Left → Right)
    static void preorder(TreeNode root) {
        if (root == null) return;

        System.out.print(root.val + " ");
        preorder(root.left);
        preorder(root.right);
    }

    static void preorderIterative(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }


    // 2️⃣ Inorder DFS (Left → Root → Right)
    static void inorder(TreeNode root) {
        if (root == null) return;

        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
;
    static void inorderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            System.out.print(current.val + " ");
            current = current.right;
        }
    }

    // 3️⃣ Postorder DFS (Left → Right → Root)
    static void postorder(TreeNode root) {
        if (root == null) return;

        postorder(root.left);
        postorder(root.right);
        System.out.print(root.val + " ");
    }

    static void postorderIterative(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().val + " ");
        }
    }

    public static void main(String[] args) {
        /*
              1
             / \
            2   3
               / \
              4   5
        */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        System.out.println("Preorder DFS:");
        preorder(root);   // 1 2 3 4 5
        System.out.println();
        System.out.println("Preorder DFS (Iterative):");
        preorderIterative(root);

        System.out.println("\n\nInorder DFS:");
        inorder(root);    // 2 1 4 3 5
        System.out.println();
        System.out.println("Inorder DFS (Iterative):");
        inorderIterative(root);

        System.out.println("\n\nPostorder DFS:");
        postorder(root);  // 2 4 5 3 1
        System.out.println();
        System.out.println("Postorder DFS (Iterative):");
        postorderIterative(root);
    }
}
