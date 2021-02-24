public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    // false blocked ; true open
    private final boolean[] grid;
    private final int[] parent;
    private final int[] size;
    private final int n;

    private int nrOpenSites = 0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is < = 0");
        }

        int nn = n * n;
        this.n = n;
        this.grid = new boolean[nn];
        this.parent = new int[nn];
        this.size = new int[nn];

        for (int i = 0; i < nn; i++) {
            this.grid[i] = false;
            this.size[i] = 1;
            this.parent[i] = i;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            this.nrOpenSites++;
        } else {
            return;
        }
        row--;
        col--;

        int index = row * this.n + col;
        this.grid[index] = true;
        unionCell(row, col, index);
    }

    private void unionCell(int row, int col, int index) {
        // top
        if ((row - 1) >= 0) {
            int topIndex = (row - 1) * this.n + col;
            if (this.grid[topIndex]) {
                union(index, topIndex);
            }
        }

        // left
        if ((col - 1) >= 0) {
            int leftIndex = row * this.n + (col - 1);
            if (this.grid[leftIndex]) {
                union(index, leftIndex);
            }
        }

        // right
        if ((col + 1) < this.n) {
            int rightIndex = row * this.n + (col + 1);
            if (this.grid[rightIndex]) {
                union(index, rightIndex);
            }
        }

        // bottom
        if ((row + 1) < this.n) {
            int bottomIndex = (row + 1) * this.n + col;
            if (this.grid[bottomIndex]) {
                union(index, bottomIndex);
            }
        }
    }

    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (this.size[rootP] < this.size[rootQ]) {
            this.parent[rootP] = rootQ;
            this.size[rootQ] += this.size[rootP];
        } else {
            this.parent[rootQ] = rootP;
            this.size[rootP] += this.size[rootQ];
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        validateCoordinates(row, col);
        int index = row * this.n + col;
        return this.grid[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }

        row--;
        col--;

        int index = row * this.n + col;
        int parentP = find(index);

        for (int i = 0; i < this.n; i++) {
            if (isOpen(1, i + 1)) {
                int parentQ = find(i);
                if (parentQ == parentP) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.nrOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int topParents[] = new int[this.n];
        for (int i = 0; i < this.n; i++) {
            if(isOpen(1, i + 1)) {
                int parentP = find(i);
                topParents[i] = parentP;
            }
        }

        for (int i = 0; i < this.n; i++) {
            int index = this.n * (this.n - 1) + i;
            if(isOpen(this.n, i + 1)) {
                int parentP = find(index);
                for (int j = 0; j < this.n; j++) {
                    if(parentP == topParents[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void validateCoordinates(int row, int col) {
        validate(row);
        validate(col);
    }

    private void validate(int p) {
        if (p < 0 || p >= this.n) {
            throw new IllegalArgumentException("p is not in range of 0 and " + this.n);
        }
    }

    private int find(int p) {
        while (p != this.parent[p]) {
            p = this.parent[p];
        }
        return p;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 7;
        Percolation percolation = new Percolation(n);
        System.out.println("percolates = " + percolation.percolates());
    }
}
