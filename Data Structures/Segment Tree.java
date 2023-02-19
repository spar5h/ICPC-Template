int n; // set
long[] init; // set to initialize values
long[] tree, lazy;

void pushLazy(int n, int nL, int nR) {
    tree[n] += lazy[n]; // change
    if (nL != nR) {
        lazy[2 * n + 1] += lazy[n]; // change
        lazy[2 * n + 2] += lazy[n]; // change
    }
    lazy[n] = 0;
}

void update(int l, int r, long val) {
    updateUtil(0, 0, n - 1, l, r, val);
}

void updateUtil(int n, int nL, int nR, int l, int r, long x) {
    pushLazy(n, nL, nR);
    if (nR < l || r < nL)
        return;
    if (l <= nL && nR <= r) {
        lazy[n] += x; // change
        pushLazy(n, nL, nR);
        return;
    }
    updateUtil(2 * n + 1, nL, (nL + nR) / 2, l, r, x);
    updateUtil(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, x);
    tree[n] = tree[2 * n + 1] + tree[2 * n + 2]; // change
}

long query(int l, int r) {
    return queryUtil(0, 0, n - 1, l, r);
}

long queryUtil(int n, int nL, int nR, int l, int r) {
    pushLazy(n, nL, nR);
    if (nR < l || r < nL)
        return 0;
    if (l <= nL && nR <= r) {
        return tree[n];
    }
    long ret = queryUtil(2 * n + 1, nL, (nL + nR) / 2, l, r) +
            queryUtil(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r); // change
    tree[n] = tree[2 * n + 1] +  tree[2 * n + 2]; // change
    return ret;
}

void build(int n, int nL, int nR) {
    if (nL == nR) {
        tree[n] = init[nL];
        return;
    }
    build(2 * n + 1, nL, (nL + nR) / 2);
    build(2 * n + 2, (nL + nR) / 2 + 1, nR);
}

// invoke
void preprocess() {
    tree = new long[4 * n];
    lazy = new long[4 * n];
    build(0, 0, n - 1); // skip if initial values 0
}

