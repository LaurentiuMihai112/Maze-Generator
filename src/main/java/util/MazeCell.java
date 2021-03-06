package util;

public class MazeCell {
    private Boolean eastWall, westWall, northWall, southWall;
    private Integer line, column;
    private Boolean visited = false;

    public MazeCell() {
        this.eastWall = true;
        this.westWall = true;
        this.northWall = true;
        this.southWall = true;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Boolean getEastWall() {
        return eastWall;
    }

    public void setEastWall(Boolean eastWall) {
        this.eastWall = eastWall;
    }

    public Boolean getWestWall() {
        return westWall;
    }

    public void setWestWall(Boolean westWall) {
        this.westWall = westWall;
    }

    public Boolean getNorthWall() {
        return northWall;
    }

    public void setNorthWall(Boolean northWall) {
        this.northWall = northWall;
    }

    public Boolean getSouthWall() {
        return southWall;
    }

    public void setSouthWall(Boolean southWall) {
        this.southWall = southWall;
    }

    public Boolean isVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

}
