final class Viewport
{
    public int row;
    public int col;
    public int numRows;
    public int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    //+shift()
    public void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    //+contains(): boolean
    public boolean contains(Point p) {
        return p.y >= row && p.y < row + numRows && p.x >= col && p.x < col + numCols;
    }
    //+viewportToWorld(): Point

    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, this.row + row);
    }

    //+worldToViewport(): Point

    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }
}
