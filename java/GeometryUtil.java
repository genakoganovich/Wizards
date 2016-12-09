import model.LivingUnit;

class Point {
    double x;
    double y;
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
class Vector {
    double x;
    double y;
    Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Vector(Point beg, Point end) {
        x = end.x - beg.x;
        y = end.y - beg.y;
    }
    Vector(double angle) {
        x = StrictMath.cos(angle);
        y = StrictMath.sin(angle);
    }
    double length() { return StrictMath.hypot(x, y);}
    double norm() {return x * x + y * y;}
}
class Matrix3D {
    private static final int SIZE = 3;
    private double[][] m;
    private double[] buildRow(Point p) {return new double[]{p.x, p.y, 1};}

    Matrix3D(Point point, Point beg, Point end) {
        m = new double[SIZE][];
        m[0] = buildRow(point);
        m[1] = buildRow(beg);
        m[2] = buildRow(end);
    }
    double det() {
        return  m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) -
                m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0]) +
                m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
    }
}
class Line {
    private Point beg;
    private Point end;
    private Vector directionVector;
    private Line(Point beg, Point end) {
        this.beg = beg;
        this.end = end;
        directionVector = new Vector(beg, end);
    }
    private Line(Point beg, Vector directionVector) {
        this(beg, new Point(beg.x + directionVector.x, beg.y + directionVector.y));
    }
    Line(Point beg, double angle) {
        this(beg, new Vector(angle));
    }
    Vector getDirectionVector() { return directionVector;}
    Point getBeg() { return beg;}
    boolean contains(Point point) {
        return new Matrix3D(point, beg, end).det() == 0;
    }
}
class GeometryUtil {
    static Vector sum(Vector p, Vector q) { return new Vector(p.x + q.x, p.y + q.y);}
    static Vector scale(double scalar, Vector v) { return new Vector(scalar * v.x, scalar * v.y);}
    static Vector sub(Vector p, Vector q) { return sum(p, scale(-1, q));}
    static double dotProduct(Vector p, Vector q) { return p.x * q.x + p.y * q.y;}
    static Vector proj(Vector p, Vector q) { return scale(dotProduct(p, q) / q.norm(), q);}
    static Vector perp(Vector p, Vector q) { return sub(p, proj(p, q));}
    static double distance(Point point, Line line) {
        return perp(new Vector(line.getBeg(), point), line.getDirectionVector()).length();
    }
}
