package algorithms;

import util.MazeCell;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solution {
    private final MazeGrid mazeGrid;
    private final boolean[][] visited;
    private final boolean[][] correctPath;
    private final List<MazeCell> path = new LinkedList<>();
    private int startX, startY;
    private final int endX;
    private final int endY;
    private Integer currentValue;

    public Solution(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        visited = new boolean[mazeGrid.getNumberOfRows()][mazeGrid.getNumberOfColumns()];
        correctPath = new boolean[mazeGrid.getNumberOfRows()][mazeGrid.getNumberOfColumns()];
        startX = startY = 0;
        currentValue = 0;
        Random random = new Random();
        endX = random.nextInt(mazeGrid.getNumberOfRows());
        endY = mazeGrid.getNumberOfColumns() - 1;
        solveMaze();
    }

    public void solveMaze() {
        for (int row = 0; row < mazeGrid.getNumberOfRows(); row++) {
            for (int col = 0; col < mazeGrid.getNumberOfColumns(); col++) {
                visited[row][col] = false;
                correctPath[row][col] = false;
            }
        }
        recursiveSolve(startX, startY);
        startX = startY = 0;
        while (true) {
            path.add(mazeGrid.getMazeMatrix()[startX][startY]);
            if (startX == endX && startY == endY) break;
            correctPath[startX][startY] = false;
            if (!mazeGrid.getMazeMatrix()[startX][startY].getNorthWall()) {
                if (interior(startX - 1, startY)) {
                    if (correctPath[startX - 1][startY]) {
                        startX--;
                        continue;
                    }
                }
            }
            if (!mazeGrid.getMazeMatrix()[startX][startY].getSouthWall()) {
                if (interior(startX + 1, startY)) {
                    if (correctPath[startX + 1][startY]) {
                        startX++;
                        continue;
                    }
                }
            }
            if (!mazeGrid.getMazeMatrix()[startX][startY].getWestWall()) {
                if (interior(startX, startY - 1)) {
                    if (correctPath[startX][startY - 1]) {
                        startY--;
                        continue;
                    }
                }
            }
            if (!mazeGrid.getMazeMatrix()[startX][startY].getEastWall()) {
                if (interior(startX, startY + 1)) {
                    if (correctPath[startX][startY + 1]) {
                        startY++;
                    }
                }
            }
        }
        mazeGrid.getMazeMatrix()[endX][endY].setEastWall(false);
        mazeGrid.getMazeMatrix()[0][0].setWestWall(false);
        path.add(mazeGrid.getMazeMatrix()[endX][endY]);
        MazeCell finalCell = new MazeCell();
        finalCell.setLine(endX);
        finalCell.setColumn(endY + 1);
        path.add(finalCell);
    }

    private boolean recursiveSolve(int x, int y) {
        if (x == endX && y == endY) {
            correctPath[x][y] = true;
            return true;
        }

        if (visited[x][y]) return false;
        visited[x][y] = true;
        if (!mazeGrid.getMazeMatrix()[x][y].getNorthWall()) {
            if (x - 1 >= 0) {
                if (recursiveSolve(x - 1, y)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }
        if (!mazeGrid.getMazeMatrix()[x][y].getSouthWall()) {
            if (x + 1 < mazeGrid.getNumberOfRows()) {
                if (recursiveSolve(x + 1, y)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }
        if (!mazeGrid.getMazeMatrix()[x][y].getWestWall()) {
            if (y - 1 >= 0) {
                if (recursiveSolve(x, y - 1)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }
        if (!mazeGrid.getMazeMatrix()[x][y].getEastWall()) {
            if (y + 1 < mazeGrid.getNumberOfColumns()) {
                if (recursiveSolve(x, y + 1)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean interior(int x, int y) {
        return (x >= 0 && x < mazeGrid.getNumberOfRows() && y >= 0 && y < mazeGrid.getNumberOfColumns());
    }

    public List<MazeCell> getPath() {
        return path;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }
}
