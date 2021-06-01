import algorithms.MazeGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import util.MazeCell;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeCellTest {
    private MazeGrid mazeGrid;

    @BeforeEach
    public void setUpMaze() {
        mazeGrid = new MazeGrid(30, 30, 2);
    }

    @RepeatedTest(10)
    @DisplayName("Cell should have at least 1 wall left")
    public void testCellWalls() {
        Random random = new Random();
        int x = random.nextInt(mazeGrid.getNumberOfRows()), y = random.nextInt(mazeGrid.getNumberOfColumns());
        MazeCell cell = mazeGrid.getMazeMatrix()[x][y];
        assertTrue((cell.getEastWall() || cell.getNorthWall() || cell.getWestWall() || cell.getSouthWall()));
    }

    @RepeatedTest(10)
    @DisplayName("Cell should not have 4 walls")
    public void testCellAllWalls() {
        Random random = new Random();
        int x = random.nextInt(mazeGrid.getNumberOfRows()), y = random.nextInt(mazeGrid.getNumberOfColumns());
        MazeCell cell = mazeGrid.getMazeMatrix()[x][y];
        assertFalse((cell.getEastWall() && cell.getNorthWall() && cell.getWestWall() && cell.getSouthWall()));
    }

    @Test
    @DisplayName("All cells should be visited")
    public void testVisitedCells() {
        for (int row = 0; row < mazeGrid.getNumberOfRows(); row++) {
            for (int col = 0; col < mazeGrid.getNumberOfColumns(); col++) {
                assertTrue(mazeGrid.getMazeMatrix()[row][col].isVisited());
            }
        }
    }
}
