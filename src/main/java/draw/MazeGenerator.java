package draw;

import algorithms.MazeGrid;
import algorithms.Solution;
import processing.core.PApplet;
import util.Button;
import util.MazeCell;

public class MazeGenerator extends PApplet {
    private MazeGrid mazeGrid1;
    private Solution solution1;
    private MazeGrid mazeGrid2;
    private Solution solution2;
    private int currentDrawValue1;
    private int currentDrawValue2;
    private int currentSolveValue1;
    private int currentSolveValue2;
    private int size;
    private int offset;
    private int delayTime;
    private int solveDelayTime;
    private Button startDFSButton;
    private Button startBKTButton;
    private Button exitButton;
    private boolean start1;
    private boolean start2;
    private boolean exit;
    private boolean centered;

    public void settings() {
        mazeGrid1 = new MazeGrid(30, 30, 1);
        solution1 = new Solution(mazeGrid1);
        mazeGrid2 = new MazeGrid(30, 30, 2);
        solution2 = new Solution(mazeGrid2);
        currentDrawValue1 = 0;
        currentDrawValue2 = 0;
        currentSolveValue1 = 0;
        currentSolveValue2 = 0;
        size = 20;
        width = 702;
        height = 602;
        startDFSButton = new Button(width - 90, 10, 80, 30);
        startBKTButton = new Button(width - 90, 50, 80, 30);
        exitButton = new Button(width - 90, height - 40, 80, 30);
        offset = 1;
        delayTime = 0;
        solveDelayTime = 50;
        start1 = false;
        start2 = false;
        exit = false;
        centered = false;
        size(width, height, P2D);
    }

    public void createGrid(MazeGrid mazeGrid) {
        int i, j;
        for (i = 0; i < mazeGrid.getNumberOfRows(); i++) {
            for (j = 0; j < mazeGrid.getNumberOfColumns(); j++) {
                fill(60, 60, 60);
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

    private void createMaze(MazeGrid mazeGrid, int currentDrawValue) {
        int maxIndex = 0;
        for (var cell : mazeGrid.getCellOrder()) {
            fill(30, 60, 150);
            rect(cell.getColumn() * size + offset, cell.getLine() * size + offset, size, size);
            if (!cell.getEastWall()) {
                stroke(30, 60, 150);
                line(cell.getColumn() * size + offset + size, cell.getLine() * size + offset, cell.getColumn() * size + offset + size, cell.getLine() * size + offset + size);
            }
            if (!cell.getWestWall()) {
                stroke(30, 60, 150);
                line(cell.getColumn() * size + offset, cell.getLine() * size + offset, cell.getColumn() * size + offset, cell.getLine() * size + offset + size);

            }
            if (!cell.getNorthWall()) {
                stroke(30, 60, 150);
                line(cell.getColumn() * size + offset, cell.getLine() * size + offset, cell.getColumn() * size + offset + size, cell.getLine() * size + offset);

            }
            if (!cell.getSouthWall()) {
                stroke(30, 60, 150);
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
        if (exit) {
            System.exit(0);
        }
        centerWindow();
        update();
        if (start1) {
            if (currentDrawValue1 < mazeGrid1.getNumberOfCells() + 10) {
                createGrid(mazeGrid1);
                createMaze(mazeGrid1, currentDrawValue1);
                delay(delayTime);
                currentDrawValue1 += 2;// max(min(mazeGrid.getNumberOfRows(), mazeGrid.getNumberOfColumns()) / 2, 1);
            }
        } else if (start2) {
            if (currentDrawValue2 < mazeGrid2.getNumberOfCells() + 10) {
                createGrid(mazeGrid2);
                createMaze(mazeGrid2, currentDrawValue2);
                delay(delayTime);
                currentDrawValue2 += 2;// max(min(mazeGrid.getNumberOfRows(), mazeGrid.getNumberOfColumns()) / 2, 1);
            }
        }
        showButtons();

        if (currentDrawValue1 >= mazeGrid1.getNumberOfCells() + 10) {
            solveMaze(solution1, currentSolveValue1);
            delay(solveDelayTime);
            currentSolveValue1++;
            if (currentSolveValue1 >= solution1.getPath().size()) {
                start1 = false;
                currentSolveValue1 = 0;
                currentDrawValue1 = 0;
                mazeGrid1 = new MazeGrid(30, 30, 1);
                solution1 = new Solution(mazeGrid1);
            }
        } else if (currentDrawValue2 >= mazeGrid2.getNumberOfCells() + 10) {
            solveMaze(solution2, currentSolveValue2);
            delay(solveDelayTime);
            currentSolveValue2++;
            if (currentSolveValue2 >= solution2.getPath().size()) {
                start2 = false;
                currentSolveValue2 = 0;
                currentDrawValue2 = 0;
                mazeGrid2 = new MazeGrid(30, 30, 2);
                solution2 = new Solution(mazeGrid2);
            }
        }

    }

    private void showButtons() {
        if (exitButton.isHover()) {
            fill(80);
        } else {
            fill(160);
        }
        stroke(0);
        rect(exitButton.getX(), exitButton.getY(), exitButton.getWidth(), exitButton.getHeight());
        fill(0);
        text("Exit", exitButton.getX() + 31, exitButton.getY() + 19);
        if (startBKTButton.isHover()) {
            fill(80);
        } else {
            fill(160);
        }
        stroke(0);
        rect(startBKTButton.getX(), startBKTButton.getY(), startBKTButton.getWidth(), startBKTButton.getHeight());
        fill(0);
        text("Start (BKT)", startBKTButton.getX() + 10, startBKTButton.getY() + 19);
        if (startDFSButton.isHover()) {
            fill(80);
        } else {
            fill(160);
        }
        stroke(0);
        rect(startDFSButton.getX(), startDFSButton.getY(), startDFSButton.getWidth(), startDFSButton.getHeight());
        fill(0);
        text("Start (DFS)", startDFSButton.getX() + 10, startDFSButton.getY() + 19);
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

    private void solveMaze(Solution solution, int currentSolveValue) {
        int maxIndex = 0;
        for (var cell : solution.getPath()) {
            fill(210, 30, 30);
            int angle = 0;
            try {
                MazeCell next = solution.getPath().get(solution.getPath().indexOf(cell) + 1);
                if (next.getLine() > cell.getLine()) {
                    angle = 90;
                } else if (next.getColumn() < cell.getColumn()) {
                    angle = 180;
                } else if (next.getColumn() > cell.getColumn()) {
                    angle = 0;
                } else if (next.getLine() < cell.getLine()) {
                    angle = 270;
                }
                stroke(210, 30, 30);
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
        startDFSButton.setHover(overButton(startDFSButton));
        startBKTButton.setHover(overButton(startBKTButton));
        exitButton.setHover(overButton(exitButton));
    }

    boolean overButton(Button startButton) {
        return mouseX >= startButton.getX() && mouseX <= startButton.getX() + startButton.getWidth() && mouseY >= startButton.getY() && mouseY <= startButton.getY() + startButton.getHeight();
    }

    public void mousePressed() {
        if (startDFSButton.isHover()) {
            start1 = !start1;
        }
        if (startBKTButton.isHover()) {
            start2 = !start2;
        }
        if (exitButton.isHover()) {
            exit = true;
        }
    }
}