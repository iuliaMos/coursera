import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private static final double DEVIATION_CONST = 1.96;
    private final int t;
    private final double[] arrayX;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.t = trials;

        this.arrayX = new double[this.t];

        double nn = n * n;

        for (int i = 0; i < this.t; i++) {
            Percolation percolation = new Percolation(n);

            do {
                int row = StdRandom.uniform(1, (n + 1));
                int col = StdRandom.uniform(1, (n + 1));
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            } while (!percolation.percolates());
            double value = percolation.numberOfOpenSites() / nn;
            this.arrayX[i] = value;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arrayX);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arrayX);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - DEVIATION_CONST * stddev() / Math.sqrt(this.t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + DEVIATION_CONST * stddev() / Math.sqrt(this.t);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException();
        }

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats tPercolationStats = new PercolationStats(n, t);

        System.out.println("mean                        = " + tPercolationStats.mean());
        System.out.println("stddev                      = " + tPercolationStats.stddev());
        System.out.println("95% confidence interval     = [" +
                tPercolationStats.confidenceLo() + ", " + tPercolationStats.confidenceHi() + "]");
    }
}
