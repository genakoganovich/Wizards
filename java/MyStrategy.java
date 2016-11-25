import model.*;

public final class MyStrategy implements Strategy{

    public void move(Wizard self, World world, Game game, Move move) {
        StrategyState strategyState = new ToNearestTreeStrategyState();
        strategyState.move(self, world, game, move);
    }
}