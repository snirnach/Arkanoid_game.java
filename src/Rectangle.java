import biuoop.DrawSurface;

import java.awt.*;
import java.util.ArrayList;

public class Rectangle {
    private Point upperLeft;
    private double width, height;

    public Rectangle(Point upperLeft, double width, double height){
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    public java.util.List<Point> intersectionPoints(Line line) {
        Line[] edges = {getUp(), getDown(), getLeft(), getRight()};

        java.util.List<Point> intersections = new ArrayList<>();

        for (Line edge : edges) {
            Point intersection = edge.intersectionWith(line);
            if (intersection != null && (!intersections.contains(intersection))) {
                intersections.add(intersection);
            }
        }

        return intersections;
    }


    // Return the width and height of the rectangle
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft(){
        return upperLeft;
    }

    public Line getUp(){
        return new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX() + width, upperLeft.getY());
    }
    public Line getDown(){
        return new Line(upperLeft.getX(), upperLeft.getY() + height, upperLeft.getX() + width, upperLeft.getY() + height);
    }
    public Line getLeft(){
        return new Line(upperLeft.getX(), upperLeft.getY(), upperLeft.getX(), upperLeft.getY() + height);
    }
    public Line getRight(){
        return new Line(upperLeft.getX() + width, upperLeft.getY(), upperLeft.getX() + width, upperLeft.getY() + height);
    }

}

