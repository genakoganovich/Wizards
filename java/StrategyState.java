import model.*;
import java.util.Random;
import java.util.logging.Logger;
import static java.lang.StrictMath.PI;

class StrategyState {
    Random random;
    Wizard self;
    World world;
    Game game;
    Move move;
    private static final double RADIUS_DELTA = 10D;
    static final double TARGET_DELTA = 100D;
    static final Logger LOGGER = Logger.getLogger("MyStrategy");
    static final int STUCK_TICKS = 30;
    /**
     * Сохраняем все входные данные в полях класса для упрощения доступа к ним.
     */
    final void initializeTick(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
        if (random == null) {
            random = new Random(game.getRandomSeed());
        }
    }
    LivingUnit getNearestLivingUnit(LivingUnit[] livingUnits) {
        LivingUnit nearestLivingUnit = null;
        double nearestTargetDistance = Double.MAX_VALUE;
        for (LivingUnit unit : livingUnits) {
            double distance = self.getDistanceTo(unit);
            if (distance < nearestTargetDistance) {
                nearestLivingUnit = unit;
                nearestTargetDistance = distance;
            }
        }
        return nearestLivingUnit;
    }
    protected void goTo(double x, double y) {
        double angle = self.getAngleTo(x, y);
        move.setTurn(angle);
        if (StrictMath.abs(angle) < game.getStaffSector() / 4.0D) {
            move.setSpeed(game.getWizardForwardSpeed());
        }
    }
    boolean isNear(LivingUnit unit) {
        return self.getDistanceTo(unit) <= self.getRadius() + unit.getRadius() + RADIUS_DELTA;
    }
    void dance() {
        move.setTurn(PI / 32);
    }
    boolean isGotStuck() {
        return world.getTickIndex() > STUCK_TICKS && self.getSpeedX() == 0 && self.getSpeedY() == 0;
    }
    void strafe() {
        move.setStrafeSpeed(random.nextBoolean() ? game.getWizardStrafeSpeed() : -game.getWizardStrafeSpeed());
    }
    void turnRandomly() {
        move.setTurn(PI / (5 + random.nextInt(3)));
    }
    public void move(Wizard self, World world, Game game, Move move) {
        initializeTick(self, world, game, move);
    }
}
