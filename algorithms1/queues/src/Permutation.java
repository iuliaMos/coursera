import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException();
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> qeque = new RandomizedQueue<>();

        int count = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if(qeque.size() == k) {
                int index = StdRandom.uniform(count);
                if (index < k) {
                    qeque.dequeue();
                    qeque.enqueue(item);
                }
            } else {
                qeque.enqueue(item);
            }
            count ++;
        }

        while (qeque.size() > 0 && k > 0) {
            System.out.println(qeque.dequeue());
            k--;
        }

    }
}
