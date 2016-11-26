import model.*;
import java.util.function.Function;

abstract class RouteBuilder implements Function<Integer, RoundPoint> {}
class RulerBuilder extends RouteBuilder {
    private double xStart;
    private double yStart;
    private double distance;
    private double angle;

    RulerBuilder(double xStart, double yStart, double distance, double angle) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.distance = distance;
        this.angle = angle;
    }
    private double getDeltaX(Integer i) {return i * distance * StrictMath.cos(angle);}
    private double getDeltaY(Integer i) {return i * distance * StrictMath.sin(angle);}
    @Override
    public RoundPoint apply(Integer i) {
        return new RoundPoint(xStart + getDeltaX(i), yStart + getDeltaY(i));
    }
}
class PolygonBuilder extends RouteBuilder {
    private LivingUnit livingUnit;
    private double radius;
    private double angle;

    PolygonBuilder(LivingUnit livingUnit, double radius, double angle) {
        this.livingUnit = livingUnit;
        this.radius = radius;
        this.angle = angle;
    }
    private double getDeltaX(Integer i) {return radius * StrictMath.cos((2 * i + 1) * angle);}
    private double getDeltaY(Integer i) {return radius * StrictMath.sin((2 * i + 1) * angle);}
    @Override
    public RoundPoint apply(Integer i) {
        return new RoundPoint(livingUnit.getX() + getDeltaX(i), livingUnit.getY() + getDeltaY(i));
    }
}
