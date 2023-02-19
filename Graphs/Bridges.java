int n; // set to number of nodes
ArrayList<Integer>[] adj; // set (indices of edges)
Edge[] edges; // set

boolean[] vis, is_bridge;
int[] tin, low;
int timer;

void dfs(int x, int p) {
    vis[x] = true;
    tin[x] = low[x] = timer++;
    for (int i : adj[x]) {
        int y = edges[i].other(x);
        if (y == p) continue;
        if (vis[y]) {
            low[x] = min(low[x], tin[y]);
        } else {
            dfs(y, x);
            low[x] = min(low[x], low[y]);
            if (low[y] > tin[x])
                is_bridge[i] = true;
        }
    }
}

// invoke
void find_bridges() {
    timer = 0;
    vis = new boolean[n];
    is_bridge = new boolean[edges.length];
    tin = new int[n];
    Arrays.fill(tin, -1);
    low = new int[n];
    Arrays.fill(low, -1);
    for (int i = 0; i < n; i++) {
        if (!vis[i])
            dfs(i, -1);
    }
}

class Edge {
    int u, v;

    Edge (int u, int v) {
        this.u = u;
        this.v = v;
    }

    int other(int x) {
        return (x == u) ? v : u;
    }
}