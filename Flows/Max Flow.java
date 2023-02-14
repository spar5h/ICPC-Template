int limit; // set to at least 3 * (max possible flow) + 100

List<Edge>[] createGraph(int nodes) {
    List<Edge>[] graph = new List[nodes];
    for (int i = 0; i < nodes; i++)
        graph[i] = new ArrayList<>();
    return graph;
}

void addEdge(List<Edge>[] graph, int s, int t, int cap) {
    graph[s].add(new Edge(t, graph[t].size(), cap));
    graph[t].add(new Edge(s, graph[s].size() - 1, 0));
}

boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
    Arrays.fill(dist, -1);
    dist[src] = 0;
    int[] Q = new int[graph.length];
    int sizeQ = 0;
    Q[sizeQ++] = src;
    for (int i = 0; i < sizeQ; i++) {
        int u = Q[i];
        for (Edge e : graph[u]) {
            if (dist[e.t] < 0 && e.f < e.cap) {
                dist[e.t] = dist[u] + 1;
                Q[sizeQ++] = e.t;
            }
        }
    }
    return dist[dest] >= 0;
}

int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
    if (u == dest)
        return f;
    for (; ptr[u] < graph[u].size(); ++ptr[u]) {
        Edge e = graph[u].get(ptr[u]);
        if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
            int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
            if (df > 0) {
                e.f += df;
                graph[e.t].get(e.rev).f -= df;
                return df;
            }
        }
    }
    return 0;
}

int maxFlow(List<Edge>[] graph, int src, int dest) {
    int flow = 0;
    int[] dist = new int[graph.length];
    while (flow < (limit + 1) / 2 && dinicBfs(graph, src, dest, dist)) {
        int[] ptr = new int[graph.length];
        while (flow < (limit + 1) / 2) {
            int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
            if (df == 0)
                break;
            flow += df;
        }
    }
    return flow;
}

class Edge {
    int t;
    int rev;
    int cap;
    int f;

    public Edge(int t, int rev, int cap) {
        this.t = t;
        this.rev = rev;
        this.cap = cap;
    }
}

// List<Edge>[] graph = createGraph(nodes_in_network);
// addEdge(graph, from, to, capacity);
// int flow = maxFlow(graph, src, snk);
            