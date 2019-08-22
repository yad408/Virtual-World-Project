final class Viewport
{
    private int row;
    private int col;
    private int numRows;
    private int numCols;

    Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    int getNumRows() {
        return numRows;
    }

    int getNumCols() {
        return numCols;
    }

    boolean contains(Point p) {
        return p.y >= this.row && p.y < this.row + this.numRows
                && p.x >= this.col && p.x < this.col + this.numCols;
    }

    Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }
}
