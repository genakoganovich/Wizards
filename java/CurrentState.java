import model.*;
import static java.lang.StrictMath.PI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import java.awt.geom.*;

public final class CurrentState extends State{
    private Random random;
    private Wizard self;
    private World world;
    private Game game;
    private Move move;
    private static final double RADIUS_DELTA = 10D;
    private static final double TARGET_DELTA = 100D;
    private static final Logger LOGGER = Logger.getLogger("MyStrategy");


    private void initializeStrategy(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
        if (random == null) {
            random = new Random(game.getRandomSeed());
        }

    }
    private double getSpeedX() {return move.getSpeed() * StrictMath.cos(self.getAngle());}
    private double getSpeedY() {return move.getSpeed() * StrictMath.sin(self.getAngle());}

    private void addObstacles(ArrayList<CircularUnit> neighbours, CircularUnit[] circularUnits) {
        double centerX = self.getX() + getSpeedX() / 2;
        double centerY = self.getY() + getSpeedY() / 2;
        Rectangle2D area = new Rectangle2D.Double(centerX, centerY, game.getWizardForwardSpeed(), 2 * self.getRadius());
        for (CircularUnit unit: circularUnits) {
            Ellipse2D round = new Ellipse2D.Double(unit.getX(), unit.getY(), 2 * unit.getRadius(), 2 * unit.getRadius());
            if(round.intersects(area)) {
                neighbours.add(unit);
            }
        }
    }
    private ArrayList<CircularUnit> getObstacles() {
        ArrayList<CircularUnit> neighbours = new ArrayList<>();
        addObstacles(neighbours, world.getBuildings());
        addObstacles(neighbours, world.getTrees());
        addObstacles(neighbours, world.getWizards());
        addObstacles(neighbours, world.getMinions());
        return neighbours;
    }
    private void changeDirectionTo(double x, double y) {
        double angle = self.getAngleTo(x, y);
        move.setStrafeSpeed(random.nextBoolean() ? game.getWizardStrafeSpeed() : -game.getWizardStrafeSpeed());
        move.setTurn(angle);

        if (StrictMath.abs(angle) < game.getStaffSector() / 4.0D) {
            move.setSpeed(game.getWizardForwardSpeed());
        }
    }
    private boolean isNotFoe(Faction faction) {
        return faction == self.getFaction() || faction == Faction.NEUTRAL || faction == Faction.OTHER;
    }
    private void goTo(double x, double y) {
        changeDirectionTo(x, y);
        ArrayList<CircularUnit> obstacles = getObstacles();
        if(!obstacles.isEmpty()) {
            CircularUnit nearest = (CircularUnit) getNearest(obstacles);
            if (self.getDistanceTo(nearest) > self.getRadius() + nearest.getRadius()) {
                changeDirectionTo(nearest.getX(), nearest.getY());
            } else {
                move.setTurn(PI / 32);
                if(nearest == self) {
                    LOGGER.info("self");
                }
                //LOGGER.info("distance: " + self.getDistanceTo(nearest));
                //LOGGER.info("self.getRadius() + nearest.getRadius(): " + self.getRadius() + nearest.getRadius());
            }
        }
    }
    private Unit getNearest(ArrayList<? extends Unit> units) {
        double distance = Double.MAX_VALUE;
        Unit nearest = null;
        for(Unit unit : units) {
            if(self.getDistanceTo(unit) < distance) {
                distance = self.getDistanceTo(unit);
                nearest = unit;
            }
        }
        return nearest;
    }
    private void goRight(Unit unit) {
        if(self.getAngleTo(unit) != PI/2) {
            move.setTurn(self.getAngleTo(unit) + PI/2);
        }
        move.setSpeed(game.getWizardForwardSpeed());
    }

    public void move(Wizard self, World world, Game game, Move move) {
        initializeStrategy(self, world, game, move);
        goTo(game.getMapSize() - TARGET_DELTA, TARGET_DELTA);
    }
}