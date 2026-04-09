---

# 🌳 Non-Linear Data Structures Guide (Java)

A beginner-friendly guide to **Non-Linear Data Structures in Java**, focused on:

* **Tree (concept)**
* **TreeMap**
* **TreeSet**
* **Graph**

Built on ideas from the **Java Collections Framework** and common graph basics.

---

# 📌 What are Non-Linear Data Structures?

Non-linear data structures store elements in a **hierarchical** or **networked** form instead of a straight sequence.

👉 Unlike arrays or lists, one element can connect to **multiple other elements**.

That makes them ideal for:

* hierarchy
* relationships
* navigation
* dependency modelling
* sorted tree-based data

---

# 🧭 Covered Topics

* **Tree concept**
* **TreeMap** → sorted key-value data
* **TreeSet** → sorted unique values
* **Graph** → many-to-many relationships
* **BFS / DFS** traversal basics

---

# 📊 Visual Overview

```text
Linear                    Non-Linear
------                    ----------
A -> B -> C -> D          A
                          ├── B
                          └── C
                              ├── D
                              └── E
```

```text
Tree (hierarchy)

        Root
       /    \
   Child1  Child2
    /   \      \
  N1    N2     N3
```

```text
Graph (network)

   A ----- B
   | \     |
   |  \    |
   C ----- D
```

![Image](https://miro.medium.com/0%2AD8vzN1xS61oaQMQZ.jpg)

![Image](https://deen3evddmddt.cloudfront.net/uploads/content-images/binary-tree-in-dsa.webp)

![Image](https://mathinsight.org/media/image/image/small_undirected_network_labeled.png)

---

# 1️⃣ Tree (Concept)

### 🔹 What

A **tree** is a hierarchical structure made of:

* a **root** node
* **child** nodes
* **parent-child** relationships

Each child can further have its own children.

### 🔹 Why

Trees naturally model hierarchical data and support efficient search in balanced forms.

### 🔹 Where

* File systems
* Category trees
* Organization charts
* Database indexes
* Search/autocomplete systems

### 🔹 How

```java
class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {
        this.value = value;
    }
}

Node root = new Node(10);
root.left = new Node(5);
root.right = new Node(20);
root.left.left = new Node(3);
root.left.right = new Node(7);
```

### 🔹 Visual Example

```text
        10
       /  \
      5    20
     / \
    3   7
```

### 🔹 Complexity

* Traversal → `O(n)`
* Search in balanced BST-style tree → about `O(log n)`
* Search in badly unbalanced tree → `O(n)`

### 🔹 Pros

* Natural representation for hierarchy
* Recursive structure is elegant for many problems
* Balanced trees support efficient search

### 🔹 Cons

* Can become unbalanced
* More complex than arrays/lists for beginners

### 🔹 Pitfalls

* Confusing tree traversal orders (`preorder`, `inorder`, `postorder`)
* Assuming all trees are balanced

---

# 2️⃣ TreeMap

![Image](https://deen3evddmddt.cloudfront.net/uploads/content-images/red-black-tree.webp)

### 🔹 What

`TreeMap` is a `Map` implementation backed by a **Red-Black Tree**.

It stores key-value pairs in **sorted key order**.

### 🔹 Why

Use it when you want:

* sorted keys
* range queries
* navigation APIs like `firstKey()`, `higherKey()`, `ceilingKey()`

### 🔹 Where

* Leaderboards
* Sorted configuration values
* Price/range lookups
* Time-ordered processing

### 🔹 How

```java
Map<Integer, String> rankMap = new TreeMap<>();
rankMap.put(3, "Charlie");
rankMap.put(1, "Alice");
rankMap.put(2, "Bob");

System.out.println(rankMap);      // {1=Alice, 2=Bob, 3=Charlie}
System.out.println(rankMap.firstKey());
System.out.println(rankMap.higherKey(2));
```

### 🔹 Complexity

* `put/get/remove` → `O(log n)`
* navigation operations → `O(log n)`

### 🔹 Pros

* Keys remain automatically sorted
* Rich navigation API
* Great for ordered/range-based map use-cases

### 🔹 Cons

* Slower than `HashMap` for plain lookup
* Does not allow `null` keys

### 🔹 Pitfalls

* Keys must be `Comparable` or you must supply a `Comparator`
* Do not use when ordering is not needed — `HashMap` is usually faster

---

# 3️⃣ TreeSet

![Image](https://cdn.programiz.com/sites/tutorial2program/files/java-sortedset.png)

### 🔹 What

`TreeSet` is a `Set` implementation backed by a **Red-Black Tree**.

It keeps elements:

* **sorted**
* **unique**

### 🔹 Why

Use it when you need both:

* no duplicates
* sorted order

### 🔹 Where

* Ranking systems
* Sorted unique IDs
* Auto-complete dictionaries
* Score bands / range queries

### 🔹 How

```java
Set<Integer> scoreSet = new TreeSet<>();
scoreSet.add(30);
scoreSet.add(10);
scoreSet.add(20);
scoreSet.add(10); // duplicate ignored

System.out.println(scoreSet);     // [10, 20, 30]
```

### 🔹 Complexity

* `add/remove/contains` → `O(log n)`

### 🔹 Pros

* Automatic sorting
* No duplicates
* Useful navigation/range operations

### 🔹 Cons

* Slower than `HashSet`
* No `null` elements allowed

### 🔹 Pitfalls

* Duplicates are silently ignored
* Ordering depends on natural order or supplied comparator

---

# 4️⃣ Graph

![Image](https://storage.googleapis.com/algodailyrandomassets/curriculum/graphs/implementing-graphs-adjacencylist.png)

### 🔹 What

A **graph** is a collection of:

* **vertices** (nodes)
* **edges** (connections)

Graphs are used when relationships are **many-to-many**, not strictly hierarchical.

### 🔹 Why

Graphs model real-world connected systems extremely well.

### 🔹 Where

* Social networks
* Maps and route planning
* Recommendation systems
* Dependency graphs
* Network topology

### 🔹 How

A common Java representation is an **adjacency list**:

```java
Map<Integer, List<Integer>> graph = new HashMap<>();

graph.put(1, Arrays.asList(2, 3));
graph.put(2, Arrays.asList(1, 4));
graph.put(3, Arrays.asList(1, 4));
graph.put(4, Arrays.asList(2, 3, 5));
```

### 🔹 Visual Example

```text
1 -- 2
|    |
3 -- 4 -- 5
```

### 🔹 Complexity

* Adjacency list storage → `O(V + E)`
* BFS / DFS traversal → `O(V + E)`

### 🔹 Pros

* Very expressive for networked relationships
* Scales well using adjacency lists
* Supports traversal, path-finding, dependency analysis

### 🔹 Cons

* Harder to understand than linear structures
* Cycles can make traversal tricky

### 🔹 Pitfalls

* Forgetting the `visited` set causes repeated traversal or infinite loops
* Graph is **not directly part of JCF** — usually custom-built using `Map`, `List`, and `Set`

---

# 🔁 Graph Traversal

## 🔸 BFS (Breadth First Search)

Use BFS when you want to explore **level by level**.

```java
Queue<Integer> queue = new ArrayDeque<>();
Set<Integer> visited = new HashSet<>();

queue.offer(1);
visited.add(1);

while (!queue.isEmpty()) {
    int node = queue.poll();
    System.out.println(node);

    for (int neighbor : graph.getOrDefault(node, List.of())) {
        if (visited.add(neighbor)) {
            queue.offer(neighbor);
        }
    }
}
```

### BFS Visual

```text
Start from 1
Level order: 1 -> 2,3 -> 4 -> 5
```

---

## 🔸 DFS (Depth First Search)

Use DFS when you want to go **deep first**, then backtrack.

```java
void dfs(int node, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
    if (visited.contains(node)) return;

    visited.add(node);
    System.out.println(node);

    for (int neighbor : graph.getOrDefault(node, List.of())) {
        dfs(neighbor, graph, visited);
    }
}
```

### DFS Visual

```text
One possible DFS from 1:
1 -> 2 -> 4 -> 3 -> 5
```

---

# ⚡ Time Complexity Summary

| Structure / Algorithm | Operation            | Complexity |
|-----------------------|----------------------|------------|
| Tree traversal        | Visit all nodes      | `O(n)`     |
| TreeMap               | Insert/Search/Delete | `O(log n)` |
| TreeSet               | Insert/Search/Delete | `O(log n)` |
| Graph (Adjacency List)| Store graph          | `O(V + E)` |
| BFS / DFS             | Traversal            | `O(V + E)` |

---

# 🧠 Interview Cheat Sheet

| Use Case               | Best Choice |
|------------------------|-------------|
| Sorted key-value data  | `TreeMap`   |
| Sorted unique values   | `TreeSet`   |
| Hierarchical data      | Tree        |
| Network relationships  | Graph       |
| Level-order traversal  | BFS         |
| Backtracking traversal | DFS         |

---

# 🚀 Real-World Mapping

* 🏆 Leaderboard → `TreeMap`
* 🔢 Sorted unique IDs → `TreeSet`
* 📂 File system → Tree
* 🗺️ Maps / routes → Graph
* 👥 Social network → Graph
* 📦 Package dependencies → Graph

---

# 🔥 Final Takeaways

* `TreeMap` and `TreeSet` are tree-based structures built on **Red-Black Tree**
* They give **sorted order**, but usually cost `O(log n)`
* Graphs are not a single built-in JCF structure — they are usually built from `Map + List + Set`
* Use **BFS** for level-order exploration
* Use **DFS** for depth-first exploration and recursive problems
* Always keep a **visited set** while traversing graphs

---

