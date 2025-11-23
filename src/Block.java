public class Block implements Collidable {
    private Rectangle collisionRectangle;

    public Block(Rectangle rect) {
        collisionRectangle = rect;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();

        if (inLine(collisionRectangle.getUp(), collisionPoint) ||
                inLine(collisionRectangle.getDown(), collisionPoint)) {
            newDy *= -1;
        }

        if (inLine(collisionRectangle.getLeft(), collisionPoint) ||
                inLine(collisionRectangle.getRight(), collisionPoint)) {
            newDx *= -1;
        }

        return new Velocity(newDx, newDy);
    }

    private boolean inLine(Line line, Point slice) {
        return Line.inLine(line.start(), line.end(), slice);
    }
}
