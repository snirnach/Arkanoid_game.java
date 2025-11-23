// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx,dy;

    public Velocity(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double rad = Math.toRadians(angle);
        double dx = speed * Math.sin(rad);
        double dy = -speed * Math.cos(rad);
        return new Velocity(dx, dy);
    }

    public double getDx() {return this.dx; }
    public double getDy() {return this.dy; }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p){
        return new Point(dx + p.getX(), dy + p.getY());
    }
}
