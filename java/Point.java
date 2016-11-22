
class Point {
    private double x;
    private double y;
    private double radius;
    Point(double x, double y, double radius) {this.x = x; this.y = y; this.radius = radius;}
    @Override
    public boolean equals(Object object) {
        return !(object == null || !(object instanceof Point)) && this.isNear((Point) object);
    }
    private boolean isNear(Point point) {
        return StrictMath.hypot(x - point.x, y - point.y) <= StrictMath.max(radius, point.radius);
    }
}
