// UNTESTED since 2019

int[] msp(String S) {
	int n = S.length();
	int p[] = getLongestPalindrome(S.toCharArray());
	int ans[] = new int[n];
	int last = 2 * n - 2;
	for (int i = n - 1; i >= 0; i--) {
		while (true) {
			int length = p[last + 2];
			int first = last - length + 1;
			first /= 2;
			if (first > i)
				last--;
			else {
				ans[i] = length - 2 * (i - first);
				break;
			}  }  }
	return ans;
}

/** function to preprocess string **/
char[] preProcess(char s[]) {
	int len = s.length;
	if (len == 0)
		return "^$".toCharArray();
	char ans[] = new char[2 * len + 3];
	ans[0] = '^';
	for (int i = 0; i < len; i++) {
		ans[2 * i + 1] = '#';
		ans[2 * i + 2] = s[i];
	}
	ans[ans.length - 2] = '#';
	ans[ans.length - 1] = '$';
	return ans;
}

// function to get largest palindromic substring
int[] getLongestPalindrome(char orig[]) {
	/** preprocess string **/
	char[] s = preProcess(orig);
	int N = s.length;
	int[] p = new int[N + 1];
	int id = 0, mx = 0;
	for (int i = 1; i < N - 1; i++) {
		p[i] = mx > i ? Math.min(p[2 * id - i], mx - i) : 0;
		while (s[i + 1 + p[i]] == s[i - 1 - p[i]])
			p[i]++;
		if (i + p[i] > mx) {
			mx = i + p[i];
			id = i;
		} 
	}
	return p;
}
