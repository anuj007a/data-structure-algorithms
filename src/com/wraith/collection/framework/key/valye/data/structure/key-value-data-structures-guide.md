---

# 🔑 Key-Value Data Structures Guide (Java)

A complete guide to **Map-based data structures** using
**Java Collections Framework**

---

# 📌 What are Key-Value Data Structures?

Data structures that store data in **(key → value) pairs**, where:

* Each key is **unique**
* Values can be duplicated
* Provides **fast lookup based on key**

---

# 📊 Visual Overview

![Image](https://media.licdn.com/dms/image/v2/C5612AQFx7LdXoXgQzQ/article-inline_image-shrink_1000_1488/article-inline_image-shrink_1000_1488/0/1599414993934?e=1775692800\&t=oyJ65KddAfiMzpjkwX4O96PcRxIuXTsw4Ec_2mNOI9k\&v=beta)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1012/1%2ABD8et6WM_o0XIqypGkf4HA.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2ARPA7uN8e2e-Ez2H7t02vRw.png)

![Image](https://miro.medium.com/1%2AP4I-yu0k1v6Tac23JOBW_Q.png)

---

# 1️⃣ HashMap

### 🔹 What

Hash-based implementation of Map (uses **hash table**).

### 🔹 Why

Provides **fast lookup, insert, delete → O(1)** (average).

### 🔹 Where

* Caching
* Lookup tables
* Indexing systems

### 🔹 How

```java id="xv3lti"
Map<String, Integer> map = new HashMap<>();

map.put("A", 1);
map.put("B", 2);

System.out.println(map.get("A"));
```

### 🔹 Complexity

* Get/Put/Remove → `O(1)` average
* Worst case → `O(log n)` (Java 8 treeification)

### 🔹 Pros

* Very fast
* Allows one null key
* Flexible

### 🔹 Cons

* No ordering
* Not thread-safe

### 🔹 Pitfalls

* Poor `hashCode()` / `equals()` → performance issues
* Collision handling misunderstood
* Iteration order unpredictable

---

# 2️⃣ LinkedHashMap

### 🔹 What

HashMap + **Doubly Linked List** (maintains order)

### 🔹 Why

Maintains **insertion order (or access order)**

### 🔹 Where

* LRU Cache
* Ordered maps
* Logging systems

### 🔹 How

```java id="mxrxp7"
Map<String, Integer> map = new LinkedHashMap<>();

map.put("A", 1);
map.put("B", 2);

System.out.println(map);
```

### 🔹 Complexity

* Same as HashMap → `O(1)` average

### 🔹 Pros

* Predictable iteration order
* Can be used for LRU cache

### 🔹 Cons

* Slightly more memory (linked list)

### 🔹 Pitfalls

* Forgetting access-order mode for LRU

---

## ⭐ LRU Cache Example

```java id="zv1a6g"
LinkedHashMap<Integer, Integer> lru =
    new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > 3;
        }
    };
```

---

# 3️⃣ TreeMap

### 🔹 What

Sorted Map using **Red-Black Tree**

### 🔹 Why

Maintains keys in **sorted order**

### 🔹 Where

* Leaderboards
* Range queries
* Sorted configs

### 🔹 How

```java id="sl48lp"
Map<Integer, String> map = new TreeMap<>();

map.put(3, "C");
map.put(1, "A");
map.put(2, "B");

System.out.println(map);
```

---

## ⭐ Custom Sorting

```java id="r5y6hg"
Map<Integer, String> map =
    new TreeMap<>(Comparator.reverseOrder());
```

---

### 🔹 Complexity

* Get/Put/Remove → `O(log n)`

### 🔹 Pros

* Sorted keys
* Navigation APIs (`firstKey`, `higherKey`)

### 🔹 Cons

* Slower than HashMap

### 🔹 Pitfalls

* No null key allowed
* Comparator mistakes can break ordering

---

# 🧠 Interview Cheat Sheet

| Use Case          | Best Choice   |
| ----------------- | ------------- |
| Fast lookup       | HashMap       |
| Ordered insertion | LinkedHashMap |
| LRU Cache         | LinkedHashMap |
| Sorted keys       | TreeMap       |
| Range queries     | TreeMap       |

---

# 🚀 Real-World Mapping

* 🧠 Cache → HashMap
* 🔁 LRU Cache → LinkedHashMap
* 🏆 Leaderboard → TreeMap
* 📊 Analytics aggregation → HashMap

---

# ⚡ Comparison Table

| Feature     | HashMap     | LinkedHashMap | TreeMap     |
| ----------- | ----------- | ------------- | ----------- |
| Order       | ❌ No        | ✅ Insertion   | ✅ Sorted    |
| Performance | O(1)        | O(1)          | O(log n)    |
| Null Key    | ✅ One       | ✅ One         | ❌ No        |
| Use Case    | Fast lookup | Ordered map   | Sorted data |

---

# 🔥 Final Takeaways

* Default choice → **HashMap**
* Need order → **LinkedHashMap**
* Need sorting → **TreeMap**
* LRU → **LinkedHashMap (access-order)**

---
