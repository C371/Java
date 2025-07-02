public class Graph {

    public enum Representation {
        LIST, MATRIX
    }

    private final int numVertices;
    private final boolean isDirected;

    private int[][] adjMatrix;
    private int[][] adjList;
    private int[] adjListSize;

    public Graph(int numVertices, Representation initialRepresentation, boolean isDirected) {
        if (numVertices <= 0) throw new IllegalArgumentException("Jumlah simpul harus positif.");
        this.numVertices = numVertices;
        this.isDirected = isDirected;
        initializeAdjacencyMatrix();
        initializeAdjacencyList();
    }

    private void initializeAdjacencyMatrix() {
        adjMatrix = new int[numVertices][numVertices];
    }

    private void initializeAdjacencyList() {
        adjList = new int[numVertices][numVertices];
        adjListSize = new int[numVertices];
    }

    public void addEdge(int src, int dest) {
        if (src < 0 || dest < 0 || src >= numVertices || dest >= numVertices)
            throw new IllegalArgumentException("Simpul tidak valid.");

        // adjacency matrix
        adjMatrix[src][dest] = 1;
        if (!isDirected) adjMatrix[dest][src] = 1;

        // adjacency list
        adjList[src][adjListSize[src]++] = dest;
        if (!isDirected) {
            adjList[dest][adjListSize[dest]++] = src;
        }
    }

    public void BFS(int start) {
        boolean[] visited = new boolean[numVertices];
        int[] queue = new int[numVertices];
        int front = 0, rear = 0;

        visited[start] = true;
        queue[rear++] = start;

        System.out.print("BFS starting from vertex " + start + ": ");
        while (front < rear) {
            int u = queue[front++];
            System.out.print(u + " ");
            for (int v = 0; v < numVertices; v++) {
                if (adjMatrix[u][v] == 1 && !visited[v]) {
                    visited[v] = true;
                    queue[rear++] = v;
                }
            }
        }
        System.out.println();
    }

    public void DFS(int start) {
        boolean[] visited = new boolean[numVertices];
        System.out.print("DFS starting from vertex " + start + ": ");
        DFSUtil(start, visited);
        System.out.println();
    }

    private void DFSUtil(int u, boolean[] visited) {
        visited[u] = true;
        System.out.print(u + " ");
        for (int v = 0; v < numVertices; v++) {
            if (adjMatrix[u][v] == 1 && !visited[v]) {
                DFSUtil(v, visited);
            }
        }
    }

    public boolean hasCycleUndirected() {
        if (isDirected) throw new UnsupportedOperationException("Graf harus tidak berarah.");
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (hasCycleUndirectedUtil(i, visited, -1)) return true;
            }
        }
        return false;
    }

    private boolean hasCycleUndirectedUtil(int u, boolean[] visited, int parent) {
        visited[u] = true;
        for (int v = 0; v < numVertices; v++) {
            if (adjMatrix[u][v] == 1) {
                if (!visited[v]) {
                    if (hasCycleUndirectedUtil(v, visited, u)) return true;
                } else if (v != parent) return true;
            }
        }
        return false;
    }

    public boolean hasCycleDirected() {
        if (!isDirected) throw new UnsupportedOperationException("Graf harus berarah.");
        boolean[] visited = new boolean[numVertices];
        boolean[] recStack = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (hasCycleDirectedUtil(i, visited, recStack)) return true;
            }
        }
        return false;
    }

    private boolean hasCycleDirectedUtil(int u, boolean[] visited, boolean[] recStack) {
        visited[u] = true;
        recStack[u] = true;
        for (int v = 0; v < numVertices; v++) {
            if (adjMatrix[u][v] == 1) {
                if (!visited[v] && hasCycleDirectedUtil(v, visited, recStack)) return true;
                else if (recStack[v]) return true;
            }
        }
        recStack[u] = false;
        return false;
    }

    public void printGraph() {
        System.out.println("Representasi Adjacency List:");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("[" + i + "] -> ");
            if (adjListSize[i] == 0) {
                System.out.print("NULL");
            } else {
                for (int j = 0; j < adjListSize[i]; j++) {
                    System.out.print(adjList[i][j] + " -> ");
                }
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Representasi Adjacency Matrix:");
        System.out.print("   ");
        for (int i = 0; i < numVertices; i++) System.out.print(i + " ");
        System.out.println("\n---" + "-".repeat(numVertices * 2));
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + "| ");
            for (int j = 0; j < numVertices; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // main method
    public static void main(String[] args) {
        int numVertices = 8;

        System.out.println("--- DIRECTED GRAPH ---");
        Graph directedGraph = getDirectedGraph(numVertices);
        directedGraph.printGraph();
        directedGraph.BFS(0);
        directedGraph.DFS(0);
        System.out.println("Apakah graf directed memiliki siklus? " + directedGraph.hasCycleDirected());
        System.out.println();

        System.out.println("--- UNDIRECTED GRAPH ---");
        Graph undirectedGraph = getUndirectedGraph(numVertices);
        undirectedGraph.printGraph();
        undirectedGraph.BFS(0);
        undirectedGraph.DFS(0);
        System.out.println("Apakah graf undirected memiliki siklus? " + undirectedGraph.hasCycleUndirected());
    }

    private static Graph getDirectedGraph(int n) {
        Graph g = new Graph(n, Representation.LIST, true);
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 5);
        g.addEdge(3, 1);
        g.addEdge(3, 5);
        g.addEdge(4, 2);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(7, 5);
        return g;
    }

    private static Graph getUndirectedGraph(int n) {
        Graph g = new Graph(n, Representation.LIST, false);
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 5);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(5, 7);
        return g;
    }
}
