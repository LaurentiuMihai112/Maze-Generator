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
    private int size;
    private int offset;
    private Button startDFSButton;
    private Button startBKTButton;
    private Button exitButton;

    public void settings() {
        mazeGrid1 = new MazeGrid(30, 30, 1);
        solution1 = new Solution(mazeGrid1);
        mazeGrid2 = new MazeGrid(30, 30, 2);
        solution2 = new Solution(mazeGrid2);
        size = 20;
        width = 702;
        height = 602;
        startDFSButton = new Button(width - 90, 10, 80, 30);
        startBKTButton = new Button(width - 90, 50, 80, 30);
        exitButton = new Button(width - 90, height - 40, 80, 30);
        offset = 1;
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
        if (exitButton.isActivated()) {
            System.exit(0);
        }
        update();
        if (startDFSButton.isActivated()) {
            if (mazeGrid1.getCurrentValue() < mazeGrid1.getNumberOfCells() + 10) {
                createGrid(mazeGrid1);
                createMaze(mazeGrid1, mazeGrid1.getCurrentValue());
                mazeGrid1.setCurrentValue(mazeGrid1.getCurrentValue() + 2);// max(min(mazeGrid.getNumberOfRows(), mazeGrid.getNumberOfColumns()) / 2, 1);
            }
        } else if (startBKTButton.isActivated()) {
            if (mazeGrid2.getCurrentValue() < mazeGrid2.getNumberOfCells() + 10) {
                createGrid(mazeGrid2);
                createMaze(mazeGrid2, mazeGrid2.getCurrentValue());
                mazeGrid2.setCurrentValue(mazeGrid2.getCurrentValue() + 2);// max(min(mazeGrid.getNumberOfRows(), mazeGrid.getNumberOfColumns()) / 2, 1);
            }
        }
        showButtons();

        if (mazeGrid1.getCurrentValue() >= mazeGrid1.getNumberOfCells() + 10) {
            solveMaze(solution1, solution1.getCurrentValue());
            solution1.setCurrentValue(solution1.getCurrentValue() + 1);
            if (solution1.getCurrentValue() >= solution1.getPath().size()) {
                startDFSButton.setActivated(false);
                mazeGrid1 = new MazeGrid(30, 30, 1);
                solution1 = new Solution(mazeGrid1);
            }
        } else if (mazeGrid2.getCurrentValue() >= mazeGrid2.getNumberOfCells() + 10) {
            solveMaze(solution2, solution2.getCurrentValue());
            solution2.setCurrentValue(solution2.getCurrentValue() + 1);
            if (solution2.getCurrentValue() >= solution2.getPath().size()) {
                startBKTButton.setActivated(false);
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
            int angle = -1;
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
            startDFSButton.setActivated(!startDFSButton.isActivated());
        }
        if (startBKTButton.isHover()) {
            startBKTButton.setActivated(!startBKTButton.isActivated());
        }
        if (exitButton.isHover()) {
            exitButton.setActivated(true);
        }
    }
}