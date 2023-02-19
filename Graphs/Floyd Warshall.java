int n; // set
long[][] dist; // set to weighted adjacency matrix, i->i as 0

floydWarshall() {
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][k] != Long.MAX_VALUE && dist[k][j] != Long.MAX_VALUE)
                    dist[i][j] = min(dist[i][k], dist[k][j] + dist[i][j]);
            }
        }
    }
}
