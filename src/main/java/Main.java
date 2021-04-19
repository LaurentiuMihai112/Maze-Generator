import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends PApplet {
    int x = 0;
    List<Point> points = createGrid(100);
    List<Point> other = createGrid(1000);
    int sign = 1;
    int scale1=1;

    public void settings() {
        size(800, 800, P2D);
    }

    public void setup() {

    }

    public List createGrid(int offset) {
        int y = 0;
        int i, j, ok = 1;
        List<Point> points = new ArrayList<>();
        for (i = 0; i < 100; i++) {
            for (j = 0; j < 100; j++) {
                points.add(new Point(i * 8, j * 8 + offset));
            }
        }
        return points;
    }


    public void square(int x, int y, int r) throws InterruptedException {
        stroke(100);
        Thread.sleep(1000);
        rect(x, y, r, r);
        square(x + r / 2, y, r);
        square(x - r / 2, y, r);
        square(x, y + r / 2, r);
        square(x, y - r / 2, r);
        square(x + r / 2, y + r / 2, r);
        square(x - r / 2, y - r / 2, r);
        square(x + r / 2, y - r / 2, r);
        square(x - r / 2, y + r / 2, r);
    }

    void polygon(float x, float y, float radius, int npoints) {
        float angle = TWO_PI / npoints;
        beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius;
            float sy = y + sin(a) * radius;
            vertex(sx, sy);
        }
        endShape(CLOSE);
    }

    void star(float x, float y, float radius1, float radius2, int npoints) {
        float angle = TWO_PI / npoints;
        float halfAngle = (float) (angle / 2.0);
        beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius2;
            float sy = y + sin(a) * radius2;
            vertex(sx, sy);
            sx = x + cos(a + halfAngle) * radius1;
            sy = y + sin(a + halfAngle) * radius1;
            vertex(sx, sy);
        }
        endShape(CLOSE);
    }

    public void hexGrid(int scale, int x, int y) {
        fill(180, 0, 0);
        polygon(x, y, 10, 6);
        if (scale != 0) {
            scale--;
            fill(0, 0, 90);
            hexGrid(scale, x, y - 17);
            fill(0, 90, 90);
            hexGrid(scale, x, y + 17);
            fill(90, 0, 90);
            hexGrid(scale, x + 15, y + 8);
            fill(190, 0, 90);
            hexGrid(scale, x + 15, y - 9);
            fill(160, 0, 30);
            hexGrid(scale, x - 15, y + 8);
            fill(0, 110, 90);
            hexGrid(scale, x - 15, y - 9);
        } else
            return;
    }

    public void draw() {
        fill(180, 0, 0);
        hexGrid(scale1, 400, 400);
        scale1++;
    }
//    public void draw() {
//        Random random = new Random();
//        background(0);
//        for (Point point : points) {
//            fill(random(256), random(256), random(256));
//            rect(point.y, point.x, 8, 8);
//            if (points.get(x) == point) {
//                break;
//            }
//        }
//        for (Point point2 : other) {
//            fill(random(256), random(256), random(256));
//            rect(point2.y, point2.x, 8, 8);
//            if (other.get(x) == point2) {
//                break;
//            }
//        }
//        x += sign * 99;
//        if (x + sign * 20 >= 10000 - 1) {
//            sign = -1;
//        }
//        if (x + sign * 20 < 0) {
//            sign = 1;
//        }
//    }


    public static void main(String[] args) {
        PApplet.main("Main");
    }
}