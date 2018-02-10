import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    /**
     * interval value.
     */
    private static double intervalValue = 1.96;
    /**
     * number of trials.
     */
    private int trials;

    /**
     * results of experiments stores ammount of opened sites on threshhold.
     */
    private double[] results;

    /**
     * @param gridSize size of grid
     * @param t ammount of attempts
     */
    public PercolationStats(final int gridSize, final int t) {
        if (gridSize <= 0 || t <= 0) throw new IllegalArgumentException("illegal arguments for constructor");
        trials = t;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation pc = new Percolation(gridSize);
            double j = 0;
            while (!pc.percolates()) {
                int x = StdRandom.uniform(1, gridSize + 1),
                    y = StdRandom.uniform(1, gridSize + 1);

                if (!pc.isOpen(x, y)) {
                    pc.open(x, y);
                    j++;
                }
            }
            results[i] = j / (gridSize * gridSize);
        }

    }

    /**
     * sample mean of percolation threshold.
     * @return mean of attempts
     */
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold

    /**
     * @return standart deviation
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo()  {
        return this.mean()
            -
            (intervalValue * this.stddev()) / Math.sqrt(trials);
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return this.mean()
            + ((intervalValue * this.stddev()) / Math.sqrt(trials));
    }

    /**
     * @param args test data
     */
    public static void main(final String[] args) {
        PercolationStats pcStat = new PercolationStats(
            Integer.parseInt(args[0]),
            Integer.parseInt(args[1])
        );

        System.out.println("mean\t= " + pcStat.mean());
        System.out.println("stddev\t= " + pcStat.stddev());
        System.out.println("95% confidence interval\t= "
            +
            pcStat.confidenceLo()
            +
            ", "
            +
            pcStat.confidenceHi()
        );
    }
}
