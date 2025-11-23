import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c){
        collidables.add(c);
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory){
        Collidable closestCollidable = null;
        Point closestPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Collidable collidable : collidables) {
            Rectangle rect = collidable.getCollisionRectangle();

            Point p = trajectory.closestIntersectionToStartOfLine(rect);

            if (p != null) {
                double currentDistance = p.distance(trajectory.start());
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    closestPoint = p;
                    closestCollidable = collidable;
                }
            }
        }

        if (closestCollidable == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestCollidable);

    }

}
