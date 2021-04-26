import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateMaze extends PApplet {
    int currentValue = 1;
    MazeGrid mazeGrid;
    int size = 10;
    int offset = 1;
    int delayTime = 0;

    public void settings() {
        size(3002, 1002, P2D);
    }

    public void setup() {
        mazeGrid = new MazeGrid(20, 300);
        currentValue = 0;
        createGrid();
    }

    public void createGrid() {
        int i, j;
        for (i = 0; i < mazeGrid.numberOfRows; i++) {
            for (j = 0; j < mazeGrid.numberOfColumns; j++) {
                fill(1, 0, 62);
                rect(j * size + offset, i * size + offset, size, size);
            }
        }
    }

    private void createMaze() {
        int maxIndex = 0;
        for (var cell : mazeGrid.cellOrder) {
            fill(255, 255, 55);
            rect(cell.getColumn() * size + offset, cell.getLine() * size + offset, size, size);
            if (!cell.getEastWall()) {
                stroke(255, 255, 55);
                line(cell.getColumn() * size + offset + size, cell.getLine() * size + offset, cell.getColumn() * size + offset + size, cell.getLine() * size + offset + size);
            }
            if (!cell.getWestWall()) {
                stroke(255, 255, 55);
                line(cell.getColumn() * size + offset, cell.getLine() * size + offset, cell.getColumn() * size + offset, cell.getLine() * size + offset + size);

            }
            if (!cell.getNorthWall()) {
                stroke(255, 255, 55);
                line(cell.getColumn() * size + offset, cell.getLine() * size + offset, cell.getColumn() * size + offset + size, cell.getLine() * size + offset);

            }
            if (!cell.getSouthWall()) {
                stroke(255, 255, 55);
                line(cell.getColumn() * size + offset, cell.getLine() * size + offset + size, cell.getColumn() * size + offset + size, cell.getLine() * size + offset + size);

            }
            stroke(0);
            maxIndex++;
            if (currentValue == maxIndex) {
                break;
            }
        }
    }

    public void draw() {
        createGrid();
        createMaze();
        delay(delayTime);
        currentValue++;
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
}