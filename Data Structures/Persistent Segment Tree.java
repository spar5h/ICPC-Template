class Node {
    Node left, right;
    int sum;

    Node(int value) {
        this.sum = value;
    }

    Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        if (left != null)
            sum += left.sum;
        if (right != null)
            sum += right.sum;
    }
}

Node build(int nL, int nR, int[] init) {
    if (nL == nR)
        return new Node(init[nL]);
    return new Node(build(nL, (nL + nR) >> 1, init), build(((nL + nR) >> 1) + 1, nR, init));
}

int query(Node n, int nL, int nR, int l, int r) {
    if (r < nL || nR < l)
        return 0;
    if (l <= nL && nR <= r)
        return n.sum;
    return query(n.left, nL, (nL + nR) >> 1, l, r) + query(n.right, ((nL + nR) >> 1) + 1, nR, l, r);
}

Node update(Node n, int nL, int nR, int idx, int v) {
    if (nL == nR)
        return new Node(v);
    int mid = (nL + nR) >> 1;
    if (idx <= mid)
        return new Node(update(n.left, nL, mid, idx, v), n.right);
    else
        return new Node(n.left, update(n.right, mid + 1, nR, idx, v));
}

// Node[] nodes = new Node[n];
// nodes[0] = build(0, n - 1, init);
// nodes[l] = update(nodes[l - 1], 0, n - 1, idx, v);
// int ans = query(nodes[l], 0, n - 1, l, r);
