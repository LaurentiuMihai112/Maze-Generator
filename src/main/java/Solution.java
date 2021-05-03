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
                if (correctPath[startX - 1][startY]) {
                    startX--;
                    continue;
                }
            }
            if (!mazeGrid.mazeMatrix[startX][startY].getSouthWall()) {
                if (correctPath[startX + 1][startY]) {
                    startX++;
                    continue;
                }
            }
            if (!mazeGrid.mazeMatrix[startX][startY].getWestWall()) {
                if (correctPath[startX][startY - 1]) {
                    startY--;
                    continue;
                }
            }
            if (!mazeGrid.mazeMatrix[startX][startY].getEastWall()) {
                if (correctPath[startX][startY + 1]) {
                    startY++;
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
            if (recursiveSolve(x - 1, y)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        if (!mazeGrid.mazeMatrix[x][y].getSouthWall()) {
            if (recursiveSolve(x + 1, y)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        if (!mazeGrid.mazeMatrix[x][y].getWestWall()) {
            if (recursiveSolve(x, y - 1)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        if (!mazeGrid.mazeMatrix[x][y].getEastWall()) {
            if (recursiveSolve(x, y + 1)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        MazeGrid mazeGrid = new MazeGrid(10, 10);
//        Solution solution = new Solution(mazeGrid);
//    }
}
