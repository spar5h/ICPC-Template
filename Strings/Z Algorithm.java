// UNTESTED since 2019

// Z value (Z[i]) - length of longest common prefix of original string and suffix starting at ith index
// Z value is obtained in O(n) using  box where we maintain l and r 
// For string matching use P + $ + S and find all indices having value = length of P 
// Here, P is pattern and S is string where pattern is to be found
boolean contains(char[] string, char[] pattern) {
    return (findSubstrings(string, pattern) > 0);
}

int findSubstrings(char[] string, char[] pattern) {
    char[] s = new char[string.length + pattern.length + 1];
    for(int i = 0; i < pattern.length; ++i) // P + '$' + S
        s[i] = pattern[i];
    s[pattern.length] = '$';
    for(int i = 0; i < string.length; ++i)
        s[pattern.length + 1 + i] = string[i];
    z = findZValue(s);
    int ans = 0;
    for(int i = pattern.length + 1; i < s.length; ++i) {
        // having longest common prefix equal to length of pattern
        if(z[i] == pattern.length)
            ans++;
    }
    return ans;
}

int[] findZValue(char[] s) {
    int n = s.length;
    int[] z = new int[n];
    int l = 0, r = 0;
    for(int i = 1; i < n; ++i) { // will be neglecting first
        if(r < i) { // new range
            l = i;
            r = i - 1;
            while(r + 1 < n && s[r + 1] == s[r + 1 - l])
                r++;
            z[i] = r - l + 1;
        }
        else if(r >= i) {
            if(r - i + 1 <= z[i - l]) { //old range
                l = i; // can extend
                while(r + 1 < n && s[r + 1] == s[r + 1 - l])
                    r++;
                z[i] = r - l + 1;
            }
            else {
                z[i] = z[i - l]; // can't extend
            }
        }
    }
    return z; 
}