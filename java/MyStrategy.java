import model.*;

public final class MyStrategy implements Strategy{

    public void move(Wizard self, World world, Game game, Move move) {
        State state = new CurrentState();
        state.move(self, world, game, move);
    }
}