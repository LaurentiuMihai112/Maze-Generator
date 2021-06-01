package algorithms;

import util.MazeCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGrid {
    private final Integer numberOfRows;
    private final Integer numberOfColumns;
    private final MazeCell[][] mazeMatrix;
    private final Integer numberOfCells;
    private final Boolean[] visited;
    private final List<Integer>[] adjacencyLists;
    private final List<MazeCell> cellOrder;
    private Integer currentValue;

    public MazeGrid(Integer numberOfRows, Integer numberOfColumns, Integer algNumber) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        mazeMatrix = new MazeCell[numberOfRows][numberOfColumns];
        numberOfCells = numberOfColumns * numberOfRows;
        visited = new Boolean[numberOfCells];
        adjacencyLists = new ArrayList[numberOfCells];
        cellOrder = new ArrayList<>();
        currentValue = 0;
        if (algNumber == 1) {
            generateMazeDFSAlgorithm();
        } else if (algNumber == 2) {
            generateMazeBacktrackingAlgorithm();
        }
    }

    private void generateMazeDFSAlgorithm() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                mazeMatrix[i][j] = new MazeCell();
            }
        }
        createGraph();
        DFS(0);
    }

    private void generateMazeBacktrackingAlgorithm() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                mazeMatrix[i][j] = new MazeCell();
            }
        }
        createMaze(0, 0);
    }

    private boolean interior(int x, int y) {
        return (x >= 0 && x < numberOfRows && y >= 0 && y < numberOfColumns);
    }

    private int createMaze(int x, int y) {
        if (!mazeMatrix[x][y].isVisited()) {
            mazeMatrix[x][y].setLine(x);
            mazeMatrix[x][y].setColumn(y);
            mazeMatrix[x][y].setVisited(true);
            cellOrder.add(mazeMatrix[x][y]);
            if (cellOrder.size() != numberOfCells) {
                Random random = new Random();
                int[] dx = {-1, 1, 0, 0};
                int[] dy = {0, 0, -1, 1};
                int p = random.nextInt(4);
                for (int i = 0; i < 4; i++) {
                    if (interior(x + dx[p], y + dy[p])) {
                        int res = createMaze(x + dx[p], y + dy[p]);
                        if (res == 1) {
                            if (p == 0) {
                                mazeMatrix[x][y].setNorthWall(false);
                                mazeMatrix[x + dx[p]][y + dy[p]].setSouthWall(false);
                            }
                            if (p == 1) {
                                mazeMatrix[x][y].setSouthWall(false);
                                mazeMatrix[x + dx[p]][y + dy[p]].setNorthWall(false);
                            }
                            if (p == 2) {
                                mazeMatrix[x][y].setWestWall(false);
                                mazeMatrix[x + dx[p]][y + dy[p]].setEastWall(false);
                            }
                            if (p == 3) {
                                mazeMatrix[x][y].setEastWall(false);
                                mazeMatrix[x + dx[p]][y + dy[p]].setWestWall(false);
                            }
                        }
                    }
                    p++;
                    p = p % 4;
                }
                return 1;
            }
        }
        return 0;
    }

    private void createGraph() {

        for (int i = 0; i < numberOfCells; i++) {
            visited[i] = false;
            adjacencyLists[i] = new ArrayList<>();
            if ((i - numberOfColumns) >= 0) {
                adjacencyLists[i].add(i - numberOfColumns);
            }
            if ((i + numberOfColumns) < numberOfCells) {
                adjacencyLists[i].add(i + numberOfColumns);
            }
            if ((i - 1) % numberOfColumns != (numberOfColumns - 1) && (i - 1) >= 0) {
                adjacencyLists[i].add(i - 1);
            }
            if ((i + 1) % numberOfColumns != 0 && (i + 1) < numberOfCells) {
                adjacencyLists[i].add(i + 1);
            }
        }
    }

    void DFS(int v) {
        visited[v] = true;
        cellOrder.add(mazeMatrix[v / numberOfColumns][v % numberOfColumns]);
        mazeMatrix[v / numberOfColumns][v % numberOfColumns].setLine(v / numberOfColumns);
        mazeMatrix[v / numberOfColumns][v % numberOfColumns].setColumn(v % numberOfColumns);
        int l = 0;
        Integer[] neighbours = new Integer[4];

        for (Integer i : adjacencyLists[v]) {
            neighbours[l] = i;
            l++;
        }

        Random random = new Random();
        int index = random.nextInt(l);
        int j;
        for (j = 0; j < l; j++) {
            if (!visited[neighbours[index]]) {
                int row = (neighbours[index] / numberOfColumns);
                int col = neighbours[index] % numberOfColumns;
                if (neighbours[index] == (v + 1)) {
                    mazeMatrix[v / numberOfColumns][v % numberOfColumns].setEastWall(false);
                    mazeMatrix[row][col].setWestWall(false);
                }
                if (neighbours[index] == (v - 1)) {
                    mazeMatrix[v / numberOfColumns][v % numberOfColumns].setWestWall(false);
                    mazeMatrix[row][col].setEastWall(false);
                }
                if (neighbours[index] == (v + numberOfColumns)) {
                    mazeMatrix[v / numberOfColumns][v % numberOfColumns].setSouthWall(false);
                    mazeMatrix[row][col].setNorthWall(false);
                }
                if (neighbours[index] == (v - numberOfColumns)) {
                    mazeMatrix[v / numberOfColumns][v % numberOfColumns].setNorthWall(false);
                    mazeMatrix[row][col].setSouthWall(false);
                }
                DFS(neighbours[index]);
            }
            index++;
            index = index % l;
        }
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public Integer getNumberOfColumns() {
        return numberOfColumns;
    }

    public Integer getNumberOfCells() {
        return numberOfCells;
    }

    public List<MazeCell> getCellOrder() {
        return cellOrder;
    }

    public MazeCell[][] getMazeMatrix() {
        return mazeMatrix;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }
}
