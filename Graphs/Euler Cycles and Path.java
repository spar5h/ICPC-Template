// UNTESTED since 2019

// If need to find euler path, add an edge from last node to first node find euler cycle
// now in the resultant list remove the added edge and take the remaining array circularly
List<Integer> eulerCycleUndirected(List<Integer>[] graph, int u) {
	Set<Long> usedEdges = new HashSet<>();
	int n = graph.length;
	int[] curEdge = new int[n];
	List<Integer> res = new ArrayList<>();
	dfs(graph, curEdge, usedEdges, res, u);
	Collections.reverse(res);
	return res;
}

void dfs(List<Integer>[] graph, int[] curEdge, Set<Long> usedEdges, List<Integer> res, int u) {
	while (curEdge[u] < graph[u].size()) {
		int v = graph[u].get(curEdge[u]++);
		if (usedEdges.add(((long) Math.min(u, v) << 32) + Math.max(u, v)))
			dfs(graph, curEdge, usedEdges, res, v);
	}
	res.add(u);
}

List<Integer> eulerCycleDirected(List<Integer>[] graph, int v) {
	int[] curEdge = new int[graph.length];
	List<Integer> res = new ArrayList<>();
	Stack<Integer> stack = new Stack<>();
	stack.add(v);
	while (!stack.isEmpty()) {
		v = stack.pop();
		while (curEdge[v] < graph[v].size()) {
			stack.push(v);
			v = graph[v].get(curEdge[v]++);
		}
		res.add(v);
	}
	Collections.reverse(res);
	return res;
}