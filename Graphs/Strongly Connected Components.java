ArrayList<Integer>[] adj, adj_rev;
boolean[] used;
ArrayList<Integer> order, component;

void dfs1(int x) {
    used[x] = true;
    for (int y : adj[x]) {
        if (!used[y])
            dfs1(y);
    }
    order.add(x);
}

void dfs2(int x) {
    used[x] = true;
    component.add(x);

    for (int y : adj_rev[x]) {
        if (!used[y])
            dfs2(y);
    }
}

public void solve(FastReader s, PrintWriter w) {
    int n = s.nextInt();
    adj = new ArrayList[n];
    adj_rev = new ArrayList[n];
    for (int i = 0; i < n; i++) {
        adj[i] = new ArrayList<>();
        adj_rev[i] = new ArrayList<>();
    }

    int m = s.nextInt();
    for (int i = 0; i < m; i++) {
        int u = s.nextInt() - 1, v = s.nextInt() - 1;
        adj[u].add(v);
        adj_rev[v].add(u);
    }

    used = new boolean[n];
    component = new ArrayList<>();
    order = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        if (!used[i])
            dfs1(i);
    }

    Collections.reverse(order);
    Arrays.fill(used, false);
    for (int x : order) {
        if (!used[x]) {
            dfs2(x);

            // ... process component

            component.clear();
        }
    }
}