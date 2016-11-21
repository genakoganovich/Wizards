package quickstart;

import model.*;

/**
 * Created by Gena on 11/19/2016.
 */
public final class MyDefaultStrategy {

    public void move(Wizard self, World world, Game game, Move move) {
        move.setSpeed(game.getWizardForwardSpeed());
        move.setStrafeSpeed(game.getWizardStrafeSpeed());
        move.setTurn(game.getWizardMaxTurnAngle());
        move.setAction(ActionType.MAGIC_MISSILE);
    }
}
