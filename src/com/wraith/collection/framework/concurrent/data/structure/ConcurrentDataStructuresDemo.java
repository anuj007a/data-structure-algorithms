package com.wraith.collection.framework.concurrent.data.structure;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentDataStructuresDemo {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentDataStructuresDemo demo = new ConcurrentDataStructuresDemo();

        demo.concurrentHashMapExample();
        demo.copyOnWriteArrayListExample();
        demo.arrayBlockingQueueExample();
        demo.linkedBlockingQueueExample();
        demo.priorityBlockingQueueExample();

        demo.finalTipsForBeginners();
    }

    // ─────────────────────────────────────────────────────────────
    // 1) ConcurrentHashMap
    // ─────────────────────────────────────────────────────────────
    private void concurrentHashMapExample() throws InterruptedException {
        title("CONCURRENTHASHMAP");

        explain(
                "Thread-safe hash-table Map; uses CAS + fine-grained bucket-level locking (Java 8+).",
                "Multiple threads can read/write simultaneously without locking the whole map.",
                "Shared counters, caches, frequency maps, session stores in multi-threaded apps.",
                "Drop-in replacement for HashMap; use compute/merge/putIfAbsent for atomic updates.",
                "get/put O(1) avg; no full-map lock → much higher throughput than synchronizedMap.",
                "High concurrency; no ConcurrentModificationException during iteration.",
                "Slightly higher memory than HashMap; does NOT allow null keys or null values.",
                "putIfAbsent/compute/merge are atomic – prefer them over manual check-then-act."
        );

        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();

        // Basic put / get / putIfAbsent
        wordCount.put("apple", 1);
        wordCount.put("banana", 3);
        wordCount.putIfAbsent("apple", 99);        // ignored – key already exists
        wordCount.putIfAbsent("cherry", 5);        // inserted – new key
        System.out.println("Map after puts          : " + wordCount);

        // getOrDefault
        System.out.println("get('banana')           : " + wordCount.get("banana"));
        System.out.println("get('grape', 0)         : " + wordCount.getOrDefault("grape", 0));

        // compute – atomic increment
        wordCount.compute("apple", (k, v) -> v == null ? 1 : v + 1);
        System.out.println("After compute apple++   : " + wordCount.get("apple"));

        // merge – elegant frequency count
        String[] tokens = {"cat", "dog", "cat", "bird", "dog", "cat"};
        ConcurrentHashMap<String, Integer> freq = new ConcurrentHashMap<>();
        for (String t : tokens) {
            freq.merge(t, 1, Integer::sum);        // atomic add-or-increment
        }
        System.out.println("Frequency (merge)       : " + freq);

        // computeIfAbsent – lazy initialisation
        ConcurrentHashMap<String, List<String>> groups = new ConcurrentHashMap<>();
        groups.computeIfAbsent("fruits", k -> new java.util.ArrayList<>()).add("apple");
        groups.computeIfAbsent("fruits", k -> new java.util.ArrayList<>()).add("mango");
        groups.computeIfAbsent("veggies", k -> new java.util.ArrayList<>()).add("carrot");
        System.out.println("Groups                  : " + groups);

        // ── Multi-threaded frequency counter ─────────────────────
        ConcurrentHashMap<String, AtomicInteger> concurrentFreq = new ConcurrentHashMap<>();
        String[] shared = {"a", "b", "a", "c", "b", "a", "d", "c", "a"};
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (String s : shared) {
            pool.submit(() ->
                concurrentFreq.computeIfAbsent(s, k -> new AtomicInteger(0)).incrementAndGet()
            );
        }
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Concurrent freq count   : " + concurrentFreq);

        // remove / size
        wordCount.remove("cherry");
        System.out.println("After remove('cherry')  : " + wordCount);
        System.out.println("size                    : " + wordCount.size());
        System.out.println("containsKey('banana')   : " + wordCount.containsKey("banana"));

        // forEach (parallel-aware)
        System.out.println("forEach entries         :");
        wordCount.forEach((k, v) -> System.out.println("  " + k + " -> " + v));
    }

    // ─────────────────────────────────────────────────────────────
    // 2) CopyOnWriteArrayList
    // ─────────────────────────────────────────────────────────────
    private void copyOnWriteArrayListExample() throws InterruptedException {
        title("COPYONWRITEARRAYLIST");

        explain(
                "Thread-safe List; every write (add/remove/set) copies the entire underlying array.",
                "Reads are lock-free and extremely fast; safe for read-heavy, write-rare scenarios.",
                "Event listener lists, observer registries, read-heavy shared configuration lists.",
                "Use same List API (add/get/remove/iterator); no synchronization needed by callers.",
                "read O(1), write O(n) due to array copy; iteration never throws ConcurrentModificationException.",
                "Lock-free reads; snapshot iteration – never throws ConcurrentModificationException.",
                "Writes are O(n) and memory-intensive; not suitable for write-heavy workloads.",
                "Iterator reflects the snapshot at creation time – newly added elements are invisible to it."
        );

        CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();

        // Adding elements (each add copies the array internally)
        listeners.add("ListenerA");
        listeners.add("ListenerB");
        listeners.add("ListenerC");
        System.out.println("Listeners               : " + listeners);

        // get / contains / size
        System.out.println("get(1)                  : " + listeners.get(1));
        System.out.println("contains('ListenerB')   : " + listeners.contains("ListenerB"));
        System.out.println("size                    : " + listeners.size());

        // Safe iteration – snapshot at iterator creation time
        System.out.println("Iterating (snapshot)    :");
        for (String l : listeners) {
            System.out.println("  " + l);
            // Adding during iteration is SAFE – won't throw ConcurrentModificationException
            if (l.equals("ListenerB")) listeners.add("ListenerD");
        }
        System.out.println("After add during iter   : " + listeners);

        // addIfAbsent – atomic check-then-add
        listeners.addIfAbsent("ListenerA");   // already present – ignored
        listeners.addIfAbsent("ListenerE");   // new – added
        System.out.println("After addIfAbsent       : " + listeners);

        // remove
        listeners.remove("ListenerC");
        System.out.println("After remove C          : " + listeners);

        // ── Multi-threaded reader/writer demo ─────────────────────
        CopyOnWriteArrayList<Integer> shared = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        ExecutorService pool = Executors.newFixedThreadPool(4);

        // 3 readers – concurrent, lock-free
        for (int i = 0; i < 3; i++) {
            pool.submit(() -> {
                int sum = shared.stream().mapToInt(Integer::intValue).sum();
                System.out.println("  Reader sum            : " + sum);
            });
        }
        // 1 writer – copies array on each write
        pool.submit(() -> shared.add(99));

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("After concurrent writes : " + shared);
    }

    // ─────────────────────────────────────────────────────────────
    // 3) ArrayBlockingQueue
    // ─────────────────────────────────────────────────────────────
    private void arrayBlockingQueueExample() throws InterruptedException {
        title("ARRAYBLOCKINGQUEUE");

        explain(
                "Bounded FIFO BlockingQueue backed by a fixed-size array.",
                "Controls backpressure – producers block when full, consumers block when empty.",
                "Classic bounded producer-consumer pipelines, thread pool work queues.",
                "Use put()/take() for blocking; offer()/poll() for timed/non-blocking variants.",
                "put O(1), take O(1), offer O(1); capacity is fixed at construction time.",
                "Bounded capacity prevents unbounded memory growth; fair ordering option available.",
                "Fixed size – must choose capacity at creation; cannot grow dynamically.",
                "Deadlock possible if producer and consumer share the same thread; always use separate threads."
        );

        final int CAPACITY = 3;
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(CAPACITY);

        // Non-blocking offer
        System.out.println("offer('task-1')         : " + queue.offer("task-1"));
        System.out.println("offer('task-2')         : " + queue.offer("task-2"));
        System.out.println("offer('task-3')         : " + queue.offer("task-3"));
        System.out.println("offer('task-4') [full]  : " + queue.offer("task-4")); // false – full
        System.out.println("Queue (full)            : " + queue);
        System.out.println("size / remainingCapacity: " + queue.size() + " / " + queue.remainingCapacity());

        // Non-blocking poll
        System.out.println("poll()                  : " + queue.poll());
        System.out.println("peek()                  : " + queue.peek());
        System.out.println("Queue after poll        : " + queue);

        // Timed offer / poll
        System.out.println("offer(5s timeout)       : " + queue.offer("task-5", 5, TimeUnit.SECONDS));
        System.out.println("poll(1s timeout)        : " + queue.poll(1, TimeUnit.SECONDS));

        // ── Producer-Consumer with blocking put/take ───────────────
        System.out.println("--- Producer-Consumer (ArrayBlockingQueue) ---");
        BlockingQueue<Integer> pipe = new ArrayBlockingQueue<>(2);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Producer
        pool.submit(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    pipe.put(i);
                    System.out.println("  Produced             : " + i
                            + "  [queue=" + pipe.size() + "]");
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        // Consumer
        pool.submit(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int val = pipe.take();
                    System.out.println("  Consumed             : " + val);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    // ─────────────────────────────────────────────────────────────
    // 4) LinkedBlockingQueue
    // ─────────────────────────────────────────────────────────────
    private void linkedBlockingQueueExample() throws InterruptedException {
        title("LINKEDBLOCKINGQUEUE");

        explain(
                "Optionally-bounded FIFO BlockingQueue backed by linked nodes.",
                "Higher throughput than ArrayBlockingQueue (separate head/tail locks); flexible capacity.",
                "Unbounded task queues, log pipelines, message buffers between thread pools.",
                "Same API as ArrayBlockingQueue; omit capacity to create an unbounded queue.",
                "put O(1), take O(1); separate lock for head and tail → higher concurrency.",
                "Higher throughput than ArrayBlockingQueue; can be unbounded (Integer.MAX_VALUE).",
                "Unbounded usage can cause OutOfMemoryError if producer is much faster than consumer.",
                "Always specify capacity for production systems to prevent unbounded memory growth."
        );

        // ── Bounded LinkedBlockingQueue ───────────────────────────
        BlockingQueue<String> bounded = new LinkedBlockingQueue<>(3);
        bounded.put("job-1");
        bounded.put("job-2");
        bounded.put("job-3");
        System.out.println("Bounded queue           : " + bounded);
        System.out.println("offer (full)            : " + bounded.offer("job-4")); // false

        bounded.take();
        System.out.println("After take              : " + bounded);

        // ── Unbounded LinkedBlockingQueue ─────────────────────────
        BlockingQueue<String> unbounded = new LinkedBlockingQueue<>();  // capacity = Integer.MAX_VALUE
        unbounded.put("event-A");
        unbounded.put("event-B");
        unbounded.put("event-C");
        System.out.println("Unbounded queue         : " + unbounded);
        System.out.println("size                    : " + unbounded.size());

        // drainTo – batch consume
        java.util.List<String> batch = new java.util.ArrayList<>();
        int drained = unbounded.drainTo(batch, 2);  // drain at most 2
        System.out.println("drainTo(batch, 2)       : drained=" + drained + "  batch=" + batch);
        System.out.println("Queue after drain       : " + unbounded);

        // ── Producer-Consumer with separate head/tail locks ────────
        System.out.println("--- Producer-Consumer (LinkedBlockingQueue) ---");
        BlockingQueue<String> lbq = new LinkedBlockingQueue<>(4);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Producer
        pool.submit(() -> {
            try {
                String[] items = {"msg-A", "msg-B", "msg-C", "msg-D"};
                for (String item : items) {
                    lbq.put(item);
                    System.out.println("  Produced             : " + item);
                    Thread.sleep(40);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        // Consumer
        pool.submit(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    String item = lbq.poll(3, TimeUnit.SECONDS);
                    System.out.println("  Consumed             : " + item);
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    // ─────────────────────────────────────────────────────────────
    // 5) PriorityBlockingQueue
    // ─────────────────────────────────────────────────────────────
    private void priorityBlockingQueueExample() throws InterruptedException {
        title("PRIORITYBLOCKINGQUEUE");

        explain(
                "Unbounded BlockingQueue backed by a min-heap; elements polled in priority order.",
                "Processes highest-priority tasks first across threads, not in arrival order.",
                "Priority-based task schedulers, alert processing, Dijkstra's algorithm with threads.",
                "Use natural order (Comparable) or supply a Comparator; take() blocks only when empty.",
                "put O(log n), take O(log n), peek O(1); unbounded – never blocks on put.",
                "Priority-ordered consumption; blocking take() makes it safe for consumer threads.",
                "Unbounded – can grow without limit; iteration order is NOT guaranteed to be sorted.",
                "Null elements NOT allowed; does not guarantee FIFO for equal-priority elements."
        );

        // ── Natural order (Integer min-heap) ──────────────────────
        PriorityBlockingQueue<Integer> minHeap = new PriorityBlockingQueue<>();
        minHeap.put(50);
        minHeap.put(10);
        minHeap.put(30);
        minHeap.put(20);
        minHeap.put(40);
        System.out.println("Raw queue (heap order)  : " + minHeap);  // internal heap, not sorted
        System.out.println("take() #1               : " + minHeap.take()); // 10 (min)
        System.out.println("take() #2               : " + minHeap.take()); // 20
        System.out.println("peek()                  : " + minHeap.peek()); // 30

        // ── Custom Comparator – max-heap ──────────────────────────
        PriorityBlockingQueue<Integer> maxHeap =
                new PriorityBlockingQueue<>(10, Comparator.reverseOrder());
        maxHeap.put(50);
        maxHeap.put(10);
        maxHeap.put(30);
        System.out.println("Max-heap take()         : " + maxHeap.take()); // 50 (max)

        // ── Task with priority ─────────────────────────────────────
        record Task(String name, int priority) implements Comparable<Task> {
            @Override
            public int compareTo(Task other) {
                return Integer.compare(this.priority, other.priority); // lower number = higher priority
            }
            @Override public String toString() { return name + "(p=" + priority + ")"; }
        }

        PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>();
        taskQueue.put(new Task("LowPriority",    5));
        taskQueue.put(new Task("Critical",       1));
        taskQueue.put(new Task("Medium",         3));
        taskQueue.put(new Task("High",           2));
        taskQueue.put(new Task("Background",     9));

        System.out.println("Task queue raw          : " + taskQueue);
        System.out.println("Processing order        :");
        while (!taskQueue.isEmpty()) {
            System.out.println("  " + taskQueue.take());
        }

        // ── Producer-Consumer with priority scheduling ─────────────
        System.out.println("--- Priority Producer-Consumer ---");
        PriorityBlockingQueue<Task> scheduledQueue = new PriorityBlockingQueue<>();
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Producer: submit tasks in arbitrary order
        pool.submit(() -> {
            try {
                Task[] tasks = {
                    new Task("Batch-Report",  8),
                    new Task("Alert-Email",   1),
                    new Task("DB-Backup",     6),
                    new Task("PaymentProc",   2)
                };
                for (Task t : tasks) {
                    scheduledQueue.put(t);
                    System.out.println("  Submitted            : " + t);
                    Thread.sleep(30);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        // Consumer: always picks highest-priority (lowest number) task
        pool.submit(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    Task t = scheduledQueue.poll(3, TimeUnit.SECONDS);
                    System.out.println("  Executing            : " + t);
                    Thread.sleep(60);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    // ─────────────────────────────────────────────────────────────
    // Tips
    // ─────────────────────────────────────────────────────────────
    private void finalTipsForBeginners() {
        title("BEGINNER CHOICE GUIDE – CONCURRENT DATA STRUCTURES");
        divider();
        System.out.println("1) Use ConcurrentHashMap      : thread-safe key-value store; use compute/merge for atomic updates.");
        System.out.println("2) Use CopyOnWriteArrayList   : read-heavy shared lists (listeners, config); writes are O(n).");
        System.out.println("3) Use ArrayBlockingQueue     : bounded producer-consumer with strict backpressure.");
        System.out.println("4) Use LinkedBlockingQueue    : higher throughput than ABQ; set capacity to avoid OOM.");
        System.out.println("5) Use PriorityBlockingQueue  : priority-based task scheduling across threads.");
        System.out.println("6) NEVER put null into ConcurrentHashMap, CopyOnWriteArrayList, or any BlockingQueue.");
        System.out.println("7) Always use put()/take() for guaranteed blocking; offer()/poll() for timeout or non-blocking.");
        System.out.println("8) Prefer concurrent collections over synchronized wrappers (Collections.synchronizedXxx).");
        System.out.println("9) ConcurrentHashMap iteration is weakly-consistent – it won't throw ConcurrentModificationException.");
        System.out.println("10)PriorityBlockingQueue is unbounded – always monitor queue depth in production.");
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

