package hit;
import game.GameLevel;
import sprites.Ball;
import sprites.Block;

public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        hitter.removeFromGame(game);
        this.remainingBalls.decrease(1);
    }
}