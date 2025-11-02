# 🧮 Assignment 4 — Directed Graph Algorithms (Tarjan SCC, Topological Sort, DAG Shortest Path)

### 📚 Course: Data Structures and Algorithms
### 🧑‍💻 Author: Nurtilek Koblandy
### 🗓️ Project: Implementation and Analysis of Graph Algorithms

---

## 🎯 Project Goal

The purpose of this assignment is to **implement and analyze three classical algorithms for directed graphs**:

- **Tarjan’s Algorithm** — to detect Strongly Connected Components (SCCs)
- **Topological Sort** — to find a valid linear ordering of vertices in a DAG
- **DAG Shortest Path** — to compute shortest paths in acyclic graphs

The project also includes:
- Automated **graph generation** in JSON format
- **Graph loading** and parsing using Google Gson
- **Algorithm performance analysis** (execution time & operations count)
- **CSV export** of all results
- **JUnit tests** for validation

---

## ⚙️ Technologies Used

| Tool | Purpose |
|------|----------|
| **Java 23** | Core implementation language |
| **Apache Maven** | Build automation & dependency management |
| **Google Gson** | JSON parsing and graph serialization |
| **JUnit 5** | Automated unit testing |
| **IntelliJ IDEA** | IDE for project development |

---

## 🧩 Project Structure
```
src/
├── main/
│ ├── java/
│ │ ├── org/example/
│ │ │ ├── algo/ Algorithms package
│ │ │ │ ├── dagsp/ DAG Shortest Path
│ │ │ │ ├── scc/ Tarjan SCC
│ │ │ │ └── topo/ Topological Sort
│ │ │ ├── data/ GraphLoader (JSON parser)
│ │ │ ├── generator/ DirectedGraphGenerator
│ │ │ ├── model/ Graph structure (DirectedGraph, Edge)
│ │ │ └── runner/ MainRunner
│ └── resources/ Graph JSON files
│ ├── small_graph.json
│ ├── medium_graph.json
│ └── large_graph.json
└── test/
└── java/org/example/GraphTest.java
```

---

## 🧠 Implemented Algorithms

### 🔹 **1. Tarjan's Strongly Connected Components**
- Detects groups of vertices where each vertex is reachable from every other vertex.
- Uses DFS traversal with low-link values.
- **Complexity:** O(V + E)

### 🔹 **2. Topological Sort**
- Produces a linear ordering of nodes in a Directed Acyclic Graph (DAG).
- Used as a prerequisite for the shortest path algorithm.
- **Complexity:** O(V + E)

### 🔹 **3. DAG Shortest Path**
- Finds the shortest path from a single source node in a DAG.
- Uses Topological Sort order for edge relaxation.
- **Complexity:** O(V + E)

---

## 🧱 Core Classes

| Class | Package | Description |
|-------|----------|-------------|
| `DirectedGraph` | `org.example.model` | Graph representation using adjacency lists |
| `Edge` | `org.example.model` | Stores edge details (u, v, w) |
| `GraphLoader` | `org.example.data` | Loads graph data from JSON files |
| `DirectedGraphGenerator` | `org.example.generator` | Generates random JSON graphs (small, medium, large) |
| `TarjanSCC` | `org.example.algo.scc` | Implementation of Tarjan SCC |
| `TopologicalSort` | `org.example.algo.topo` | Implementation of Topological Sort |
| `DAGShortestPath` | `org.example.algo.dagsp` | Implementation of shortest path in DAG |
| `Result` | `org.example.algo` | Stores algorithm performance results |
| `MainRunner` | `org.example.runner` | Executes all algorithms and saves results to CSV |

---

## 🧪 Testing and Verification

Unit tests are implemented in `GraphTest.java`.  
They verify:
- Graph loading from JSON
- Correct SCC detection
- Correct topological ordering
- Proper shortest path computation

To run all tests:
```bash
mvn test
```
```scss
✅ Tarjan SCC passed
✅ Topological Sort passed
✅ DAG Shortest Path passed
```
---

## ⚡ Running the Program

####  1. Generate Random Graphs
```bash
mvn exec:java -Dexec.mainClass="org.example.generator.DirectedGraphGenerator"
```
#### 2. Run All Algorithms
```bash
mvn exec:java -Dexec.mainClass="org.example.runner.MainRunner" 
```
#### 3. View Output

Console logs show progress for each algorithm.

Results saved in:
```
results.csv
```
### Sample CSV output:
```sass
Algorithm,Operations,Time(ms),NegativeCycle,Distances
Tarjan SCC,6,0.2,false,N/A
Topological Sort,6,0.03,false,N/A
DAG Shortest Path,10,0.4,false,[0,5,3,10,7,5]
```
---
# 📊 Performance Analysis
```
Dataset	Algorithm	Operations	Time (ms)	Comment
Small	Tarjan SCC	~6	0.2–0.4	Linear scaling
Medium	Tarjan SCC	~12	0.3–0.5	Stable
Large	Tarjan SCC	~25	0.6–0.8	Linear
Small	Topological Sort	~6	0.03	Instant
Medium	Topological Sort	~12	0.02	Minimal
Large	Topological Sort	~25	0.05	Scalable
Small	DAG Shortest Path	~10	0.4	Linear
Medium	DAG Shortest Path	~11	0.05	Stable
Large	DAG Shortest Path	~14	0.23	Slight growth
```
---
# 🧾 Conclusion

- All algorithms correctly implement their theoretical behavior.

- Performance scales linearly with graph size (O(V + E)).

- Random graphs are correctly generated and serialized.

- `results.csv` successfully captures all runtime metrics.

- Project demonstrates clean OOP design and modular structure.
---
# 💡 Future Improvements

- Add graph visualization using JavaFX or Graphviz.

- Include Dijkstra and Bellman–Ford for comparison.

- Add density control for random graph generation.

- Extend tests for extreme cases and disconnected graphs.

# 🧑‍💻 Author

### Nurtilek Koblandy

📘 Data Structures and Algorithms Course — 2025

🏫 SE-2423
