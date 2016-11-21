import model.*;

import java.util.Random;
import java.util.logging.Logger;

public class State {
    protected Random random;
    protected Wizard self;
    protected World world;
    protected Game game;
    protected Move move;
    protected static final double RADIUS_DELTA = 10D;
    protected static final double TARGET_DELTA = 100D;
    protected static final Logger LOGGER = Logger.getLogger("MyStrategy");

    /**
     * Сохраняем все входные данные в полях класса для упрощения доступа к ним.
     */
    protected final void initializeTick(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    public void move(Wizard self, World world, Game game, Move move) {}
}
