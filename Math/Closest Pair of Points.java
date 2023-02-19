// UNTESTED since 2019

long ClosestPairOfPoints(Point p[]) {
	int n = p.length;
	if (n <= 1)
		return Long.MAX_VALUE;
	int mid = n >> 1;
	Point left[] = new Point[mid];
	for (int i = 0; i < mid; i++)
		left[i] = p[i];
	Point right[] = new Point[n - mid];
	for (int i = 0, j = mid; j < n; j++, i++)
		right[i] = p[j];
	long ans = Math.min(ClosestPairOfPoints(left), ClosestPairOfPoints(right));
	ArrayList<Point> strip = new ArrayList<Point>();
	for (int i = mid; i >= 0; i--) {
		if ((p[mid].x - p[i].x) * 1L * (p[mid].x - p[i].x) > ans)  break;
		strip.add(p[i]);
	}
	for (int i = mid + 1; i < n; i++) {
		if ((p[mid].x - p[i].x) * 1L * (p[mid].x - p[i].x) > ans)
			break;
		strip.add(p[i]);
	}
	Collections.sort(strip, new PointCompY());
	int siz = strip.size();
	for (int i = 0; i < siz; i++) {
		for (int j = i + 1; j < siz; j++) {
			long a = strip.get(i).y;
			long b = strip.get(j).y;
			if ((a - b) * (a - b) > ans)
				break;
			ans = Math.min(ans, sqEuclid(strip.get(i), strip.get(j)));
		}
	}
	return ans;
}

long sqEuclid(Point a, Point b) {
	return (a.x - b.x) * 1L * (a.x - b.x) + (a.y - b.y) * 1L * (a.y - b.y);
}

long area() { //returns 2 * area
	long ans = 0;
	for (int i = 1; i + 1 < n; i++)
		ans += p[i].minus(p[0]).cross(p[i + 1].minus(p[0]));
	return ans;
}

double cross(Point b) {
	return (double) x * b.y - (double) y * b.x;
}
