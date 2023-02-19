int n; // set to number of nodes
ArrayList<Integer>[] adj; // set

boolean[] vis, is_ap;
int[] tin, low;
int timer;

void dfs(int x, int p) {
	vis[x] = true;
	tin[x] = low[x] = timer++;
	int children = 0;
	for (int y : adj[x]) {
		if (y == p) continue;
		if (vis[y]) {
			low[x] = min(low[x], tin[y]);
		} else {
			dfs(y, x);
			low[x] = min(low[x], low[y]);
			if (low[y] >= tin[x] && p!=-1)
				is_ap[x] = true; // .. process
			++children;
		}
	}
	if (p == -1 && children > 1)
		is_ap[x] = true; // .. process
}

// invoke
void find_cutpoints() {
	timer = 0;
	vis = new boolean[n];
	is_ap = new boolean[n];
	tin = new int[n];
	Arrays.fill(tin, -1);
	low = new int[n];
	Arrays.fill(low, -1);
	for (int i = 0; i < n; i++) {
		if (!vis[i])
			dfs(i, -1);
	}
}