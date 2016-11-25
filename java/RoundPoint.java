
class RoundPoint {
    double x;
    double y;
    private double radius;
    private static final double DEFAULT_RADIUS = 40;
    RoundPoint(double x, double y) {this(x, y, DEFAULT_RADIUS);}
    RoundPoint(double x, double y, double radius) {this.x = x; this.y = y; this.radius = radius;}
    @Override
    public boolean equals(Object object) {
        return !(object == null || !(object instanceof RoundPoint)) && this.isNear((RoundPoint) object);
    }
    private boolean isNear(RoundPoint roundPoint) {
        return StrictMath.hypot(x - roundPoint.x, y - roundPoint.y) <= StrictMath.max(radius, roundPoint.radius);
    }
}
