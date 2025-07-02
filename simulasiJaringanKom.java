import java.util.*;

class GraphNode {
    String name;

    GraphNode(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GraphNode) {
            return this.name.equals(((GraphNode) o).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Edge {
    GraphNode target;
    int weight; // latency (ms)

    Edge(GraphNode target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Graph {
    Map<GraphNode, List<Edge>> adjacencyList = new HashMap<>();
    Random rand = new Random(12345); // seed agar hasil random konsisten
    public void addNode(String name) {
        adjacencyList.putIfAbsent(new GraphNode(name), new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        int weight = rand.nextInt(96) + 5; // random latency dari 5–100 ms
        addEdge(from, to, weight);
    }

    public void addEdge(String from, String to, int weight) {
        GraphNode nodeFrom = getNodeByName(from);
        GraphNode nodeTo = getNodeByName(to);

        if (nodeFrom == null || nodeTo == null) return;

        adjacencyList.get(nodeFrom).add(new Edge(nodeTo, weight));
        adjacencyList.get(nodeTo).add(new Edge(nodeFrom, weight)); // undirected
    }

    private GraphNode getNodeByName(String name) {
        for (GraphNode node : adjacencyList.keySet()) {
            if (node.name.equals(name)) return node;
        }
        return null;
    }

    public void printGraph() {
        for (GraphNode node : adjacencyList.keySet()) {
            System.out.print(node + " -> ");
            for (Edge edge : adjacencyList.get(node)) {
                System.out.print(edge.target + "(" + edge.weight + "ms), ");
            }
            System.out.println();
        }
    }

    public void findShortestPath(String startName, String endName) {
        GraphNode start = getNodeByName(startName);
        GraphNode end = getNodeByName(endName);

        if (start == null || end == null) {
            System.out.println("Node tidak ditemukan.");
            return;
        }

        Map<GraphNode, Integer> distances = new HashMap<>();
        Map<GraphNode, GraphNode> prev = new HashMap<>();
        PriorityQueue<GraphNode> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (GraphNode node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            GraphNode current = pq.poll();

            for (Edge edge : adjacencyList.get(current)) {
                int newDist = distances.get(current) + edge.weight;
                if (newDist < distances.get(edge.target)) {
                    distances.put(edge.target, newDist);
                    prev.put(edge.target, current);
                    pq.add(edge.target);
                }
            }
        }

        if (!distances.containsKey(end) || distances.get(end) == Integer.MAX_VALUE) {
            System.out.println("Tidak ada jalur dari " + start + " ke " + end);
            return;
        }

        List<GraphNode> path = new ArrayList<>();
        for (GraphNode at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        System.out.println("Jalur tercepat dari " + start + " ke " + end + ":");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println(" (Total Latency: " + distances.get(end) + "ms)");
    }
}

public class simulasiJaringanKom {
    public static void main(String[] args) {
        Graph network = new Graph();

        network.addNode("ClientA");
        network.addNode("Router1");
        network.addNode("Router2");
        network.addNode("ServerZ");
        network.addNode("Router3");

        network.addEdge("ClientA", "Router1");
        network.addEdge("ClientA", "Router2");
        network.addEdge("Router1", "Router2");
        network.addEdge("Router2", "Router3");
        network.addEdge("Router3", "ServerZ");
        network.addEdge("Router1", "ServerZ");

        System.out.println("== Struktur Jaringan ==");
        network.printGraph();

        System.out.println("\n== Rute Tercepat ==");
        network.findShortestPath("ClientA", "ServerZ");
    }
}
