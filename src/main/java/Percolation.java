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
        int totalSites = n * n + 2; // Add 2 for virtual top and bottom sites
        grid = new WeightedQuickUnionUF(totalSites);
        openSites = new boolean[totalSites - 2]; // Don't need booleans for virtual sites

        // Connect top row to virtual top site (index n*n)
        for (int i = 0; i < n; i++) {
            grid.union(n * n, i);
        }

        // Connect bottom row to virtual bottom site (index n*n + 1)
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

        if (isOpen(row, col)) return; // Site is already open

        int index = to1D(row, col);
        openSites[index] = true; // Mark the site as open
        openSiteCount++; // Increment the count of open sites

        // Connect to adjacent open sites (up, down, left, right)
        if (row > 1 && isOpen(row - 1, col)) grid.union(index, to1D(row - 1, col)); // Up
        if (row < size && isOpen(row + 1, col)) grid.union(index, to1D(row + 1, col)); // Down
        if (col > 1 && isOpen(row, col - 1)) grid.union(index, to1D(row, col - 1)); // Left
        if (col < size && isOpen(row, col + 1)) grid.union(index, to1D(row, col + 1)); // Right
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
        return grid.connected(to1D(row, col), size * size); // Connect to virtual top site
    }

    public int numberOfOpenSites() {
        return openSiteCount;
    }

    public boolean percolates() {
        // The system percolates if the virtual top site (index size*size)
        // is connected to the virtual bottom site (index size*size + 1)
        return grid.connected(size * size, size * size + 1); // Virtual top to virtual bottom
    }

    public static void main(String[] args) {
        // TODO: test client (optional)
    }
}
