import java.awt.geom.AffineTransform;

class RoundPoint {
    double x;
    double y;
    private double radius;
    private static final double DEFAULT_RADIUS = 2;

    RoundPoint(double x, double y) {this(x, y, DEFAULT_RADIUS);}
    RoundPoint(double x, double y, double radius) {this.x = x; this.y = y; this.radius = radius;}
    @Override
    public boolean equals(Object object) {
        return !(object == null || !(object instanceof RoundPoint)) && this.isAt((RoundPoint) object);
    }
    private boolean isNear(RoundPoint roundPoint) {
        return StrictMath.hypot(x - roundPoint.x, y - roundPoint.y) <= StrictMath.max(radius, roundPoint.radius);
    }
    private boolean isAt(RoundPoint roundPoint) {
        return StrictMath.hypot(x - roundPoint.x, y - roundPoint.y) <= DEFAULT_RADIUS;
    }
    void transform(AffineTransform transform) {
        double[] values = new double[] {x, y};
        transform.transform(values, 0, values, 0, 1);
        x = values[0];
        y = values[1];
    }
}
