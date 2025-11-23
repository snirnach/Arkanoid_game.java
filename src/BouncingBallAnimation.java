
public class BouncingBallAnimation {
    public static void main(String[] args) {


        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int dx = Integer.parseInt(args[2]);
        int dy = Integer.parseInt(args[3]);

        Point start = new Point(x, y);

        Ball.drawAnimation(start, dx, dy);
    }
}
