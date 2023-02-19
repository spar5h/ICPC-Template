void createLPS(char[] t, int[] lps) {
    lps[0] = -1;
    int j = 0;
    for(int i = 1; i < t.length; ++i) {
        while(j != 0 && t[i] != t[j])
            j = lps[j - 1] + 1;
        if(t[i] == t[j]) {
            lps[i] = j;
            j++;
        }
        else
            lps[i] = -1;
    }
}

// s: string, t: pattern
int findSubstrings(char[] s, char[] t) {
    int[] lps = new int[t.length];
    createLPS(t, lps);
    int j = 0, ans = 0;
    for(int i = 0; i < s.length; ++i) {
        while(j != 0 && s[i] != t[j])
            j = lps[j - 1] + 1;
        if(s[i] == t[j]) {
            j++;
            if(j == t.length) {
                j = lps[j - 1] + 1;
                ans++; //.. process
            }
        }
    }
    return ans;
}