package com.anuj;

public class Queue {
    int[] queue = new int[5];
    int size;
    int front;
    int rear;

    public void enQueue(int data) {
        if (!isFull()) {
            queue[rear] = data;
            rear = (rear + 1) % 5;
            size++;
        } else {
            System.out.println("Queue is full.");
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public boolean isFull() {
        return getSize() == 5;
    }

    public int deQueue() {
        int data = queue[front];
        if (!isEmpty()) {
            front = (front + 1) % 5;
            size--;
        } else {
            System.out.println("Queue is empty.");
        }
        return data;
    }


    public void show() {
        System.out.print("Element are : ");
        for (int i = 0; i < size; i++) {
            System.out.print(queue[(front + i) % 5] + " ");
        }
        System.out.println();
    }


}

class queueRunner {
    public static void main(String[] args) {
        Queue queue = new Queue();
        System.out.println(queue.deQueue());
        System.out.println("Is queue empty : " + queue.isEmpty());
        System.out.println("Is queue full : " + queue.isFull());
        queue.enQueue(5);
        queue.enQueue(8);
        queue.enQueue(9);
        queue.enQueue(19);
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        queue.enQueue(13);
        queue.enQueue(44);
        queue.enQueue(43);
        queue.enQueue(46);
        queue.enQueue(47);
        queue.show();
        System.out.println("Size of queue is: " + queue.getSize());
        System.out.println("Is queue empty : " + queue.isEmpty());
        System.out.println("Is queue full : " + queue.isFull());


    }
}
