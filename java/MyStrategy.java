import model.*;

public final class MyStrategy implements Strategy{
    private StrategyState strategyState;
    public void move(Wizard self, World world, Game game, Move move) {
        if(strategyState == null) {
            strategyState = new PatrolStrategyState(self, world, game);
        }
        strategyState.move(self, world, game, move);
    }
}