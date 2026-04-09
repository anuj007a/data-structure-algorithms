package com.wraith.collection.framework.linear.data.structure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

public class LinearDataStructuresDemo {

    public static void main(String[] args) {
        LinearDataStructuresDemo demo = new LinearDataStructuresDemo();

        demo.arrayExample();
        demo.arrayListExample();
        demo.linkedListExample();
        demo.vectorExample();
        demo.stackExample();
        demo.queueExample();
        demo.priorityQueueExample();
        demo.dequeExample();

        demo.finalTipsForBeginners();
    }



    // 1) Array
    private void arrayExample() {
        title("ARRAY");

        explain(
                "Fixed-size contiguous memory data structure.",
                "Fast random access by index.",
                "When size is known and performance is important.",
                "Declare with [] and access via index positions.",
                "Read O(1), update O(1), search O(n), insert/delete middle O(n).",
                "Very fast, memory-efficient.",
                "Fixed size; resizing needs new array.",
                "ArrayIndexOutOfBoundsException if index is invalid."
        );

        int[] arr1 = new int[3];
        int[] arr2 = {10, 20, 30};

        System.out.println("Initial arr1 = " + Arrays.toString(arr1));
        arr1[0] = 5;
        arr1[1] = 15;
        System.out.println("After updates arr1 = " + Arrays.toString(arr1));

        System.out.println("arr1[0] = " + arr1[0]);
        System.out.println("arr2 length = " + arr2.length);
    }

    // 2) ArrayList
    private void arrayListExample() {
        title("ARRAYLIST");

        explain(
                "Dynamic resizable array implementation of List.",
                "You do not need to manage size manually.",
                "Read-heavy use cases: product lists, dashboards, cached views.",
                "Use add/get/set/remove with index support.",
                "get O(1), append amortized O(1), insert/delete middle O(n).",
                "Easy API, fast reads, flexible size.",
                "Middle insert/delete can be slow for large lists.",
                "Arrays.asList returns fixed-size list (cannot add/remove)."
        );

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        System.out.println("list1 initial = " + list1);
        list1.add(10);
        list1.add(20);
        System.out.println("list1 after add = " + list1);
        list1.remove(0);
        System.out.println("list1 after remove index 0 = " + list1);

        System.out.println("list2 (fixed size view) = " + list2);
    }

    // 3) LinkedList
    private void linkedListExample() {
        title("LINKEDLIST");

        explain(
                "Doubly linked list; each node stores prev/next references.",
                "Efficient add/remove at beginning/end.",
                "Queue/deque-like workloads with frequent updates.",
                "Use addFirst/addLast/removeFirst/removeLast, or List methods.",
                "Insert/remove at ends O(1), random access O(n).",
                "Fast structural modifications at ends.",
                "Slow random index access compared to ArrayList.",
                "Using get(i) in loops can hurt performance on large lists."
        );

        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.addFirst(5);

        System.out.println("LinkedList after add/addFirst = " + list);
        System.out.println("removeFirst = " + list.removeFirst());
        System.out.println("LinkedList now = " + list);
    }

    // 4) Vector
    private void vectorExample() {
        title("VECTOR");

        explain(
                "Legacy synchronized dynamic array.",
                "Thread-safe operations by default.",
                "Mostly legacy systems or backward compatibility needs.",
                "Use similar APIs as ArrayList.",
                "Similar to ArrayList but with synchronization overhead.",
                "Built-in synchronization.",
                "Slower than ArrayList in single-threaded scenarios.",
                "Prefer ArrayList + explicit sync or concurrent collections in modern apps."
        );

        Vector<Integer> vector = new Vector<>();
        vector.add(100);
        vector.add(200);

        System.out.println("Vector = " + vector);
    }

    // 5) Stack (recommended: Deque)
    private void stackExample() {
        title("STACK (USING DEQUE)");

        explain(
                "LIFO: Last In First Out.",
                "Useful when latest item should be processed first.",
                "Undo/redo, recursion simulation, expression evaluation.",
                "Use Deque with push/pop/peek (prefer over legacy Stack class).",
                "push/pop/peek are O(1).",
                "Fast and clean for LIFO behavior.",
                "Not for random access patterns.",
                "Do not confuse stack order with queue order."
        );

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Stack after pushes = " + stack);
        System.out.println("pop = " + stack.pop());
        System.out.println("peek = " + stack.peek());
        System.out.println("Stack now = " + stack);
    }

    // 6) Queue
    private void queueExample() {
        title("QUEUE");

        explain(
                "FIFO: First In First Out.",
                "Items are processed in arrival order.",
                "Task scheduling, messaging, request buffering.",
                "Use offer/poll/peek on Queue interface.",
                "offer/poll/peek usually O(1) depending on implementation.",
                "Natural model for pipelines and job processing.",
                "Cannot directly access middle elements efficiently.",
                "poll returns null on empty queue; remove throws exception."
        );

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);

        System.out.println("Queue after offer = " + queue);
        System.out.println("poll = " + queue.poll());
        System.out.println("peek = " + queue.peek());
        System.out.println("Queue now = " + queue);
    }

    // 7) PriorityQueue
    private void priorityQueueExample() {
        title("PRIORITY QUEUE");

        explain(
                "Heap-based queue ordered by priority, not insertion order.",
                "Always process smallest (or highest-priority) element first.",
                "Schedulers, ranking, shortest-path style problems.",
                "Use natural order (min-heap) or custom comparator (max-heap).",
                "offer O(log n), poll O(log n), peek O(1).",
                "Efficient top-priority retrieval.",
                "Iteration order is not sorted order.",
                "Do not assume FIFO behavior."
        );

        Queue<Integer> minHeap = new PriorityQueue<>();
        Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        minHeap.offer(30);
        minHeap.offer(10);
        minHeap.offer(20);

        maxHeap.offer(30);
        maxHeap.offer(10);
        maxHeap.offer(20);

        System.out.println("MinHeap raw = " + minHeap);
        System.out.println("MaxHeap raw = " + maxHeap);
        System.out.println("MinHeap poll = " + minHeap.poll());
        System.out.println("MaxHeap poll = " + maxHeap.poll());
    }

    // 8) Deque
    private void dequeExample() {
        title("DEQUE");

        explain(
                "Double-ended queue: insert/remove from both ends.",
                "Can work as both stack and queue.",
                "Sliding window, LRU cache patterns, BFS variants.",
                "Use addFirst/addLast/removeFirst/removeLast.",
                "End operations are O(1) in common implementations.",
                "Very flexible data structure.",
                "API has many methods, can confuse beginners initially.",
                "Choose consistent style (queue-like or stack-like) in one flow."
        );

        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(10);
        deque.addLast(20);
        deque.addFirst(5);

        System.out.println("Deque after inserts = " + deque);
        System.out.println("removeFirst = " + deque.removeFirst());
        System.out.println("removeLast = " + deque.removeLast());
        System.out.println("Deque now = " + deque);
    }

    private void finalTipsForBeginners() {
        title("BEGINNER CHOICE GUIDE");
        divider();
        System.out.println("1) Start with ArrayList for general List use.");
        System.out.println("2) Use LinkedList when you mostly add/remove at ends.");
        System.out.println("3) Use ArrayDeque for stack/queue before legacy Stack/Vector.");
        System.out.println("4) Use PriorityQueue only when priority order matters.");
        System.out.println("5) Use interface types in code: List, Queue, Deque.");
        System.out.println("6) Learn Big-O basics to choose the right structure.");
        divider();
    }

    private void divider() {
        System.out.println("--------------------------------------------------");
    }

    private void title(String heading) {
        System.out.println("\n=== " + heading + " ===");
    }

    private void explain(String what, String why, String where, String how,
                         String complexity, String pros, String cons, String pitfalls) {
        divider();
        System.out.println("What       : " + what);
        System.out.println("Why        : " + why);
        System.out.println("Where      : " + where);
        System.out.println("How        : " + how);
        System.out.println("Complexity : " + complexity);
        System.out.println("Pros       : " + pros);
        System.out.println("Cons       : " + cons);
        System.out.println("Pitfalls   : " + pitfalls);
        divider();
    }
}