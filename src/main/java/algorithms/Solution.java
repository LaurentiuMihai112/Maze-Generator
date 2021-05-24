package algorithms;

import util.MazeCell;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solution {
    MazeGrid mazeGrid;
    boolean[][] visited;
    boolean[][] correctPath;
    List<MazeCell> path = new LinkedList<>();
    int startX, startY;
    int endX, endY;

    public Solution(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        visited = new boolean[mazeGrid.numberOfRows][mazeGrid.numberOfColumns];
        correctPath = new boolean[mazeGrid.numberOfRows][mazeGrid.numberOfColumns];
        startX = startY = 0;
        Random random = new Random();
        endX = random.nextInt(mazeGrid.numberOfRows);
        endY = mazeGrid.numberOfColumns - 1;
        solveMaze();
    }

    public void solveMaze() {
        for (int row = 0; row < mazeGrid.numberOfRows; row++) {
            for (int col = 0; col < mazeGrid.numberOfColumns; col++) {
                visited[row][col] = false;
                correctPath[row][col] = false;
            }
        }
        recursiveSolve(startX, startY);
        startX = startY = 0;
        while (true) {
            path.add(mazeGrid.mazeMatrix[startX][startY]);
            if (startX == endX && startY == endY) break;
            correctPath[startX][startY] = false;
            if (!mazeGrid.mazeMatrix[startX][startY].getNorthWall()) {
                if (interior(startX - 1, startY)) {
                    if (correctPath[startX - 1][startY]) {
                        startX--;
                        continue;
                    }
                }
            }
            if (!mazeGrid.mazeMatrix[startX][startY].getSouthWall()) {
                if (interior(startX + 1, startY)) {
                    if (correctPath[startX + 1][startY]) {
                        startX++;
                        continue;
                    }
                }
            }
            if (!mazeGrid.mazeMatrix[startX][startY].getWestWall()) {
                if (interior(startX, startY - 1)) {
                    if (correctPath[startX][startY - 1]) {
                        startY--;
                        continue;
                    }
                }
            }
            if (!mazeGrid.mazeMatrix[startX][startY].getEastWall()) {
                if (interior(startX, startY + 1)) {
                    if (correctPath[startX][startY + 1]) {
                        startY++;
                    }
                }
            }
        }
        mazeGrid.mazeMatrix[endX][endY].setEastWall(false);
        mazeGrid.mazeMatrix[0][0].setWestWall(false);
        path.add(mazeGrid.mazeMatrix[endX][endY]);
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
        if (!mazeGrid.mazeMatrix[x][y].getNorthWall()) {
            if (x - 1 >= 0) {
                if (recursiveSolve(x - 1, y)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }
        if (!mazeGrid.mazeMatrix[x][y].getSouthWall()) {
            if (x + 1 < mazeGrid.numberOfRows) {
                if (recursiveSolve(x + 1, y)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }
        if (!mazeGrid.mazeMatrix[x][y].getWestWall()) {
            if (y - 1 >= 0) {
                if (recursiveSolve(x, y - 1)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }
        if (!mazeGrid.mazeMatrix[x][y].getEastWall()) {
            if (y + 1 < mazeGrid.numberOfColumns) {
                if (recursiveSolve(x, y + 1)) {
                    correctPath[x][y] = true;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean interior(int x, int y) {
        return (x >= 0 && x < mazeGrid.numberOfRows && y >= 0 && y < mazeGrid.numberOfColumns);
    }

    public MazeGrid getMazeGrid() {
        return mazeGrid;
    }

    public void setMazeGrid(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
    }

    public boolean[][] getVisited() {
        return visited;
    }

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
    }

    public boolean[][] getCorrectPath() {
        return correctPath;
    }

    public void setCorrectPath(boolean[][] correctPath) {
        this.correctPath = correctPath;
    }

    public List<MazeCell> getPath() {
        return path;
    }

    public void setPath(List<MazeCell> path) {
        this.path = path;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}
