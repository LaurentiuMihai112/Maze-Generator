public class MazeCell {
    Boolean eastWall, westWall, northWall, southWall;
    Integer line, column;
    Integer value;

    public MazeCell() {
        this.eastWall = true;
        this.westWall = true;
        this.northWall = true;
        this.southWall = true;
        this.value = 0;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "MazeCell{" +
                "eastWall=" + eastWall +
                ", westWall=" + westWall +
                ", northWall=" + northWall +
                ", southWall=" + southWall +
                ", line=" + line +
                ", column=" + column +
                ", value=" + value +
                '}';
    }
}
