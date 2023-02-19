// find number of ways to form a sentence given a dictionary of words

int[][] trie;
boolean[] is_word;
int next;

void insert(char[] c) {
    int curr = 0;
    for (int i = 0; i < c.length; i++) {
        int idx = c[i] - 'a';
        if (trie[curr][idx] == -1) {
            trie[curr][idx] = next;
            new_node();
        }
        curr = trie[curr][idx];
    }
    is_word[curr] = true;
}

void new_node() {
    trie[next] = new int[26];
    Arrays.fill(trie[next], -1);
    next++;
}

public void solve(FastReader s, PrintWriter w) {
    char[] a = s.next().toCharArray();
    int n = a.length;
    int k = s.nextInt();
    long mod = (long)1e9 + 7;

    trie = new int[(int)1e6][];
    next = 0;
    new_node();
    is_word = new boolean[(int)1e6];

    for (int i = 0; i < k; i++) {
        char[] c = s.next().toCharArray();
        insert(c);
    }

    long[] dp = new long[n + 1];
    dp[n] = 1;

    for (int l = n - 1; l >= 0; l--) {
        int curr = 0;
        for (int r = l; r < n; r++) {
            int idx = a[r] - 'a';
            if (trie[curr][idx] == -1)
                break;
            curr = trie[curr][idx];
            if (is_word[curr]) {
                dp[l] = (dp[l] + dp[r + 1]) % mod;
            }
        }
    }
    w.println(dp[0]);
}
