---

# 📘 Set Data Structures Guide (Java)

A complete guide to **Set Data Structures** using
**Java Collections Framework**

---

# 📌 What are Set Data Structures?

Data structures that store **unique elements only** — no duplicates allowed.

👉 Each implementation differs in:

* **Ordering guarantee** (none / insertion / sorted)
* **Performance** (O(1) hash-based vs O(log n) tree-based)
* **Null support**

---

# 📊 Visual Overview

![Image](https://www.scientecheasy.com/wp-content/uploads/2021/03/java-set-hierarchy.png)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230302151829/HashSet-in-java.png)

---

# 1️⃣ HashSet

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230302151829/HashSet-in-java.png)

### 🔹 What

Hash-table backed Set; stores **unique elements with no guaranteed order**.

### 🔹 Why

`O(1)` average add/remove/contains — fastest general-purpose uniqueness check.

### 🔹 Where

* Deduplication of input data
* Visited-node tracking in graph traversal
* Membership tests (is this tag/user in the set?)
* Removing duplicates from a stream/list

### 🔹 How

```java
Set<String> fruits = new HashSet<>();
fruits.add("apple");
fruits.add("apple");           // ignored – duplicate
fruits.contains("apple");      // true
fruits.remove("apple");

// Deduplication
Set<String> unique = new HashSet<>(Arrays.asList(words));

// Set operations
Set<Integer> union = new HashSet<>(setA);
union.addAll(setB);            // A ∪ B

Set<Integer> intersection = new HashSet<>(setA);
intersection.retainAll(setB);  // A ∩ B

Set<Integer> difference = new HashSet<>(setA);
difference.removeAll(setB);    // A - B
```

### 🔹 Complexity

| Operation  | Average | Worst |
|------------|---------|-------|
| `add`      | `O(1)`  | `O(n)`|
| `remove`   | `O(1)`  | `O(n)`|
| `contains` | `O(1)`  | `O(n)`|
| `size`     | `O(1)`  | `O(1)`|

> Worst case occurs on severe hash collisions.

### 🔹 Pros

* Fastest Set implementation
* `null` element allowed (one)
* Great for large-scale deduplication

### 🔹 Cons

* No ordering guarantee — iteration order is unpredictable
* Not thread-safe

### 🔹 Pitfalls

* Custom objects in HashSet **must** override `equals()` and `hashCode()`
* Not thread-safe — use `Collections.synchronizedSet()` or `CopyOnWriteArraySet`
* `add()` returns `false` (silently) for duplicates — check return value if needed

---

# 2️⃣ LinkedHashSet

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230301163812/LinkedHashSet-in-java.png)

### 🔹 What

Hash-table + doubly-linked list backed Set; stores **unique elements in insertion order**.

### 🔹 Why

`O(1)` average operations like HashSet, but iteration is always in **insertion order**.

### 🔹 Where

* Ordered deduplication (preserving first-seen sequence)
* Browser history / navigation history
* Maintaining unique ordered steps in a workflow
* Any use case needing "deduplicate but keep order"

### 🔹 How

```java
Set<String> history = new LinkedHashSet<>();
history.add("homepage");
history.add("products");
history.add("cart");
history.add("products");     // ignored – duplicate, position unchanged

System.out.println(history); // [homepage, products, cart]

// Convert to ordered List
List<String> orderedList = new ArrayList<>(history);
```

### 🔹 Complexity

| Operation  | Average | Worst |
|------------|---------|-------|
| `add`      | `O(1)`  | `O(n)`|
| `remove`   | `O(1)`  | `O(n)`|
| `contains` | `O(1)`  | `O(n)`|

> Slightly more memory than HashSet due to the linked list pointers.

### 🔹 Pros

* Predictable **insertion-order** iteration
* Same `O(1)` core operations as HashSet
* `null` element allowed (one)

### 🔹 Cons

* Slightly higher memory overhead than HashSet
* Still not sorted — do not confuse insertion-order with sorted order

### 🔹 Pitfalls

* Re-inserting an element after removal **appends it to the tail** (new position)
* Still requires correct `equals()` + `hashCode()` for custom objects
* Not thread-safe

---

# 3️⃣ TreeSet

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20230302153501/TreeSet-in-java.png)

### 🔹 What

Red-Black tree backed `NavigableSet`; elements always stored in **sorted order**.

### 🔹 Why

Need sorted unique elements or powerful range/navigation queries.

### 🔹 Where

* Auto-complete / dictionary word lookup
* Leaderboards (unique sorted scores)
* Range queries (find all values between X and Y)
* Event scheduling by timestamp
* Price brackets / bucketing

### 🔹 How

```java
TreeSet<String> cities = new TreeSet<>();
cities.add("Mumbai");
cities.add("Delhi");
cities.add("Bangalore");

// Navigation
cities.first();               // Bangalore (smallest)
cities.last();                // Mumbai (largest)
cities.floor("Delhi");        // Delhi (largest ≤ "Delhi")
cities.ceiling("Chennai");    // Chennai (smallest ≥ "Chennai")
cities.lower("Hyderabad");    // Delhi (strictly less than)
cities.higher("Bangalore");   // Chennai (strictly greater than)

// Range views
cities.headSet("Delhi");                  // elements < "Delhi"
cities.tailSet("Hyderabad");              // elements >= "Hyderabad"
cities.subSet("Bangalore", "Delhi");      // ["Bangalore", "Chennai"]

// Descending order
TreeSet<String> desc = new TreeSet<>(Collections.reverseOrder());

// Custom Comparator
TreeSet<String> ci = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
```

### 🔹 Complexity

| Operation               | Time       |
|-------------------------|------------|
| `add`                   | `O(log n)` |
| `remove`                | `O(log n)` |
| `contains`              | `O(log n)` |
| `first` / `last`        | `O(log n)` |
| `floor` / `ceiling`     | `O(log n)` |
| `subSet/headSet/tailSet`| `O(log n)` |

### 🔹 Pros

* Always **sorted** — no need to sort manually
* Rich navigation API (`floor`, `ceiling`, `higher`, `lower`)
* Range views (`subSet`, `headSet`, `tailSet`) are live views backed by the set

### 🔹 Cons

* Slower than HashSet — `O(log n)` vs `O(1)`
* Does **NOT** allow `null` elements

### 🔹 Pitfalls

* `null` element → `NullPointerException` at runtime
* Custom objects **must** implement `Comparable` or a `Comparator` must be supplied
* Range view methods use **inclusive/exclusive** bounds — read the docs carefully
* `pollFirst()` / `pollLast()` **remove** the element — do not use if you only want to peek

---

# 🧠 Interview Cheat Sheet

| Use Case                            | Best Choice                               |
|-------------------------------------|-------------------------------------------|
| Fastest uniqueness check            | `HashSet`                                 |
| Uniqueness + insertion order        | `LinkedHashSet`                           |
| Uniqueness + sorted order           | `TreeSet`                                 |
| Range / navigation queries          | `TreeSet`                                 |
| Deduplication (order not needed)    | `HashSet`                                 |
| Ordered deduplication               | `LinkedHashSet`                           |
| Thread-safe Set                     | `CopyOnWriteArraySet` / `synchronizedSet` |

---

# 🔁 Set Operations Quick Reference

```java
// Union  A ∪ B
Set<Integer> union = new HashSet<>(setA);
union.addAll(setB);

// Intersection  A ∩ B
Set<Integer> intersection = new HashSet<>(setA);
intersection.retainAll(setB);

// Difference  A − B
Set<Integer> difference = new HashSet<>(setA);
difference.removeAll(setB);

// Subset check
setA.containsAll(setB);   // true if B ⊆ A
```

---

# 🚀 Real-World Mapping

* 🔍 Search tag filter → `HashSet`
* 🕐 Browser history (ordered, no duplicates) → `LinkedHashSet`
* 🏆 Leaderboard (sorted unique scores) → `TreeSet`
* 🗓️ Event slots (sorted, no duplicates) → `TreeSet`
* 🧹 Remove duplicates from list → `HashSet` / `LinkedHashSet`

---

# 🔥 Final Takeaways

* Prefer **`HashSet`** when order doesn't matter — it's the fastest
* Prefer **`LinkedHashSet`** when you need insertion order with uniqueness
* Prefer **`TreeSet`** when elements must be sorted or range queries are needed
* Always **program to the `Set` interface**: `Set<E> set = new HashSet<>();`
* Always override **`equals()` + `hashCode()`** for custom objects in Hash-based Sets
* `HashSet` & `LinkedHashSet` allow **one null**; `TreeSet` does **NOT**
* Use **`addAll` / `retainAll` / `removeAll`** for set algebra (union/intersection/difference)

---


