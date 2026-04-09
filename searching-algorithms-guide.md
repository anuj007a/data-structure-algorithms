# 🔎 Searching Algorithms Guide

---
## Linear Search
### 📌 What is Linear Search?
> **Linear Search** checks each element of an array or list sequentially until the target value is found.
It does NOT require sorting, and it works on any data type.

### 🎯 When to Use Linear Search
* The array or list is **unsorted**
* Dataset size is **small or moderate**
* **One-time** or **rare search** operations are performed
* Simplicity matters more than performance

Avoid Linear Search for large datasets with frequent lookups — use **Binary Search** or **HashMaps**.

### 🧠 Visual Explanation
#### ASCII Walkthrough (Searching for `7`)
```
Array:  [3] [5] [9] [1] [7] [4]
Index:   0   1   2   3   4   5

Step 0: pointer -> index 0 : 3 != 7
Step 1: pointer -> index 1 : 5 != 7
Step 2: pointer -> index 2 : 9 != 7
Step 3: pointer -> index 3 : 1 != 7
Step 4: pointer -> index 4 : 7 == 7  <--- FOUND at index 4
```
#### Mermaid Flow Diagram
```mermaid
flowchart TD
  A[Start at index 0]
  B{Is index > last index?}
  C{arr[i] == target?}
  D[Return index (Found)]
  E[i = i + 1]
  F[Return -1 (Not Found)]

  A --> B
  B -- No --> C
  B -- Yes --> F
  C -- Yes --> D
  C -- No --> E --> B
```

### ⏱️ Time & Space Complexity

| Case        | Time                            |
| ----------- | ------------------------------- |
| **Best**    | O(1) — target at first position |
| **Average** | O(n)                            |
| **Worst**   | O(n) — target not present       |
| **Space**   | O(1) — no extra memory          |

---

### 💻 Java Implementation (Clean & Simple)

#### Linear Search for `int[]`

```java
public class LinearSearch {

    public static int indexOf(int[] arr, int target) {
        if (arr == null) return -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }
}
```
### 🧪 Example Usage

```java
public class Demo {
    public static void main(String[] args) {
        int[] numbers = {4, 8, 15, 16, 23, 42};

        System.out.println(LinearSearch.indexOf(numbers, 15)); // Output: 2
        System.out.println(LinearSearch.indexOf(numbers, 7));  // Output: -1
    }
}
```
### 📝 Notes
* Linear Search is **easy** and **universal** — works on any array or list.
* Perfect for **learning**, **interviews**, and **small input sizes**.
* Does not require sorting or special structure.
---

