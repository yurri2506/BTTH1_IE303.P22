import java.util.*;

public class bai3 {
  static class Point {
    int x, y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  public static int crossProduct(Point a, Point b, Point c) {
    return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
  }

  public static List<Point> findConvexHull(List<Point> points) {
    if (points.size() <= 1)
      return points;

    points.sort(Comparator.comparingInt((Point p) -> p.y).thenComparingInt(p -> p.x));
    points.sort((p1, p2) -> {
      int cross = crossProduct(points.get(0), p1, p2);
      if (cross == 0)
        return Integer.compare(Math.abs(p1.x - points.get(0).x) + Math.abs(p1.y - points.get(0).y),
            Math.abs(p2.x - points.get(0).x) + Math.abs(p2.y - points.get(0).y));
      return -Integer.compare(cross, 0);
    });

    Stack<Point> hull = new Stack<>();
    for (Point p : points) {
      while (hull.size() >= 2 && crossProduct(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p) <= 0) {
        hull.pop();
      }
      hull.push(p);
    }
    return new ArrayList<>(hull);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    List<Point> points = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      points.add(new Point(x, y));
    }

    List<Point> convexHull = findConvexHull(points);
    for (Point p : convexHull) {
      System.out.println(p.x + " " + p.y);
    }
  }
}
