int[] rankedAt, rankOf, lcp;
// CHANGE datatype: find char change to int
void findLCP(char[] s) {
    int n = s.length;
    for(int i = 0; i < n; i++) 
        rankOf[rankedAt[i]] = i;
    lcp = new int[n - 1];
    int k = 0;
    for(int i = 0; i < n; i++) {
        if (rankOf[i] == n - 1)
            continue;
        int nextInd = rankedAt[rankOf[i] + 1];
        while (max(nextInd, i) + k < n && s[nextInd + k] == s[i + k])
            k++;
        lcp[rankOf[i]] = k;
        if (k > 0)
            k--;
    }
}

void findSuffixArray(char[] s) {
    int n = s.length;
    rankedAt = new int[n];
    rankOf = new int[n];
    // used integer for stable sorting so that similar characters with smaller length comes first
    Integer[] tempRankedAt = new Integer[n];
    for(int i = 0; i < n; i++)
        tempRankedAt[i] = n - 1 - i;
    Arrays.sort(tempRankedAt, new Comparator<Integer>() {
        public int compare(Integer i1, Integer i2) {
            if(s[i1] < s[i2])
                return -1;
            if(s[i1] > s[i2])
                return 1;
            return 0;
        }
    });
    for (int i = 0; i < n; i++) {
        rankedAt[i] = tempRankedAt[i];
        rankOf[i] = s[i] - 'a'; // CHANGE: if int or different character range
    }
    for (int len = 1; len < n; len *= 2) {
        int[] prevRankOf = rankOf.clone();
        for (int i = 0; i < n; i++) {
            if (i == 0)
                rankOf[rankedAt[i]] = i;
            else {
                // No need to check rankedAt[i] as their prev ranks are same so ofc the one at higher rank will have larger string
                if(rankedAt[i - 1] + len < n && prevRankOf[rankedAt[i]] == prevRankOf[rankedAt[i - 1]] && prevRankOf[rankedAt[i] + len / 2] == prevRankOf[rankedAt[i - 1] + len / 2])
                    rankOf[rankedAt[i]] = rankOf[rankedAt[i - 1]];
                else
                    rankOf[rankedAt[i]] = i;
            }
        }
        int[] curRank = new int[n];
        for (int i = 0; i < n; i++)
            curRank[i] = i;
        int[] prevRankedAt = rankedAt.clone();
        // radix sort
        for (int i = 0; i < n; i++) {
            int ind = prevRankedAt[i] - len;
            if(ind >= 0) 
                rankedAt[curRank[rankOf[ind]]++] = ind;
        } 	
    } 
}

// findSuffixArray(c);
