import model.*;
import java.util.LinkedHashSet;


class ToNearestTreeState extends State {
    private  LinkedHashSet<Point> points = new LinkedHashSet<>();

    public void move(Wizard self, World world, Game game, Move move) {
        super.move(self, world, game, move);
        Tree[] trees = world.getTrees();
        LivingUnit nearest = getNearestLivingUnit(trees);
        if(nearest != null) {
            if (isNear(nearest)) {
                dance();
                points.add(new Point(nearest.getX(), nearest.getY(), nearest.getRadius()));
            } else if (isGotStuck()){
                if(world.getTickIndex() % STUCK_TICKS == 0) {
                    turnRandomly();
                    move.setSpeed(game.getWizardForwardSpeed());
                    //LOGGER.info("got stuck");
                }
                strafe();

            } else {
                goTo(nearest.getX(), nearest.getY());
            }
        }
    }
}
