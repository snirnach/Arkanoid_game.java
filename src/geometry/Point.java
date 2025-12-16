package geometry;

public class Point {
    private double x, y;
    private static final double EPSILON = 0.00001;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        return  equals(this.x, other.x) && equals(this.y, other.getY());
    }
    private boolean equals(double x1, double x2) {
        return Math.abs(x1 - x2) < EPSILON;
    }

    // Return the x and y values of this point
    public double getX() {return this.x; }
    public void setX(double x) {this.x = x; }
    public double getY() {return this.y; }
    public void setY(double y) {this.y = y; }
}

