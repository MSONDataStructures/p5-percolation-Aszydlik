import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/******************************************************************************
 *  Name:    Kevin Wayne
 *  Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *  Description:  Modeling Percolation.
 ******************************************************************************/
public class Percolation {

    private final WeightedQuickUnionUF grid;
    private final boolean[] openSites;
    private final int size;
    private int openSiteCount;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }

        size = n;
        int totalSites = n * n + 2;
        grid = new WeightedQuickUnionUF(totalSites);
        openSites = new boolean[totalSites - 2];


        for (int i = 0; i < n; i++) {
            grid.union(n * n, i);
        }


        for (int i = n * (n - 1); i < n * n; i++) {
            grid.union(n * n + 1, i);
        }
    }
    private int to1D(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }

        if (isOpen(row, col)) return;

        int index = to1D(row, col);
        openSites[index] = true;
        openSiteCount++;

        if (row > 1 && isOpen(row - 1, col)) grid.union(index, to1D(row - 1, col));
        if (row < size && isOpen(row + 1, col)) grid.union(index, to1D(row + 1, col));
        if (col > 1 && isOpen(row, col - 1)) grid.union(index, to1D(row, col - 1));
        if (col < size && isOpen(row, col + 1)) grid.union(index, to1D(row, col + 1));
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        return openSites[to1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
        return grid.connected(to1D(row, col), size * size);
    }

    public int numberOfOpenSites() {
        return openSiteCount;
    }

    public boolean percolates() {


        return grid.connected(size * size, size * size + 1);
    }

    public static void main(String[] args) {
        // TODO: test client (optional)
    }
}
