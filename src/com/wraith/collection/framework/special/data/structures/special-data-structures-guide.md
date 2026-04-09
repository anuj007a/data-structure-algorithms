---

# 📘 Special Data Structures Guide (Java)

A focused guide to **Special Data Structures** using the
**Java Collections Framework**

---

# 📌 What are Special Data Structures?

These are collections designed for **special access patterns** rather than simple sequential storage.

👉 In this section we focus on:

* **Deque** → add/remove from both ends
* **ArrayDeque** → fast default implementation of `Deque`
* **LinkedList as Deque** → doubly-linked alternative with `List` + `Deque` behavior
* **PriorityQueue** → elements processed by priority, not insertion order

---

# 📊 Visual Overview

```text
                    ┌──────────────────────────┐
                    │   Special Data Structures │
                    └─────────────┬────────────┘
                                  │
                 ┌────────────────┴────────────────┐
                 │                                 │
              Deque                         PriorityQueue
        (double-ended queue)                 (heap-based)
                 │
         ┌───────┴────────┐
         │                │
     ArrayDeque      LinkedList
   (array-backed)   (node-backed)
```

```text
Deque can behave like both:

Stack (LIFO)                 Queue (FIFO)
Top                          Front              Rear
 │                            │                  │
 ▼                            ▼                  ▼
[30, 20, 10]              [10, 20, 30]   ->   poll from front
 push/pop                     offer/poll
```

```text
PriorityQueue (min-heap by default)

Inserted: 30, 10, 20, 5

Internal heap view:
        5
      /   \
     10    20
    /
   30

poll() -> 5
```

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20221013125430/DequeDataStructure.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20210722114026/ArrayDequeClass.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20220217170342/linkedlist.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230315103957/PriorityQueue.png)

---

# 1️⃣ Deque (Double-Ended Queue)

### 🔹 What

A **Deque** is a **double-ended queue** that supports insertion and removal from **both front and rear**.

### 🔹 Why

It is more flexible than a normal queue because it can behave as:

* **Stack (LIFO)** using `push/pop`
* **Queue (FIFO)** using `offer/poll`
* **Deque** using `addFirst/addLast/removeFirst/removeLast`

### 🔹 Where

* Browser history / undo-redo
* Sliding window algorithms
* BFS / DFS traversals
* Palindrome checking
* Task scheduling from both ends

### 🔹 How

```java
Deque<Integer> deque = new ArrayDeque<>();
deque.addFirst(10);
deque.addLast(20);
deque.addFirst(5);

System.out.println(deque);       // [5, 10, 20]
deque.removeFirst();             // 5
deque.removeLast();              // 20
```

### 🔹 Complexity

* Insert/remove at both ends → `O(1)`
* Peek at both ends → `O(1)`

### 🔹 Pros

* Extremely flexible
* Replaces both stack and queue APIs cleanly
* Great for end-based operations

### 🔹 Cons

* API has many similar methods
* No random indexed access like `List`

### 🔹 Pitfalls

* Mixing stack-style and queue-style methods can confuse logic
* `removeFirst/removeLast` throw exception if empty; `pollFirst/pollLast` return `null`

---

# 2️⃣ ArrayDeque

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20210722114026/ArrayDequeClass.png)

### 🔹 What

`ArrayDeque` is a **resizable-array implementation** of `Deque`.

### 🔹 Why

For most stack/queue use-cases, it is the **best default choice** because it is usually faster than `LinkedList`.

### 🔹 Where

* Stack replacement for legacy `Stack`
* Queue/deque processing
* Sliding window maximum/minimum
* Expression evaluation
* DFS/BFS helpers

### 🔹 How

```java
ArrayDeque<String> stack = new ArrayDeque<>();
stack.push("home");
stack.push("products");
stack.push("checkout");
System.out.println(stack.pop());   // checkout

ArrayDeque<String> queue = new ArrayDeque<>();
queue.offer("task-1");
queue.offer("task-2");
System.out.println(queue.poll());  // task-1
```

### 🔹 Complexity

| Operation        | Time       |
|-----------------|------------|
| `addFirst`      | `O(1)` amortized |
| `addLast`       | `O(1)` amortized |
| `pollFirst`     | `O(1)` |
| `pollLast`      | `O(1)` |
| `peekFirst`     | `O(1)` |
| `peekLast`      | `O(1)` |

### 🔹 Pros

* Fast in practice
* Better cache locality than `LinkedList`
* Lower memory overhead than linked nodes
* Perfect modern replacement for stack/queue needs

### 🔹 Cons

* Does **NOT** allow `null`
* Not thread-safe
* No indexed access

### 🔹 Pitfalls

* Do not use `null` as a special marker — `ArrayDeque` rejects it
* Prefer `ArrayDeque` over legacy `Stack`
* Not ideal when you specifically need `List` behavior

---

# 3️⃣ LinkedList as Deque

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20220217170342/linkedlist.png)

### 🔹 What

`LinkedList` is a **doubly-linked list** that also implements `Deque`.

```text
[prev|A|next] <-> [prev|B|next] <-> [prev|C|next]
```

### 🔹 Why

Useful when you want one class that can act as both:

* a `List`
* a `Deque`

### 🔹 Where

* Editor history
* Task lists with front/rear operations
* Cases where `Deque` operations and occasional index-based APIs both matter

### 🔹 How

```java
LinkedList<String> route = new LinkedList<>();
route.addFirst("A");
route.addLast("B");
route.addLast("C");

System.out.println(route.getFirst()); // A
System.out.println(route.getLast());  // C
route.removeFirst();
route.removeLast();
```

### 🔹 Complexity

| Operation              | Time |
|-----------------------|------|
| `addFirst/addLast`    | `O(1)` |
| `removeFirst/removeLast` | `O(1)` |
| `get(i)`              | `O(n)` |

### 🔹 Pros

* Supports both `List` and `Deque`
* Allows `null`
* Fast insert/remove at ends

### 🔹 Cons

* More memory overhead per element
* Worse cache locality than `ArrayDeque`
* Usually slower than `ArrayDeque` for pure deque usage

### 🔹 Pitfalls

* Beginners overuse `LinkedList` when `ArrayDeque` is usually faster
* `get(i)` inside loops becomes expensive on large lists
* Node allocation overhead can hurt performance

---

# 4️⃣ PriorityQueue

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230315103957/PriorityQueue.png)

### 🔹 What

A **heap-based queue** that returns elements by **priority**, not insertion order.

By default Java uses a **min-heap**:

* smallest element comes out first

You can also use:

* `Comparator.reverseOrder()` for a **max-heap**
* a custom comparator for domain-specific priority

### 🔹 Why

Use it when the **most important** element should be processed first.

### 🔹 Where

* CPU/job schedulers
* Leaderboards / top-k problems
* Emergency triage systems
* Dijkstra / A* / shortest-path algorithms
* Event prioritization

### 🔹 How

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(30);
minHeap.offer(10);
minHeap.offer(20);

System.out.println(minHeap.peek()); // 10
System.out.println(minHeap.poll()); // 10

PriorityQueue<Integer> maxHeap =
    new PriorityQueue<>(Collections.reverseOrder());

class Patient {
    String name;
    int severity;
}
PriorityQueue<Patient> hospitalQueue =
    new PriorityQueue<>((a, b) -> Integer.compare(b.severity, a.severity));
```

### 🔹 Complexity

| Operation | Time |
|----------|------|
| `offer`  | `O(log n)` |
| `poll`   | `O(log n)` |
| `peek`   | `O(1)` |

### 🔹 Pros

* Efficient priority-based retrieval
* Easy min-heap or max-heap construction
* Great for top-k / scheduling style problems

### 🔹 Cons

* Iteration order is **not sorted order**
* Not FIFO
* Not thread-safe

### 🔹 Pitfalls

* Do not assume printing the queue shows sorted order
* Custom objects need `Comparable` or a `Comparator`
* `null` elements are not allowed

---

# 🧠 Interview Cheat Sheet

| Use Case                          | Best Choice              |
|----------------------------------|--------------------------|
| Stack replacement                | `ArrayDeque`             |
| FIFO queue with deque flexibility| `ArrayDeque`             |
| Need `List` + `Deque` together   | `LinkedList`             |
| Priority-based task execution    | `PriorityQueue`          |
| Sliding window                   | `ArrayDeque`             |
| Emergency triage                 | `PriorityQueue`          |

---

# ⚖️ ArrayDeque vs LinkedList (as Deque)

| Feature                | ArrayDeque              | LinkedList             |
|------------------------|-------------------------|------------------------|
| Internal structure     | Resizable array         | Doubly linked nodes    |
| End operations         | `O(1)` amortized        | `O(1)`                 |
| Cache locality         | Better                  | Worse                  |
| Null allowed           | ❌ No                   | ✅ Yes                 |
| List operations        | ❌ No                   | ✅ Yes                 |
| Best default for deque | ✅ Yes                  | Usually no             |

---

# 🚀 Real-World Mapping

* ↩ Undo/Redo stack → `ArrayDeque`
* 🌐 Browser back/forward navigation → `Deque`
* 🚆 Train route with front/rear station updates → `LinkedList` as `Deque`
* 🏥 Hospital emergency queue → `PriorityQueue`
* 🧠 Sliding window algorithms → `ArrayDeque`
* 🏆 Leaderboard/top-k → `PriorityQueue`

---

# 🔥 Final Takeaways

* Start with **`ArrayDeque`** for most stack/queue/deque needs
* Use **`LinkedList` as Deque** only when you also need `List` features or `null` support
* Use **`PriorityQueue`** when processing order depends on priority, not arrival order
* `ArrayDeque` is usually a better stack replacement than legacy `Stack`
* `PriorityQueue` iteration is **not sorted** — use repeated `poll()` if you need sorted retrieval
* Prefer interfaces in code:

```java
Deque<Integer> deque = new ArrayDeque<>();
Queue<Integer> pq = new PriorityQueue<>();
```

---


