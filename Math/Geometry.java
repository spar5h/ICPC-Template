// UNTESTED since 2019

double fastHypot(double x, double y) {
	return Math.sqrt(x * x + y * y);
}

//given three colinear points p, q, r, 
//checks if point q lies on line segment 'pr'
boolean onSegment(Point p, Point q, Point r) {
	if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) && q.y <= max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
		return true;
	return false;
}

//finds orientation of ordered triplet (p, q, r)
//0 -> collinear, 1-> clockwise, 2-> counter-clockwise
int orientation(Point p, Point q, Point r) {
	long val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
	if (val == 0)
		return 0; 
	return (val > 0) ? 1 : 2; 
}

// checks if segment 'p1q1'& 'p2q2' intersect
boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
	int o1 = orientation(p1, q1, p2);
	int o2 = orientation(p1, q1, q2);
	int o3 = orientation(p2, q2, p1);
	int o4 = orientation(p2, q2, q1);
	// General case
	if (o1 != o2 && o3 != o4)
		return true;
	// Special Cases
	// p1, q1 and p2 are colinear and p2 lies on segment p1q1
	if (o1 == 0 && onSegment(p1, p2, q1))
		return true;
	// p1, q1 and p2 are colinear and q2 lies on segment p1q1
	 if (o2 == 0 && onSegment(p1, q2, q1))
		return true;
	// p2, q2 and p1 are colinear and p1 lies on segment p2q2
	if (o3 == 0 && onSegment(p2, p1, q2))
		return true;
	// p2, q2 and q1 are colinear and q1 lies on segment p2q2
	if (o4 == 0 && onSegment(p2, q1, q2))
		return true;
	return false; // Doesn't fall in any of the above cases
}

double circleIntersectionArea(Circle c1, Circle c2) {
	double r = Math.min(c1.r, c2.r);
	double R = Math.max(c1.r, c2.r);
	double d = fastHypot(c1.x - c2.x, c1.y - c2.y); //?
	if (d < R - r + EPS)
		return Math.PI * r * r;
	if (d > R + r - EPS)
		return 0;
	double area = r * r * Math.acos((d * d + r * r - R * R) / 2 / d / r) + R * R * Math.acos((d * d + R * R - r * r) / 2 / d / R) - 0.5* Math.sqrt((-d + r + R) * (d + r - R) * (d- r + R) * (d + r + R));
	return area;
}

Line[] tangents(Circle a, Circle b) {
	List<Line> lines = new ArrayList<>();
	for (int i = -1; i <= 1; i += 2)
		for (int j = -1; j <= 1; j += 2)
			tangents(new Point(b.x - a.x, b.y - a.y), a.r * i, b.r * j, lines);
	for (Line line : lines)
		line.c -= line.a * a.x + line.b * a.y;	
	return lines.toArray(new Line[lines.size()]); //change
}

void tangents(Point center2, double r1, double r2, List<Line> lines) {
	double r = r2 - r1;
	double z = center2.x * center2.x + center2.y * center2.y;
	double d = z - r * r;
	if (d < -EPS)
		return;
	d = Math.sqrt(d < 0 ? 0 : d);
	lines.add(new Line((center2.x * r + center2.y * d) / z, (center2.y * r - center2.x * d) / z, r1));
}

// min enclosing circle in O(n) on average
Circle minEnclosingCircle(Point[] pointsArray) {
	if (pointsArray.length == 0)
		return new Circle(0, 0, 0);
	if (pointsArray.length == 1)
		return new Circle(pointsArray[0].x, pointsArray[0].y, 0);
	List<Point> points = Arrays.asList(pointsArray);
	Collections.shuffle(points);
	Circle circle = getCircumCircle(points.get(0), points.get(1));
	for (int i = 2; i < points.size(); i++)
		if (!circle.contains(points.get(i)))
			circle = minEnclosingCircleWith1Point(points.subList(0, i), points.get(i));
	return circle;
}

Circle minEnclosingCircleWith1Point(List<Point> points, Point q) {
	Circle circle = getCircumCircle(points.get(0), q);
	for (int i = 1; i < points.size(); i++)
		if (!circle.contains(points.get(i)))
			circle = minEnclosingCircleWith2Points(points.subList(0, i), points.get(i), q);
	return circle;
}

Circle minEnclosingCircleWith2Points(List<Point> points, Point q1, Point q2) {
	Circle circle = getCircumCircle(q1, q2);
	for (Point point : points)
		if (!circle.contains(point))
			circle = getCircumCircle(q1, q2, point);
	return circle;
}

Point[] circleLineIntersection(Circle circle, Line line) {
	double a = line.a;
	double b = line.b;
	double c = line.c + circle.x * a + circle.y * b;
	double r = circle.r;
	double aabb = a * a + b * b;
	double d = c * c / aabb - r * r;
	if (d > EPS)
		return new Point[0];
	double x0 = -a * c / aabb;
	double y0 = -b * c / aabb;
	if (d > -EPS)
		return new Point[]{new Point(x0 + circle.x, y0 + circle.y)};
	d /= -aabb;
	double k = Math.sqrt(d < 0 ? 0 : d);
	return new Point[]{
		new Point(x0 + k * b + circle.x, y0 - k * a + circle.y),
		new Point(x0 - k * b + circle.x, y0 + k * a + circle.y) };
}

class Point {
	double x, y;
	Point (double x, double y) {
		this.x = x;
		this.y = y;
	}
}

class Circle {
	double x, y, r;
	Circle (double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
}
