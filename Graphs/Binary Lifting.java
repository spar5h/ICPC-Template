int n, l;
ArrayList<Integer>[] adj;

int timer;
int[] tin, tout;
int[][] up;

void dfs(int x, int p) {
    tin[x] = ++timer;
    up[x][0] = p;
    for (int i = 1; i <= l; i++)
        up[x][i] = up[up[x][i - 1]][i - 1];

    for (int y : adj[x]) {
        if (y != p)
            dfs(y, x);
    }

    tout[x] = ++timer;
}

boolean is_ancestor(int u, int v) {
    return tin[u] <= tin[v] && tout[u] >= tout[v];
}

int lca(int u, int v) {
    if (is_ancestor(u, v))
        return u;
    if (is_ancestor(v, u))
        return v;
    for (int i = l; i >= 0; i--) {
        if (!is_ancestor(up[u][i], v))
            u = up[u][i];
    }
    return up[u][0];
}

public void solve(FastReader s, PrintWriter w) {
    n = s.nextInt();
    l = (int)ceil(log(n) / log(2));
    
    adj = new ArrayList[n];
    for (int i = 0; i < n; i++)
        adj[i] = new ArrayList<>();
    /*  add edges ... */

    timer = 0;
    tin = new int[n];
    tout = new int[n];
    up = new int[n][l + 1];

    dfs(0, 0);
}