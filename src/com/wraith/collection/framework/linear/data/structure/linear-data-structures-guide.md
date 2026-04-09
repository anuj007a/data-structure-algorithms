---

# 📘 Linear Data Structures Guide (Java)

A complete guide to **Linear Data Structures** using
**Java Collections Framework**

---

# 📌 What are Linear Data Structures?

Data structures where elements are stored **sequentially (one after another)**.

👉 Each element has:

* One predecessor
* One successor

---

# 📊 Visual Overview

![Image](https://deen3evddmddt.cloudfront.net/uploads/content-images/difference-between-linear-and-non-linear-data-structures.webp)

![Image](https://www.masaischool.com/blog/content/images/2022/12/Array-Vs-Linked-List-01.png)

![Image](https://www.theknowledgeacademy.com/_files/images/Difference_between_Stack_and_Queue.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2AzKnDkJpL-4GQ36kzrDiODQ.png)

---

# 1️⃣ Array

### 🔹 What

Fixed-size collection stored in contiguous memory.

### 🔹 Why

Fastest access using index (`O(1)`).

### 🔹 Where

* Index-based systems
* Low-latency applications

### 🔹 How

```java
int[] arr = {10, 20, 30};
System.out.println(arr[0]);
```

### 🔹 Complexity

* Access → `O(1)`
* Insert/Delete → `O(n)`

### 🔹 Pros

* Fast access
* Memory efficient

### 🔹 Cons

* Fixed size
* Costly insert/delete

### 🔹 Pitfalls

* ArrayIndexOutOfBounds
* Wasted space if over-allocated

---

# 2️⃣ List (Interface)

## 🔸 ArrayList

![Image](https://ik.imagekit.io/upgrad1/abroad-images/imageCompo/images/__visualselection_2025_06_11T024144_208FWXN3.jpeg?pr-true=)

![Image](https://media.licdn.com/dms/image/v2/C4E12AQGzCpo2ltivtQ/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1630822712851?e=2147483647\&t=hX7RPTt1yHQwol6OmBPYvtXVofIVRK82z4kxuBMh9JM\&v=beta)

![Image](https://media2.dev.to/dynamic/image/width%3D1000%2Cheight%3D420%2Cfit%3Dcover%2Cgravity%3Dauto%2Cformat%3Dauto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2Fetwhe7vtv2s48uooi929.png)

### 🔹 What

Resizable array implementation.

### 🔹 Why

Fast read operations.

### 🔹 Where

* Product listing
* Search results

### 🔹 How

```java
List<Integer> list = new ArrayList<>();
list.add(10);
list.get(0);
```

### 🔹 Complexity

* Access → `O(1)`
* Insert/Delete → `O(n)`

### 🔹 Pros

* Fast access
* Easy to use

### 🔹 Cons

* Slow insert/delete (shifting)

### 🔹 Pitfalls

* Resizing overhead
* Not thread-safe

---

## 🔸 LinkedList

![Image](https://files.codingninjas.in/article_images/features-of-doubly-linked-list-0-1694106736.webp)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/0%2AwmyZIQ3yLwGrm9WA.gif)

![Image](https://www.w3schools.com/dsa/img_linkedlists_memory2_new.png)

### 🔹 What

Doubly linked list.

### 🔹 Why

Efficient insert/delete.

### 🔹 Where

* Queues
* Frequent updates

### 🔹 How

```java
List<Integer> list = new LinkedList<>();
list.add(10);
list.addFirst(5);
```

### 🔹 Complexity

* Access → `O(n)`
* Insert/Delete → `O(1)`

### 🔹 Pros

* Fast insert/delete
* Flexible

### 🔹 Cons

* Slow access
* Extra memory

### 🔹 Pitfalls

* Poor cache locality
* Traversal overhead

---

## 🔸 Vector (Legacy)

### 🔹 What

Thread-safe dynamic array.

### 🔹 Why

Built-in synchronization.

### 🔹 Where

* Legacy systems

### 🔹 How

```java
Vector<Integer> vector = new Vector<>();
vector.add(10);
```

### 🔹 Complexity

* Same as ArrayList

### 🔹 Pros

* Thread-safe

### 🔹 Cons

* Slow (synchronization overhead)

### 🔹 Pitfalls

* Rarely used in modern systems

---

# 3️⃣ Stack (Using Deque)

![Image](https://storage.googleapis.com/algodailyrandomassets/curriculum/stacks/guide-to-stacks-1.png)

![Image](https://codeahoy.com/img/640px-Lifo_stack.png)

![Image](https://s3.ap-south-1.amazonaws.com/s3.studytonight.com/tutorials/uploads/pictures/1627372174-103268.png)

### 🔹 What

LIFO (Last In First Out)

### 🔹 Why

Efficient push/pop operations.

### 🔹 Where

* Undo operations
* Recursion

### 🔹 How

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);
stack.pop();
```

### 🔹 Complexity

* Push/Pop → `O(1)`

### 🔹 Pros

* Simple
* Fast

### 🔹 Cons

* Limited access

### 🔹 Pitfalls

* Using legacy `Stack` instead of `Deque`

---

# 4️⃣ Queue

![Image](https://www.masaischool.com/blog/content/images/wordpress/2022/04/First-In-First-Out-Queue-1024x683.png)

![Image](https://www.callicoder.com/static/756cf2c68e5810a2f37f27ce1ce562a8/51aac/queue-data-structure.jpg)

![Image](https://www.smartwarehousing.com/hubfs/shutterstock_2322169049.jpg)

### 🔹 What

FIFO (First In First Out)

### 🔹 Why

Maintains order of processing.

### 🔹 Where

* Task scheduling
* Messaging systems

### 🔹 How

```java
Queue<Integer> queue = new LinkedList<>();
queue.offer(10);
queue.poll();
```

### 🔹 Complexity

* Insert/Delete → `O(1)`

### 🔹 Pros

* Maintains order
* Efficient

### 🔹 Cons

* Limited access

### 🔹 Pitfalls

* Using `add()` instead of `offer()` in bounded queues

---

# 5️⃣ PriorityQueue

![Image](https://he-s3.s3.amazonaws.com/media/uploads/2270b0f.jpg)

![Image](https://d33wubrfki0l68.cloudfront.net/0927054f3255230e75b6ecd1b5bba9ceb3e8d3e9/fee48/static/dc8fe7b4bba83ff881497f51b25951a2/51aac/priority-queue-data-structure.jpg)

![Image](https://upload.wikimedia.org/wikipedia/commons/c/c4/Max-Heap-new.svg)

### 🔹 What

Heap-based queue (priority order)

### 🔹 Why

Processes elements based on priority.

### 🔹 Where

* Schedulers
* Ranking systems

### 🔹 How

```java
Queue<Integer> pq = new PriorityQueue<>();
pq.offer(30);
pq.offer(10);
pq.poll(); // 10
```

### 🔹 Complexity

* Insert/Delete → `O(log n)`

### 🔹 Pros

* Automatic ordering

### 🔹 Cons

* No random access

### 🔹 Pitfalls

* Not strictly FIFO
* Comparator mistakes

---

# 6️⃣ Deque (Double-Ended Queue)

![Image](https://miro.medium.com/1%2AtuT-pn42vOOU2uNbyGiqzg.png)

![Image](https://media.licdn.com/dms/image/v2/D4E12AQENHI_uwPT9PA/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1710769479435?e=2147483647\&t=5M3xmDyczVwBfQTrVdKui4pOpt90ASxIm9NNNBigG5c\&v=beta)

![Image](https://www.hello-algo.com/en/chapter_stack_and_queue/deque.assets/deque_operations.png)

### 🔹 What

Insert/remove from both ends.

### 🔹 Why

Flexible (Stack + Queue)

### 🔹 Where

* LRU Cache
* Sliding window

### 🔹 How

```java
Deque<Integer> deque = new ArrayDeque<>();
deque.addFirst(10);
deque.addLast(20);
```

### 🔹 Complexity

* All operations → `O(1)`

### 🔹 Pros

* Flexible
* Fast

### 🔹 Cons

* Slightly complex API

### 🔹 Pitfalls

* Confusion between methods (`addFirst` vs `offerFirst`)

---

# 🧠 Interview Cheat Sheet

| Use Case               | Data Structure    |
| ---------------------- | ----------------- |
| Fast access            | Array / ArrayList |
| Frequent insert/delete | LinkedList        |
| LIFO                   | Deque             |
| FIFO                   | Queue             |
| Priority-based         | PriorityQueue     |
| Both ends ops          | Deque             |

---

# 🚀 Real-World Mapping

* 🛒 Cart → ArrayList
* 📦 Order Queue → Queue
* ↩ Undo → Stack
* ⏳ Scheduler → PriorityQueue
* 🔁 LRU Cache → Deque

---

# 🔥 Final Takeaways

* Prefer **ArrayList over Vector**
* Prefer **Deque over Stack**
* Use **PriorityQueue for scheduling**
* Use **LinkedList for frequent modifications**

---
