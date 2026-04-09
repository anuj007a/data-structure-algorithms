package com.wraith.collection.framework.special.data.structures;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SpecialDataStructuresDemo {

    public static void main(String[] args) {
        SpecialDataStructuresDemo demo = new SpecialDataStructuresDemo();

        demo.dequeOverview();
        demo.arrayDequeExample();
        demo.linkedListAsDequeExample();
        demo.priorityQueueExample();

        demo.finalTipsForBeginners();
    }

    // ─────────────────────────────────────────────────────────────
    // 1) Deque Overview
    // ─────────────────────────────────────────────────────────────
    private void dequeOverview() {
        title("DEQUE (DOUBLE-ENDED QUEUE)");

        explain(
                "A Deque is a double-ended queue that supports insert/remove at both front and rear.",
                "One data structure can behave as both a stack (LIFO) and a queue (FIFO).",
                "Undo/redo, sliding window, browser history, BFS, task scheduling pipelines.",
                "Use addFirst/addLast/removeFirst/removeLast or push/pop/offer/poll depending on style.",
                "End operations are O(1) in common implementations such as ArrayDeque and LinkedList.",
                "Very flexible; cleaner modern replacement for legacy Stack in most cases.",
                "Too many similar method names can confuse beginners initially.",
                "Pick one mental model per flow: stack-style OR queue-style, do not mix randomly."
        );

        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(20);
        deque.addLast(30);
        deque.addFirst(10);

        System.out.println("Initial deque           : " + deque);
        System.out.println("removeFirst()           : " + deque.removeFirst());
        System.out.println("removeLast()            : " + deque.removeLast());
        System.out.println("Deque now               : " + deque);

        // Stack behavior
        Deque<String> stack = new ArrayDeque<>();
        stack.push("page-1");
        stack.push("page-2");
        stack.push("page-3");
        System.out.println("As stack (push/pop)     : " + stack + " -> pop=" + stack.pop());

        // Queue behavior
        Deque<String> queue = new ArrayDeque<>();
        queue.offerLast("A");
        queue.offerLast("B");
        queue.offerLast("C");
        System.out.println("As queue (offer/poll)   : " + queue + " -> poll=" + queue.pollFirst());
    }

    // ─────────────────────────────────────────────────────────────
    // 2) ArrayDeque
    // ─────────────────────────────────────────────────────────────
    private void arrayDequeExample() {
        title("ARRAYDEQUE");

        explain(
                "Resizable-array implementation of Deque; usually the best default for stack/queue use-cases.",
                "Fast O(1) amortized operations at both ends with better locality than LinkedList.",
                "Stacks, queues, BFS, DFS, sliding window, expression evaluation, undo/redo.",
                "Use push/pop/peek for stack; offerFirst/offerLast/pollFirst/pollLast for deque/queue operations.",
                "add/remove/peek at both ends are O(1) amortized; random access is not supported.",
                "Very fast, low overhead, better cache locality than LinkedList.",
                "Does NOT allow null elements; not thread-safe.",
                "Prefer ArrayDeque over legacy Stack and usually over LinkedList for stack/queue behavior."
        );

        ArrayDeque<Integer> numbers = new ArrayDeque<>();

        // Deque operations
        numbers.addFirst(20);
        numbers.addLast(30);
        numbers.addFirst(10);
        numbers.addLast(40);
        System.out.println("After addFirst/addLast  : " + numbers);

        System.out.println("peekFirst()             : " + numbers.peekFirst());
        System.out.println("peekLast()              : " + numbers.peekLast());
        System.out.println("pollFirst()             : " + numbers.pollFirst());
        System.out.println("pollLast()              : " + numbers.pollLast());
        System.out.println("Deque now               : " + numbers);

        // Stack style
        ArrayDeque<String> browserBack = new ArrayDeque<>();
        browserBack.push("home");
        browserBack.push("products");
        browserBack.push("checkout");
        System.out.println("Stack style             : " + browserBack);
        System.out.println("pop()                   : " + browserBack.pop());
        System.out.println("peek()                  : " + browserBack.peek());

        // Queue style
        ArrayDeque<String> tasks = new ArrayDeque<>();
        tasks.offer("task-1");
        tasks.offer("task-2");
        tasks.offer("task-3");
        System.out.println("Queue style             : " + tasks);
        System.out.println("poll()                  : " + tasks.poll());
        System.out.println("After poll              : " + tasks);

        // Sliding window style idea
        int[] arr = {4, 2, 12, 3, 8};
        ArrayDeque<Integer> window = new ArrayDeque<>();
        for (int x : arr) {
            window.offerLast(x);
            if (window.size() > 3) window.pollFirst();
            System.out.println("Window                  : " + window);
        }
    }

    // ─────────────────────────────────────────────────────────────
    // 3) LinkedList as Deque
    // ─────────────────────────────────────────────────────────────
    private void linkedListAsDequeExample() {
        title("LINKEDLIST AS DEQUE");

        explain(
                "LinkedList implements Deque using doubly-linked nodes with prev/next references.",
                "Useful when you want Deque operations plus List-style features in one class.",
                "Queue/deque flows, editor history, task lists with frequent end insertions/removals.",
                "Use the same Deque API: addFirst/addLast/getFirst/getLast/removeFirst/removeLast.",
                "End insert/remove are O(1); random indexed access is O(n).",
                "Supports Deque and List interfaces in one implementation; allows null elements.",
                "More memory overhead than ArrayDeque; slower in practice for pure stack/queue usage.",
                "Do not choose LinkedList only because it sounds dynamic; ArrayDeque is usually faster for deque work."
        );

        LinkedList<String> trainStops = new LinkedList<>();
        trainStops.addFirst("Station-B");
        trainStops.addFirst("Station-A");
        trainStops.addLast("Station-C");
        trainStops.addLast("Station-D");

        System.out.println("Train route             : " + trainStops);
        System.out.println("getFirst()              : " + trainStops.getFirst());
        System.out.println("getLast()               : " + trainStops.getLast());
        System.out.println("removeFirst()           : " + trainStops.removeFirst());
        System.out.println("removeLast()            : " + trainStops.removeLast());
        System.out.println("Route now               : " + trainStops);

        // Null support demo (ArrayDeque does NOT allow null)
        trainStops.add(null);
        System.out.println("After add(null)         : " + trainStops);
        trainStops.removeLastOccurrence(null);

        // List + Deque dual nature
        trainStops.add("Station-X");
        trainStops.add("Station-Y");
        System.out.println("Index access get(1)     : " + trainStops.get(1));
        System.out.println("List + Deque combo      : " + trainStops);

        // Queue style
        Queue<String> supportTickets = new LinkedList<>();
        supportTickets.offer("ticket-101");
        supportTickets.offer("ticket-102");
        supportTickets.offer("ticket-103");
        System.out.println("Queue via LinkedList    : " + supportTickets);
        System.out.println("poll()                  : " + supportTickets.poll());
        System.out.println("Queue now               : " + supportTickets);
    }

    // ─────────────────────────────────────────────────────────────
    // 4) PriorityQueue
    // ─────────────────────────────────────────────────────────────
    private void priorityQueueExample() {
        title("PRIORITYQUEUE");

        explain(
                "Heap-based queue ordered by priority, not insertion order.",
                "Always retrieves the smallest element first by default (min-heap).",
                "Schedulers, leaderboards, top-k problems, shortest-path algorithms, job prioritization.",
                "Use offer/poll/peek; use natural order for min-heap or Comparator for max-heap/custom priority.",
                "offer O(log n), poll O(log n), peek O(1).",
                "Efficient priority retrieval; easy min-heap or max-heap setup.",
                "Iteration order is NOT sorted order; not thread-safe.",
                "Do not expect FIFO; custom objects need Comparable or a Comparator."
        );

        // Min-heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(30);
        minHeap.offer(10);
        minHeap.offer(20);
        minHeap.offer(5);
        System.out.println("MinHeap raw             : " + minHeap);
        System.out.println("peek()                  : " + minHeap.peek());
        System.out.println("poll()                  : " + minHeap.poll());
        System.out.println("After poll              : " + minHeap);

        // Max-heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(30);
        maxHeap.offer(10);
        maxHeap.offer(20);
        maxHeap.offer(50);
        System.out.println("MaxHeap raw             : " + maxHeap);
        System.out.println("MaxHeap poll()          : " + maxHeap.poll());

        // Custom objects with Comparator
        class Patient {
            final String name;
            final int severity;

            Patient(String name, int severity) {
                this.name = name;
                this.severity = severity;
            }

            @Override
            public String toString() {
                return name + "(severity=" + severity + ")";
            }
        }

        PriorityQueue<Patient> hospitalQueue = new PriorityQueue<>((a, b) -> Integer.compare(b.severity, a.severity));
        hospitalQueue.offer(new Patient("John", 2));
        hospitalQueue.offer(new Patient("Emma", 5));
        hospitalQueue.offer(new Patient("Raj", 3));
        hospitalQueue.offer(new Patient("Liam", 1));

        System.out.println("Hospital queue raw      : " + hospitalQueue);
        System.out.println("Treat next              : " + hospitalQueue.poll());
        System.out.println("Treat next              : " + hospitalQueue.poll());

        // Top-K smallest demo
        int[] scores = {55, 91, 72, 88, 67, 99, 61};
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int score : scores) pq.offer(score);

        System.out.println("Sorted by repeated poll :");
        while (!pq.isEmpty()) {
            System.out.println("  " + pq.poll());
        }

        // Heapify from collection
        PriorityQueue<Integer> heapFromCollection = new PriorityQueue<>(Arrays.asList(9, 1, 7, 2, 6));
        System.out.println("Heap from collection    : " + heapFromCollection);
    }

    // ─────────────────────────────────────────────────────────────
    // Tips
    // ─────────────────────────────────────────────────────────────
    private void finalTipsForBeginners() {
        title("BEGINNER CHOICE GUIDE – SPECIAL DATA STRUCTURES");
        divider();
        System.out.println("1) Use Deque when you need both front and rear operations.");
        System.out.println("2) Use ArrayDeque as the default choice for stack and queue behavior.");
        System.out.println("3) Use LinkedList as Deque only if you also need List-style operations or null support.");
        System.out.println("4) Use PriorityQueue when processing order depends on priority, not arrival time.");
        System.out.println("5) ArrayDeque does NOT allow null; LinkedList allows null; PriorityQueue does NOT allow null.");
        System.out.println("6) PriorityQueue iteration is not sorted — use repeated poll() to get sorted priority order.");
        System.out.println("7) Prefer interfaces in code: Deque<E> deque = new ArrayDeque<>(); Queue<E> pq = new PriorityQueue<>();");
        System.out.println("8) For thread-safe priority/deque use concurrent collections, not these basic implementations.");
        divider();
    }

    // ─────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────
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

