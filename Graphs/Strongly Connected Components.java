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

/* -- For Condensation Graph --  */
int[] roots;
ArrayList<Integer> root_nodes;
ArrayList<Integer>[] adj_scc;
/* ----------------------------  */

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

    /* -- For Condensation Graph --  */
    roots = new int[n];
    root_nodes = new ArrayList<>();
    adj_scc = new ArrayList[n];
    for (int i = 0; i < n; i++)
        adj_scc[i] = new ArrayList<>();
    /* ----------------------------  */

    for (int x : order) {
        if (!used[x]) {
            dfs2(x);

            // ... process component

            /* -- For Condensation Graph --  */
            int root = component.get(0);
            for (int y : component) roots[y] = root;
            root_nodes.add(root);
            /* ----------------------------  */

            component.clear();
        }
    }

    /* -- For Condensation Graph --  */
    for (int x = 0; x < n; x++) {
        for (int y : adj[x]) {
            int root_x = roots[x], root_y = roots[y];

            if (root_x != root_y)
                adj_scc[root_x].add(root_y);
        }
    }
    /* ----------------------------  */

}