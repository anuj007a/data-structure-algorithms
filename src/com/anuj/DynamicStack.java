package com.anuj;

public class DynamicStack {

    int capacity = 2;
    int stack[] = new int[capacity];
    int top = 0;

    public void push(int data) {
        if (size() == capacity) {
            expand();
        }
        stack[top] = data;
        top++;
    }

    private void expand() {
        int length = size();
        int newStack[] = new int[capacity * 2];
        System.arraycopy(stack, 0, newStack, 0, length);
        stack = newStack;
        capacity *= 2;
    }

    public int pop() {
        int data = 0;
        if (isEmpty()) {
            System.out.println("Can't pop, com.anuj.Stack is empty.");
        } else {
            top--;
            data = stack[top];
            stack[top] = 0;
            shrink();
        }
        return data;

    }

    private void shrink() {
        int length = size();
        if (length <= (capacity / 2) / 2) ;
        capacity = capacity / 2;
        int newStack[] = new int[capacity];
        System.arraycopy(stack, 0, newStack, 0, length);
        stack = newStack;
    }

    public int peek() {
        int data;
        data = stack[top - 1];
        return data;
    }

    public int size() {
        return top;
    }

    public boolean isEmpty() {
        return top <= 0;
    }

    public void show() {
        System.out.print("Value in stack are: ");
        for (int n : stack) {
            System.out.print(n + " , ");
        }
        System.out.println();

    }

}

class DynamicStackRunner {
    public static void main(String[] args) {
        DynamicStack dynamicStack = new DynamicStack();
        System.out.println(dynamicStack.isEmpty() == true ? "com.anuj.Stack is empty." : "com.anuj.Stack is not empty.");
        dynamicStack.pop();
        dynamicStack.push(10);
        dynamicStack.push(9);
        dynamicStack.push(8);
        dynamicStack.push(7);
        dynamicStack.push(6);
        dynamicStack.push(5);
        System.out.println("Size of stack is: " + dynamicStack.size());
        System.out.println("peek value is: " + dynamicStack.peek());
        dynamicStack.show();
        System.out.println("Value pop from stack is: " + dynamicStack.pop());
        System.out.println("Size of stack is: " + dynamicStack.size());
        System.out.println("peek value is: " + dynamicStack.peek());
        dynamicStack.show();
        System.out.println("Size of stack is: " + dynamicStack.size());
        System.out.println(dynamicStack.isEmpty() == true ? "com.anuj.Stack is empty." : "com.anuj.Stack is not empty.");
    }
}


