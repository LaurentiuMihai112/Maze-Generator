import processing.core.PApplet;

public class GenerateMaze extends PApplet {
    MazeGrid mazeGrid;
    Solution solution;
    int currentDrawValue;
    int currentSolveValue;
    int size;
    int offset;
    int delayTime;
    int solveDelayTime;
    Button startButton;
    boolean start;
    boolean centered;

    public void settings() {
        mazeGrid = new MazeGrid(30, 30);
        solution = new Solution(mazeGrid);
        currentDrawValue = 0;
        currentSolveValue = 0;
        size = 20;
        width = 702;
        height = 602;
        startButton = new Button(width - 90, 10, 80, 30);
        offset = 1;
        delayTime = 0;
        solveDelayTime = 50;
        start = false;
        centered = false;
        size(width, height, P2D);
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

    void centerWindow() {
        if (!centered) {
            frame.setLocation(0, 0);
            centered = true;
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
            if (currentDrawValue == maxIndex) {
                break;
            }
            maxIndex++;
        }
    }

    public void draw() {
        centerWindow();
        update();
        if (start) {
            if (currentDrawValue < mazeGrid.numberOfCells + 10) {
                createGrid();
                createMaze();
                delay(delayTime);
                currentDrawValue += max(min(mazeGrid.numberOfRows, mazeGrid.numberOfColumns) / 2, 1);
            }
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
        if (currentDrawValue >= mazeGrid.numberOfCells + 10) {
            solveMaze();
            delay(solveDelayTime);
            currentSolveValue++;
        }
    }

    void drawArrow(int cx, int cy, int len, float angle) {
        strokeWeight(2);
        pushMatrix();
        translate(cx, cy);
        rotate(radians(angle));
        line(0, 0, len, 0);
        line(len, 0, len / 2, -len / 2);
        line(len, 0, len / 2, len / 2);
        popMatrix();
        strokeWeight(1);
    }

    private void solveMaze() {
        int maxIndex = 0;
        for (var cell : solution.path) {
            fill(255, 0, 0);
            int angle = 0;
            try {
                MazeCell next = solution.path.get(solution.path.indexOf(cell) + 1);
                if (next.line > cell.line) {
                    angle = 90;
                } else if (next.column < cell.column) {
                    angle = 180;
                } else if (next.column > cell.column) {
                    angle = 0;
                } else if (next.line < cell.line) {
                    angle = 270;
                }
                stroke(250, 0, 0);
                drawArrow(cell.getColumn() * size + offset + size / 2, cell.getLine() * size + offset + size / 2, size / 2, angle);
            } catch (RuntimeException e) {
                break;
            }
            stroke(0);
            if (currentSolveValue == maxIndex) {
                break;
            }
            maxIndex++;
        }
    }

    void update() {
        if (overButton(startButton)) {
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

    boolean overButton(Button startButton) {
        if (mouseX >= startButton.getX() && mouseX <= startButton.getX() + startButton.getWidth() && mouseY >= startButton.getY() && mouseY <= startButton.getY() + startButton.getHeight()) {
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