---

# 📘 Concurrent Data Structures Guide (Java)

A complete guide to **Concurrent Data Structures** using the
**Java Concurrency Framework (`java.util.concurrent`)**

---

# 📌 What are Concurrent Data Structures?

Thread-safe collections designed for **multi-threaded environments**.

👉 They replace manual `synchronized` blocks with:

* **Fine-grained locking** (lock per bucket / per segment)
* **Lock-free algorithms** (CAS — Compare And Swap)
* **Copy-on-Write** (snapshot on mutation)
* **Blocking semantics** (block producer/consumer threads automatically)

---

# 📊 Visual Overview

```
┌────────────────────────────────────────────────────────────────────────┐
│                   java.util.concurrent  hierarchy                      │
│                                                                        │
│  ConcurrentMap ──► ConcurrentHashMap                                  │
│  CopyOnWriteArrayList                                                  │
│  BlockingQueue ──► ArrayBlockingQueue                                  │
│               ──► LinkedBlockingQueue                                  │
│               ──► PriorityBlockingQueue                                │
└────────────────────────────────────────────────────────────────────────┘
```

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20210722152555/ConcurrentHashMap.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20221021164104/Screenshot20221021164022.png)

---

# 1️⃣ ConcurrentHashMap

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20210722152555/ConcurrentHashMap.png)

### 🔹 What

Thread-safe `HashMap`; uses **CAS + fine-grained bucket-level locking** (Java 8+).

```
Thread-1 ──► [Bucket-3]  ← lock only bucket 3
Thread-2 ──► [Bucket-7]  ← lock only bucket 7   (both run in parallel!)
Thread-3 ──► [Bucket-3]  ← waits for Thread-1
```

### 🔹 Why

Multiple threads can **read/write simultaneously** without locking the whole map,
giving dramatically higher throughput than `Collections.synchronizedMap(new HashMap<>())`.

### 🔹 Where

* Shared frequency counters across request threads
* Thread-safe caches (session store, rate-limiter state)
* Concurrent grouping / accumulation
* Any place `HashMap` is used from multiple threads

### 🔹 How

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Basic operations
map.put("apple", 1);
map.putIfAbsent("apple", 99);         // ignored – key already exists

// Atomic updates (prefer over manual get-then-put)
map.compute("apple", (k, v) -> v == null ? 1 : v + 1);
map.merge("cat", 1, Integer::sum);    // frequency count

// Lazy initialisation
map.computeIfAbsent("fruits", k -> new ArrayList<>()).add("mango");

// Iteration (weakly consistent – won't throw ConcurrentModificationException)
map.forEach((k, v) -> System.out.println(k + " -> " + v));
```

### 🔹 Complexity

| Operation           | Average  | Notes                              |
|---------------------|----------|------------------------------------|
| `get`               | `O(1)`   | fully lock-free for reads          |
| `put`               | `O(1)`   | locks only the affected bucket     |
| `remove`            | `O(1)`   |                                    |
| `compute/merge`     | `O(1)`   | atomic; no external sync needed    |
| `size`              | `O(n)`   | approximate in highly concurrent use|

### 🔹 Pros

* **High concurrency** — bucket-level locking instead of full-map lock
* **No `ConcurrentModificationException`** during iteration (weakly consistent)
* `compute`, `merge`, `putIfAbsent` are **atomic** — no race conditions
* Drop-in `HashMap` replacement

### 🔹 Cons

* Slightly **higher memory** than `HashMap`
* Does **NOT** allow `null` keys or `null` values
* `size()` is approximate under heavy concurrency

### 🔹 Pitfalls

* Null key/value → `NullPointerException`
* **Never** do manual check-then-act (`if !containsKey → put`) — use `putIfAbsent` / `compute`
* `putIfAbsent` and `computeIfAbsent` look similar but `computeIfAbsent` is lazier (lambda only called when absent)
* Not suitable as a replacement for a database transaction — no cross-key atomicity

---

# 2️⃣ CopyOnWriteArrayList

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20221021164104/Screenshot20221021164022.png)

### 🔹 What

Thread-safe `ArrayList`; **every write creates a fresh copy** of the underlying array.

```
Initial array  :  [ A, B, C ]

Thread-1 reads :  [ A, B, C ]  ← reads the existing snapshot (lock-free)
Thread-2 adds D:  [ A, B, C, D ]  ← new copy created atomically, old snapshot still visible
```

### 🔹 Why

Reads are **completely lock-free** and extremely fast — ideal when reads vastly
outnumber writes.

### 🔹 Where

* Event listener / observer registries
* Read-heavy shared configuration lists
* Caches where the list is rebuilt periodically but read constantly
* Whitelist / blacklist that rarely changes

### 🔹 How

```java
CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();

listeners.add("ListenerA");
listeners.addIfAbsent("ListenerA");   // atomic check-and-add

// Safe iteration – snapshot at the moment iterator was created
for (String l : listeners) {
    // Adding inside the loop won't throw ConcurrentModificationException
    listeners.add("ListenerD");       // safe, but invisible to current iterator
}

// Convert to ordered List
List<String> snapshot = new ArrayList<>(listeners);
```

### 🔹 Complexity

| Operation   | Time   | Notes                                |
|-------------|--------|--------------------------------------|
| `get`       | `O(1)` | lock-free                            |
| `contains`  | `O(n)` | linear scan on snapshot              |
| `add`       | `O(n)` | copies the entire array              |
| `remove`    | `O(n)` | copies the entire array              |
| `iterator`  | `O(1)` | returns snapshot, never CME          |

### 🔹 Pros

* **Lock-free reads** — zero synchronization overhead for readers
* **Snapshot iteration** — never throws `ConcurrentModificationException`
* `addIfAbsent` for atomic idempotent add

### 🔹 Cons

* **Writes are `O(n)`** — each write copies the whole array
* **High GC pressure** under frequent writes
* Iterator reflects a **stale snapshot** — newly added elements invisible

### 🔹 Pitfalls

* Never use for write-heavy workloads (`ArrayList` + `ReentrantReadWriteLock` is better)
* Iterators are snapshots — modifications after iterator creation are invisible
* `null` elements **are allowed** but make `contains(null)` risky — check nullability

---

# 3️⃣ BlockingQueue (Interface)

```
                    ┌──────────────────────────────────┐
                    │         BlockingQueue<E>          │
                    └──────────────┬───────────────────┘
             ┌────────────────────┼────────────────────┐
             ▼                    ▼                     ▼
  ArrayBlockingQueue    LinkedBlockingQueue    PriorityBlockingQueue
  (bounded, array)      (optionally bounded,   (unbounded, heap,
                         linked nodes)          priority order)
```

### Core API

| Method                  | Behaviour on full / empty                     |
|-------------------------|-----------------------------------------------|
| `put(e)`                | **blocks** until space available              |
| `take()`                | **blocks** until element available            |
| `offer(e)`              | returns `false` immediately if full           |
| `poll()`                | returns `null` immediately if empty           |
| `offer(e, time, unit)`  | waits up to timeout, then returns `false`     |
| `poll(time, unit)`      | waits up to timeout, then returns `null`      |
| `peek()`                | inspect head without removing; `null` if empty|
| `drainTo(collection, n)`| batch consume up to `n` elements              |

---

## 🔸 ArrayBlockingQueue

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20221128111300/ArrayBlockingQueue.png)

### 🔹 What

**Bounded** FIFO `BlockingQueue` backed by a **fixed-size array**.

```
Capacity = 3

Producer ──put──► [ T1 | T2 | T3 ]  ──take──► Consumer
                       ^ FULL – producer blocks until consumer takes
```

### 🔹 Why

Hard capacity limit provides **natural backpressure** — slows down a fast producer
automatically.

### 🔹 Where

* Classic bounded producer-consumer pipelines
* Thread pool work queues with controlled memory footprint
* Rate-limiting incoming requests

### 🔹 How

```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

// Non-blocking
queue.offer("task-1");                     // true
queue.offer("task-4");                     // false (full)
queue.poll();                              // "task-1"

// Blocking (best for producer-consumer)
queue.put("task-5");                       // blocks if full
String t = queue.take();                   // blocks if empty

// Timed
queue.offer("x", 5, TimeUnit.SECONDS);    // wait up to 5s
queue.poll(1, TimeUnit.SECONDS);           // wait up to 1s
```

### 🔹 Complexity

| Operation | Time   |
|-----------|--------|
| `put`     | `O(1)` |
| `take`    | `O(1)` |
| `offer`   | `O(1)` |
| `poll`    | `O(1)` |

### 🔹 Pros

* Bounded — **prevents OOM** from runaway producers
* Optional **fair mode** (`new ArrayBlockingQueue<>(n, true)`) — FIFO thread scheduling
* Simple and predictable

### 🔹 Cons

* **Fixed size** — must decide capacity at construction
* Cannot grow dynamically

### 🔹 Pitfalls

* **Never** call `put` and `take` from the same thread — deadlock
* Null elements → `NullPointerException`
* Fair mode reduces throughput — only use when strict ordering matters

---

## 🔸 LinkedBlockingQueue

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20221128181921/LinkedBlockingQueue.png)

### 🔹 What

**Optionally-bounded** FIFO `BlockingQueue` backed by **linked nodes**.

```
Head lock                  Tail lock
    ▼                          ▼
[ N1 ] ──► [ N2 ] ──► [ N3 ] ──► [ N4 ]
  ▲                              ▲
take() (consumer)          put() (producer)
```
> Separate head and tail locks → producers and consumers **never block each other**.

### 🔹 Why

Higher throughput than `ArrayBlockingQueue` due to **two independent locks**.

### 🔹 Where

* High-throughput message/event pipelines
* Log aggregation buffers
* Work queues inside `ThreadPoolExecutor`

### 🔹 How

```java
// Bounded
BlockingQueue<String> bounded = new LinkedBlockingQueue<>(100);

// Unbounded (capacity = Integer.MAX_VALUE) — use with care!
BlockingQueue<String> unbounded = new LinkedBlockingQueue<>();

bounded.put("job-1");
String job = bounded.take();

// Batch consume
List<String> batch = new ArrayList<>();
bounded.drainTo(batch, 50);             // drain up to 50 elements
```

### 🔹 Complexity

| Operation   | Time   | Notes                              |
|-------------|--------|------------------------------------|
| `put`       | `O(1)` | acquires tail lock only            |
| `take`      | `O(1)` | acquires head lock only            |
| `drainTo`   | `O(n)` | batch remove                       |

### 🔹 Pros

* **Higher throughput** than `ArrayBlockingQueue` under high concurrency
* Can be **unbounded** — no capacity planning required (use carefully)
* `drainTo()` for efficient batch consumption

### 🔹 Cons

* **Unbounded mode risks OOM** if producer outpaces consumer
* Slightly more GC pressure (node allocation) than array-based queue

### 🔹 Pitfalls

* Always specify a **capacity bound in production** — unbounded queues can exhaust heap
* Null elements → `NullPointerException`
* Do not compare throughput with `ArrayBlockingQueue` without benchmarking — hardware matters

---

## 🔸 PriorityBlockingQueue

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20221124153149/PBQ.png)

### 🔹 What

**Unbounded** `BlockingQueue` backed by a **min-heap**; elements are polled in **priority order**.

```
put(50)  put(10)  put(30)  put(20)
         ↓
    Min-Heap internally:
         10
        /  \
       20   30
      /
     50

take() → 10  (always the minimum / highest-priority)
```

### 🔹 Why

Enables **priority-based task scheduling** across threads without manual sorting.

### 🔹 Where

* Priority task schedulers (alert before batch job)
* Hospital / airline system emergency queues
* Dijkstra / A* shortest-path algorithms with threads
* Rate-limiting with priority lanes

### 🔹 How

```java
// Natural order (min-heap)
PriorityBlockingQueue<Integer> minHeap = new PriorityBlockingQueue<>();
minHeap.put(50); minHeap.put(10); minHeap.put(30);
minHeap.take();  // 10

// Reverse order (max-heap)
PriorityBlockingQueue<Integer> maxHeap =
    new PriorityBlockingQueue<>(10, Comparator.reverseOrder());

// Custom priority via Comparable
record Task(String name, int priority) implements Comparable<Task> {
    public int compareTo(Task o) { return Integer.compare(this.priority, o.priority); }
}
PriorityBlockingQueue<Task> tasks = new PriorityBlockingQueue<>();
tasks.put(new Task("Batch", 8));
tasks.put(new Task("Alert", 1));
tasks.take();  // Alert(p=1)  ← processed first
```

### 🔹 Complexity

| Operation | Time       |
|-----------|------------|
| `put`     | `O(log n)` |
| `take`    | `O(log n)` |
| `peek`    | `O(1)`     |

### 🔹 Pros

* **Priority-ordered consumption** without external sorting
* `take()` blocks when empty — safe consumer threads
* No capacity limit — never blocks on `put`

### 🔹 Cons

* **Unbounded** — can grow without limit; monitor queue depth
* **Iteration order is NOT sorted** — only `take()`/`poll()` are priority-ordered

### 🔹 Pitfalls

* Null elements → `NullPointerException`
* Equal-priority elements have **no guaranteed FIFO order**
* Custom objects must implement `Comparable` or a `Comparator` must be supplied
* Use `drainTo()` into a sorted collection if you need a sorted snapshot

---

# 🧠 Interview Cheat Sheet

| Use Case                                     | Best Choice               |
|----------------------------------------------|---------------------------|
| Thread-safe key-value store                  | `ConcurrentHashMap`       |
| Atomic frequency count / accumulation        | `ConcurrentHashMap.merge` |
| Read-heavy shared list (listeners, config)   | `CopyOnWriteArrayList`    |
| Bounded producer-consumer pipeline           | `ArrayBlockingQueue`      |
| High-throughput unbounded queue              | `LinkedBlockingQueue`     |
| Priority-based task scheduling               | `PriorityBlockingQueue`   |
| Thread-safe sorted set                       | `ConcurrentSkipListSet`   |
| Thread-safe sorted map                       | `ConcurrentSkipListMap`   |

---

# 🔁 Blocking vs Non-Blocking API

```
                    put / take          offer / poll         offer(t) / poll(t)
                    ──────────────      ─────────────────    ──────────────────
Full queue    :     BLOCKS              returns false        waits up to timeout
Empty queue   :     BLOCKS              returns null         waits up to timeout
```

```java
// Always prefer these patterns:
queue.put(item);                           // blocking – correct for producer
item = queue.take();                       // blocking – correct for consumer

queue.offer(item, 5, TimeUnit.SECONDS);   // timed – for responsive producers
item = queue.poll(5, TimeUnit.SECONDS);   // timed – for responsive consumers
```

---

# 🚀 Real-World Mapping

| Scenario                          | Data Structure             |
|-----------------------------------|----------------------------|
| 🌐 Web session store              | `ConcurrentHashMap`        |
| 📡 Event listener registry        | `CopyOnWriteArrayList`     |
| 📬 Task queue (thread pool)       | `LinkedBlockingQueue`      |
| 🚦 Rate limiter with backpressure | `ArrayBlockingQueue`       |
| 🏥 Emergency alert queue          | `PriorityBlockingQueue`    |
| 📊 Concurrent word frequency      | `ConcurrentHashMap.merge`  |

---

# 🔥 Final Takeaways

* **`ConcurrentHashMap`** — use `compute` / `merge` for atomic updates; never null keys/values
* **`CopyOnWriteArrayList`** — perfect for listeners; writes are expensive (`O(n)`)
* **`ArrayBlockingQueue`** — bounded backpressure; use `put`/`take` for blocking semantics
* **`LinkedBlockingQueue`** — higher throughput than ABQ; always **set a capacity bound** in production
* **`PriorityBlockingQueue`** — unbounded; elements polled by priority, not insertion order
* Prefer **`java.util.concurrent`** collections over `Collections.synchronizedXxx` wrappers
* **`put`/`take` are blocking** — always run producer and consumer on **separate threads**
* Monitor **queue depth** in production — a growing queue signals a slow consumer

---


