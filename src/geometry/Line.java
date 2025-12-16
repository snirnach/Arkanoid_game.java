package geometry;

import java.util.List;

public class Line {
   private Point start, end;
   private double slope;
   private double b; // slice with line x

    public Line(Point start, Point end) {
        if (start.getX() < end.getX()) {
            this.start = start;
            this.end = end;
        }
        else {
            this.start = end;
            this.end = start;
        }
        this. slope = slope(start, end);
        if (start.getX() == end.getX()) {
            this.b = Double.NaN;
        }
        else {
        this.b = start.getY() - this.slope * start.getX();
        }
    }

    public Line(double x1, double y1, double x2, double y2) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        if (p1.getX() < p2.getX()) {
            this.start = p1;
            this.end = p2;
        }
        else {
            this.start = p2;
            this.end = p1;
        }
        this. slope = slope(this.start,this.end);
        if (start.getX() == end.getX()) {
            this.b = Double.NaN;
        }
        else {
            this.b = this.start.getY() - this.slope * this.start.getX();
        }
    }


    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        return new Point((start().getX() + end().getX()) / 2, (start().getY() + end.getY()) / 2);
    }

    // Returns the start point of the line
    public Point start() {
        return start;
    }

    // Returns the end point of the line
    public Point end() {
        return end;
    }
    public double slope() {
        return slope;
    }
    public double b() {
        return b;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
       if (this.slope == other.slope()) {
           if (this.b != other.b()) {
               return false;
           }
           else {
               return Math.max(start.getX(), other.start().getX()) <= Math.min(end.getX(), other.end().getX());
           }
       }
        return intersectionWith(other) != null;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        double ls1 = this.slope;
        double ls2 = other.slope();
        double b1 = this.b;
        double b2 = other.b();
        if (ls1 == ls2){
            return null;
        }
        double xslice, yslice;

        if (ls1 == Double.POSITIVE_INFINITY) {
            xslice = this.start.getX();
            yslice = ls2 * xslice + b2;
        } else if (ls2 == Double.POSITIVE_INFINITY) {
            xslice = other.start().getX();
            yslice = ls1 * xslice + b1;
        } else {
            xslice = (b2 - b1) / (ls1-ls2);
            yslice = xslice * ls1 + b1;
        }

        Point slice = new Point(xslice, yslice);
        if (inLine(this.start, this.end,slice) && inLine(other.start(),other.end(),slice) ) {
            return slice;
        }
        return null;
    }

    public Point closestIntersectionToStartOfLine(Rectangle rect){
        List<Point> intersections = rect.intersectionPoints(new Line(this.start,this.end));
        double closerDis = Double.POSITIVE_INFINITY;
        Point closerPoint = null;

        for (Point point : intersections) {
            if (point.distance(start) < closerDis){
                closerDis = point.distance(start);
                closerPoint = point;
            }
        }

        return closerPoint;
    }

    public static boolean inLine(Point start, Point end, Point slice) {
        boolean withinX = slice.getX() >= start.getX() && slice.getX() <= end.getX();
        boolean withinY = slice.getY() >= Math.min(start.getY(), end.getY()) &&
                slice.getY() <= Math.max(start.getY(), end.getY());
        return withinX && withinY;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        return this.start.equals(other.start()) && this.end.equals(other.end());
    }

    private double slope(Point p1, Point p2) {
        if (p1.getX() == p2.getX()){
            return Double.POSITIVE_INFINITY;
        }
        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }
}