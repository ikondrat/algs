import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rc = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            rc.enqueue(StdIn.readString());
        }

        for (String item: rc)
            if (k > 0) {
                StdOut.println(item);
                k--;
            }

    }
}
