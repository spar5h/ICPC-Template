 int n; // set
int[] parent;
int[] size;

int find_set(int v) {
	if (v == parent[v])
		return v;
	return parent[v] = find_set(parent[v]);
}

void union_sets(int a, int b) {
	a = find_set(a);
	b = find_set(b);
	if (a != b) {
		if (size[a] < size[b]) {
			int temp = a;
			a = b;
			b = temp;
		}
		parent[b] = a;
		size[a] += size[b];
	}
}

void make_set(int v) {
	parent[v] = v;
	size[v] = 1;
}

// invoke
void initialize() {
	parent = new int[n];
	size = new int[n];
	for (int i = 0; i < n; i++)
		make_set(i);
}
