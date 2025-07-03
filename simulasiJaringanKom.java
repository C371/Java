import java.util.*;

public class simulasiJaringanKom {
    public static void main(String[] args) {
        Graph network = new Graph();

    network.addNode("Client");
    network.addNode("User Terminal");
    network.addNode("Router Alpha");
    network.addNode("Router Beta");
    network.addNode("Router Gamma");
    network.addNode("Router Delta");
    network.addNode("Main Server");
    network.addNode("Backup Server");
    network.addNode("Mirror Server");
    network.addNode("DNS Server");

    network.addEdge("Client", "Router Alpha");
    network.addEdge("Client", "Router Delta");
    network.addEdge("User Terminal", "Router Delta");

    network.addEdge("Router Alpha", "Router Beta");
    network.addEdge("Router Alpha", "Router Gamma");

    network.addEdge("Router Beta", "Main Server");

    network.addEdge("Router Gamma", "Mirror Server");
    network.addEdge("Router Gamma", "DNS Server");

    network.addEdge("Router Delta", "Backup Server");

    network.printGraph();

        System.out.println("\nMengambil semua koneksi router → server favorit:");

        List<String> routers = Arrays.asList(
            "Router Alpha", "Router Beta", "Router Gamma", "Router Delta"
        );

        Set<String> serverNames = new HashSet<>(Arrays.asList(
            "Main Server", "Backup Server", "Mirror Server", "DNS Server"
        ));

        List<ServerInfo> allServerConnections = new ArrayList<>();

        for (String routerName : routers) {
            GraphNode router = network.getNodeByName(routerName);
            if (router == null) continue;

            List<Edge> edges = network.adjacencyList.get(router);
            for (Edge edge : edges) {
                if (serverNames.contains(edge.target.name)) {
                    String label = edge.target.name + " (via " + routerName + ")";
                    allServerConnections.add(new ServerInfo(label, edge.weight));
                }
            }
        }

        // sebelum sort
        System.out.println("\nSebelum diurutkan:");
        for (ServerInfo s : allServerConnections) {
            System.out.println(s);
        }

        // sort berdasarkan latency
        List<ServerInfo> sorted = ServerSorter.mergeSort(allServerConnections);
        System.out.println("\nSetelah diurutkan (latency naik):");
        for (ServerInfo s : sorted) {
            System.out.println(s);
        }
    }
}

class GraphNode {
    String name;

    GraphNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraphNode) {
            return this.name.equals(((GraphNode) obj).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

class Edge {
    GraphNode target;
    int weight; // latency in ms

    Edge(GraphNode target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}

class Graph {
    Map<GraphNode, List<Edge>> adjacencyList = new HashMap<>();
    Random rand = new Random(12346); // seed untuk hasil acak konsisten

    public List<ServerInfo> getDirectServerLatencies(String fromNode) {
        List<ServerInfo> list = new ArrayList<>();
        GraphNode from = getNodeByName(fromNode);
        if (from == null) return list;

        for (Edge edge : adjacencyList.get(from)) {
            list.add(new ServerInfo(edge.target.name, edge.weight));
        }
        return list;
    }

    public void addNode(String name) {
        GraphNode node = new GraphNode(name);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        int weight = rand.nextInt(996) + 5; // 5-100 ms latency
        addEdge(from, to, weight);
    }

    public void addEdge(String from, String to, int weight) {
        GraphNode nodeFrom = getNodeByName(from);
        GraphNode nodeTo = getNodeByName(to);

        if (nodeFrom == null || nodeTo == null) {
            System.out.println("Node tidak ditemukan: " + from + " atau " + to);
            return;
        }

        adjacencyList.get(nodeFrom).add(new Edge(nodeTo, weight));
        adjacencyList.get(nodeTo).add(new Edge(nodeFrom, weight)); // bidirectional
    }

    public GraphNode getNodeByName(String name) {
        for (GraphNode node : adjacencyList.keySet()) {
            if (node.name.equals(name)) return node;
        }
        return null;
    }

    public void printGraph() {
        System.out.println("=== Struktur Jaringan (Graph) ===");
        for (GraphNode node : adjacencyList.keySet()) {
            System.out.print(node + " -> ");
            for (Edge edge : adjacencyList.get(node)) {
                System.out.print(edge.target + "(" + edge.weight + "ms), ");
            }
            System.out.println();
        }
    }
}

class ServerInfo {
    String name;
    int latency;

    ServerInfo(String name, int latency) {
        this.name = name;
        this.latency = latency;
    }

    @Override
    public String toString() {
        return name + " (" + latency + "ms)";
    }
}

class ServerSorter {

    public static List<ServerInfo> mergeSort(List<ServerInfo> servers) {
        if (servers.size() <= 1) return servers;

        int mid = servers.size() / 2;
        List<ServerInfo> left = mergeSort(servers.subList(0, mid));
        List<ServerInfo> right = mergeSort(servers.subList(mid, servers.size()));

        return merge(left, right);
    }

    private static List<ServerInfo> merge(List<ServerInfo> left, List<ServerInfo> right) {
        List<ServerInfo> result = new ArrayList<>();
        int i = 0, j = 0;

        // sort berdasarkan latency
        while (i < left.size() && j < right.size()) {
            if (left.get(i).latency <= right.get(j).latency) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    public static int binarySearchByName(List<ServerInfo> servers, String targetName) {
        int left = 0, right = servers.size() - 1;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = servers.get(mid).name.compareToIgnoreCase(targetName);
            
            if (cmp == 0) return mid;
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1; // tidak ditemukan
    }
}
