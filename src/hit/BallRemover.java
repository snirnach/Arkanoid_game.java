package hit;
import game.Game;
import sprites.Ball;
import sprites.Block;

public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}