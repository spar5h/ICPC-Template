// UNTESTED since 2019

void findEulerTotient(int n) {
	int[] phi = new int[n + 1];
	for (int i = 1; i <= n; i++)
		phi[i] = i;
	for (int i = 1; i <= n; i++)
		if (prime[i])
			for (int j = i; j <= n; j += i)
				phi[j] -= phi[j] / i;
}

int dayOfWeek(int d, int m, int y) {
	int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
	y -= m < 3;
	return (y + y/4 - y/100 + y / 400 + t[m - 1] + d) % 7;
}

long Lucas(int n, int m, int p) {
	if (n == 0 && m == 0)
		return 1;
	int ni = n % p;
	int mi = m % p;
	if (mi > ni)
		return 0;
	return (Lucas(n / p, m / p, p) * C(ni, mi, p)) % p; //niCmi % p using factorials
}
