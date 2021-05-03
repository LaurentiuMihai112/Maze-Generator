import processing.core.PApplet;

public class GenerateMaze extends PApplet {
    MazeGrid mazeGrid;
    int currentValue;
    int size;
    int offset;
    int delayTime;
    Button startButton;
    boolean start;

    public void settings() {
        mazeGrid = new MazeGrid(30, 30);
        startButton = new Button(912, 10, 80, 30);
        currentValue = 0;
        size = 30;
        width = 1002;
        height = 902;
        offset = 1;
        delayTime = 0;
        start = false;
        size(1002, 902, P2D);
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
        update(mouseX, mouseY);
        if (start) {
            createGrid();
            createMaze();
            delay(delayTime);
            currentValue++;

        } else {
            fill(0);
            rect(0, 0, 902, 902);
        }
        if (startButton.isHover()) {
            fill(80);
        } else {
            fill(160);
        }
        stroke(0);
        rect(startButton.getX(), startButton.getY(), startButton.getWidth(), startButton.getHeight());
        fill(0);
        if (!start) {
            text("Start", startButton.getX() + 26, startButton.getY() + 19);
        } else {
            text("Stop", startButton.getX() + 26, startButton.getY() + 19);
        }
        if (currentValue == 901) {
            save("F://maze.png");
        }

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

    void update(int x, int y) {

        if (overButton(startButton.getX(), startButton.getY(), startButton.getWidth(), startButton.getHeight())) {
            startButton.setHover(true);
        } else {
            startButton.setHover(false);
        }
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
        }
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
    boolean overButton(int x, int y, int width, int height) {
        if (mouseX >= x && mouseX <= x + startButton.getWidth() && mouseY >= y && mouseY <= y + startButton.getHeight()) {
            return true;
        }
        return false;
    }

    public void mousePressed() {
        if (startButton.isHover()) {
            start = !start;
        }

    }
}