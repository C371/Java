import java.util.*;

public class simulasiJaringanKom {

    static class GraphNode {
        String name;

        GraphNode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Edge {
        GraphNode target;
        int weight;

        Edge(GraphNode target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class Graph {
        private Map<GraphNode, List<Edge>> adjacencyList = new HashMap<>();
        private Random rand = new Random();

        public void addNode(String name) {
            adjacencyList.putIfAbsent(new GraphNode(name), new ArrayList<>());
        }

        public void addEdge(String from, String to) {
            GraphNode fromNode = getNodeByName(from);
            GraphNode toNode = getNodeByName(to);
            if (fromNode == null || toNode == null) return;

            int latency = rand.nextInt(91) + 10; // 10 - 100 ms
            adjacencyList.get(fromNode).add(new Edge(toNode, latency));
            adjacencyList.get(toNode).add(new Edge(fromNode, latency));
        }

        public GraphNode getNodeByName(String name) {
            for (GraphNode node : adjacencyList.keySet()) {
                if (node.name.equalsIgnoreCase(name)) return node;
            }
            return null;
        }

        public void printGraph() {
            System.out.println("=== Struktur Jaringan (Dengan Bobot) ===");
            for (GraphNode node : adjacencyList.keySet()) {
                System.out.print(node + " -> ");
                for (Edge edge : adjacencyList.get(node)) {
                    System.out.print(edge.target + "(" + edge.weight + "ms), ");
                }
                System.out.println();
            }
        }

        public void printGraphWithoutWeights() {
            System.out.println("=== Struktur Jaringan (Tanpa Bobot) ===");
            for (GraphNode node : adjacencyList.keySet()) {
                System.out.print(node + " -> ");
                for (Edge edge : adjacencyList.get(node)) {
                    System.out.print(edge.target + ", ");
                }
                System.out.println();
            }
        }

        public List<Edge> getEdges(GraphNode node) {
            return adjacencyList.get(node);
        }

        public Map<GraphNode, List<Edge>> getAdjacencyList() {
            return adjacencyList;
        }

        public void dijkstra(String startName, String endName) {
            GraphNode start = getNodeByName(startName);
            GraphNode end = getNodeByName(endName);

            if (start == null || end == null) {
                System.out.println("❌ Node tidak ditemukan.");
                return;
            }

            Map<GraphNode, Integer> distances = new HashMap<>();
            Map<GraphNode, GraphNode> previous = new HashMap<>();
            Set<GraphNode> visited = new HashSet<>();
            PriorityQueue<GraphNode> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

            for (GraphNode node : adjacencyList.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
            }
            distances.put(start, 0);
            queue.add(start);

            while (!queue.isEmpty()) {
                GraphNode current = queue.poll();
                if (!visited.add(current)) continue;

                for (Edge edge : adjacencyList.get(current)) {
                    GraphNode neighbor = edge.target;
                    if (visited.contains(neighbor)) continue;

                    int newDist = distances.get(current) + edge.weight;
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        previous.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }

    if (distances.get(end) == Integer.MAX_VALUE) {
        System.out.println("⚠️ Tidak ada jalur dari " + start + " ke " + end);
        return;
    }

    // Cetak jalur terpendek
    List<GraphNode> path = new ArrayList<>();
    for (GraphNode at = end; at != null; at = previous.get(at)) {
        path.add(at);
    }
    Collections.reverse(path);

    System.out.println("🚀 Jalur tercepat dari " + start + " ke " + end + ":");
    for (int i = 0; i < path.size(); i++) {
        System.out.print(path.get(i));
        if (i < path.size() - 1) System.out.print(" -> ");
    }
    System.out.println("\n🕒 Total latency: " + distances.get(end) + " ms");
}

    }

    static class ServerInfo {
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

    static class ServerSorter {
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

        public static List<ServerInfo> mergeSortByName(List<ServerInfo> servers) {
            if (servers.size() <= 1) return servers;

            int mid = servers.size() / 2;
            List<ServerInfo> left = mergeSortByName(servers.subList(0, mid));
            List<ServerInfo> right = mergeSortByName(servers.subList(mid, servers.size()));

            return mergeByName(left, right);
        }

        private static List<ServerInfo> mergeByName(List<ServerInfo> left, List<ServerInfo> right) {
            List<ServerInfo> result = new ArrayList<>();
            int i = 0, j = 0;

            while (i < left.size() && j < right.size()) {
                if (left.get(i).name.compareToIgnoreCase(right.get(j).name) <= 0) {
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
            return -1;
        }
    }

    public static void main(String[] args) {
        Graph network = new Graph();
        Scanner scanner = new Scanner(System.in);
        List<ServerInfo> serverList = new ArrayList<>();

        List<String> nodes = Arrays.asList(
            "Client", "User Terminal", "Router Alpha", "Router Beta", "Router Gamma", "Router Delta",
            "Main Server", "Backup Server", "Mirror Server", "DNS Server"
        );

        for (String node : nodes) network.addNode(node);

        network.addEdge("Client", "Router Alpha");
        network.addEdge("Client", "Router Delta");
        network.addEdge("User Terminal", "Router Delta");
        network.addEdge("Router Alpha", "Router Beta");
        network.addEdge("Router Alpha", "Router Gamma");
        network.addEdge("Router Beta", "Main Server");
        network.addEdge("Router Gamma", "Mirror Server");
        network.addEdge("Router Gamma", "DNS Server");
        network.addEdge("Router Delta", "Backup Server");

        String input;
        while (true) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tampilkan Struktur Jaringan (dengan Bobot)");
            System.out.println("2. Tampilkan Struktur Jaringan (tanpa Bobot)");
            System.out.println("3. Ambil Koneksi Server Favorit");
            System.out.println("4. Urutkan Server Berdasarkan Latency");
            System.out.println("5. Cari Server Favorit (Binary Search)");
            System.out.println("6. Cek Rute Tercepat (Dijkstra)");
            System.out.println("7. Keluar");
            System.out.print("Pilih (1–7 atau ketik 'keluar'): ");

            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("keluar") || input.equalsIgnoreCase("exit")) {
                System.out.println("Terima kasih! Program selesai.");
                break;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Input tidak valid. Masukkan angka 1–6 atau ketik 'keluar'.");
                continue;
            }

            switch (choice) {
                case 1:
                    network.printGraph();
                    break;
                case 2:
                    network.printGraphWithoutWeights();
                    break;
                case 3:
                    serverList = getAllRouterToServerConnections(network);
                    System.out.println("Server favorit (router → server langsung):");
                    for (ServerInfo s : serverList) System.out.println(" - " + s);
                    break;
                case 4:
                    List<ServerInfo> sortedByLatency = ServerSorter.mergeSort(serverList);
                    System.out.println("Setelah diurutkan berdasarkan latency:");
                    for (ServerInfo s : sortedByLatency) System.out.println(" - " + s);
                    break;
                case 5:
                    if (serverList.isEmpty()) {
                        System.out.println("⚠️  Harap ambil koneksi server favorit terlebih dahulu (menu 3).");
                        break;
                    }
                    List<ServerInfo> sortedForSearch = ServerSorter.mergeSortByName(serverList);
                    System.out.println("Daftar nama server favorit:");
                    for (ServerInfo s : sortedForSearch) System.out.println(" - " + s.name);
                    System.out.print("Masukkan nama lengkap server favorit: ");
                    String target = scanner.nextLine();
                    int idx = ServerSorter.binarySearchByName(sortedForSearch, target);
                    if (idx != -1) {
                        System.out.println("✅ Ditemukan: " + sortedForSearch.get(idx));
                    } else {
                        System.out.println("❌ Tidak ditemukan.");
                    }
                    break;
                case 6:
                    System.out.print("Masukkan nama node asal: ");
                    String asal = scanner.nextLine();
                    System.out.print("Masukkan nama node tujuan: ");
                    String tujuan = scanner.nextLine();
                    network.dijkstra(asal.trim(), tujuan.trim());
                    break;
                case 7:
                    System.out.println("👋 Terima kasih telah menggunakan program ini. Sampai jumpa");
                default:
                    System.out.println("❌ Pilihan tidak valid.");
            }
        }

        scanner.close();
    }

    public static List<ServerInfo> getAllRouterToServerConnections(Graph network) {
        List<String> routers = Arrays.asList(
            "Router Alpha", "Router Beta", "Router Gamma", "Router Delta"
        );

        Set<String> serverNames = new HashSet<>(Arrays.asList(
            "Main Server", "Backup Server", "Mirror Server", "DNS Server"
        ));

        List<ServerInfo> connections = new ArrayList<>();

        for (String routerName : routers) {
            GraphNode router = network.getNodeByName(routerName);
            if (router == null) continue;

            List<Edge> edges = network.getEdges(router);
            for (Edge edge : edges) {
                if (serverNames.contains(edge.target.name)) {
                    String label = edge.target.name + " (via " + routerName + ")";
                    connections.add(new ServerInfo(label, edge.weight));
                }
            }
        }

        return connections;
    }
}
