public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collidable;

    public CollisionInfo(Point collisionPoint, Collidable collidable) {
        this.collisionPoint = collisionPoint;
        this.collidable = collidable;
    }

    // the point at which the collision occurs.
    public Point collisionPoint(){
        return collisionPoint;
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject(){
        return collidable;
    }
}
