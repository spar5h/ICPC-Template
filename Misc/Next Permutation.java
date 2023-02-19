boolean nextPermutation(int[] a) {
    int p = -1;
    for (int i = a.length - 2; i >= 0; i--) {
        if (a[i] < a[i + 1]) {
            p = i;
            break;
        }
    }

    if (p == -1)
        return false;

    int q = -1;
    for (int i = a.length - 1; i > p; i--) {
        if (a[i] > a[p]) {
            q = i;
            break;
        }
    }

    int temp = a[p];
    a[p] = a[q];
    a[q] = temp;

    for (int i = 0; i < (a.length - (p + 1)) / 2; i++) {
        temp = a[p + 1 + i];
        a[p + 1 + i] = a[a.length - i - 1];
        a[a.length - i - 1] = temp;
    }

    return true;
}