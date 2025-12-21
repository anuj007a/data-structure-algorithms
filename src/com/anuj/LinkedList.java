package com.wraith;

public class LinkedList {

    Node head;

    public void insert(int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;
        if (head == null) {
            head = node;
        } else {
            Node n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = node;
        }
    }

    public void insertAtStart(int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;
        if (head != null) {
            node.next = head;
        }
        head = node;
    }

    public void insertAtAnyLocation(int index, int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;
        if (head == null) {
            head = node;
            return;
        }
        if (index == 0) {
            insertAtStart(data);
            return;
        }
        Node n = head;
        for (int i = 0; i < index - 1; i++) {
            n = n.next;
        }
        node.next = n.next;
        n.next = node;
    }
    public boolean delete(int index) {
        if (head == null) {
            return false;
        } else if (index == 0) {
            head = head.next;
            return true;
        } else {
            Node node = head;
            for (int i = 0; i < index - 1; i++) {
                node = node.next;
            }
            Node n1 = node.next;
            node.next = n1.next;
            n1 = null;
            return true;
        }
    }


    public int size() {
        int i = 0;
        if (head == null) {
            return i;
        }
        Node n = head;
        i++;
        while (n.next != null) {
            i++;
            n = n.next;
        }
        return i;
    }

    public void show() {
        Node node = head;
        while (node.next != null) {
            System.out.print(node.data + " , ");
            node = node.next;
        }
        System.out.println(node.data);

    }

}

class Node {
    int data;
    Node next;
}

class Runner {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.insert(10);
        linkedList.insert(12);
        linkedList.insert(13);
        linkedList.insertAtStart(9);
        linkedList.insertAtAnyLocation(0, 33);
        linkedList.show();
        System.out.println(linkedList.size());
        linkedList.delete(2);
        linkedList.show();
        System.out.println(linkedList.size());
    }
}
