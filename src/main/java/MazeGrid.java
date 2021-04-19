import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MazeGrid {
    Integer numberOfRows, numberOfColumns;
    MazeCell[][] mazeMatrix;
    Integer numberOfCells;
    Boolean visited[];
    List<Integer>[] adjacencyLists;
    Integer valueVisited;
    List<MazeCell> cellOrder;

    public MazeGrid(Integer numberOfRows, Integer numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        mazeMatrix = new MazeCell[numberOfRows][numberOfColumns];
        numberOfCells = numberOfColumns * numberOfRows;
        visited = new Boolean[numberOfCells];
        adjacencyLists = new ArrayList[numberOfCells];
        cellOrder = new ArrayList<>();
        valueVisited = 0;
        generateMaze();
    }

    private void generateMaze() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                mazeMatrix[i][j] = new MazeCell();
            }
        }
        createGraph();
        DFS(0);
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
        Iterator<Integer> i = adjacencyLists[v].listIterator();

        while (i.hasNext()) {
            neighbours[l] = i.next();
            l++;
        }

        Random random = new Random();
        int index = random.nextInt(l);
        int j;
        for (j = 0; j < l; j++) {
            if (!visited[neighbours[index]]) {
                valueVisited++;
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
                mazeMatrix[row][col].setValue(valueVisited);


                DFS(neighbours[index]);
            }
            index++;
            index = index % l;
        }
    }
}
