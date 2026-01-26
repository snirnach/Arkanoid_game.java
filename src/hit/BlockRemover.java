package hit;

import game.GameLevel;
import sprites.Ball;
import sprites.Block;


public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    // Blocks that are hit should be removed from the game.
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }
}