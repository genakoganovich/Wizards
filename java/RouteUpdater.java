import model.*;

import java.util.ArrayList;
import java.util.Collections;

abstract public class RouteUpdater {
    abstract ArrayList<RoundPoint> updateRoute(Wizard self, World world, Game game, ArrayList<RoundPoint> checkpoints);
}
class DefaultRouteUpdater extends RouteUpdater {
    @Override
    ArrayList<RoundPoint> updateRoute(Wizard self, World world, Game game, ArrayList<RoundPoint> checkpoints) {
        return RouteUtil.buildRuler(self.getX(), self.getY(), 100, -StrictMath.PI / 2, 4);
    }
}
class ReverseRouteUpdater extends RouteUpdater {
    @Override
    ArrayList<RoundPoint> updateRoute(Wizard self, World world, Game game, ArrayList<RoundPoint> checkpoints) {
        Collections.reverse(checkpoints);
        return checkpoints;
    }
}