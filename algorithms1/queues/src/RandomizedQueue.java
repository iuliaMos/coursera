import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;
    private int last;       // index of last element

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.array = (Item[]) new Object[2];
        this.size = 0;
        this.last = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int count = 0;
        for (Item item : this.array) {
            if (item != null) {
                copy[count] = item;
                count++;
            }
        }
        this.last = count;
        this.array = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (this.size == this.array.length || this.last == this.array.length) {
            resize(2 * this.array.length);
        }
        this.array[this.last] = item;
        this.last++;
        this.size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        int index = getRandomIndex();
        Item returnItem = this.array[index];
        this.array[index] = null;

        if (index == this.last - 1) {
            while (this.last > 0 && this.array[this.last - 1] == null) {
                this.last--;
            }
        }

        this.size--;

        if (this.size > 0 && this.size == this.array.length / 4) {
            resize(this.array.length / 2);
        }
        return returnItem;
    }

    private int getRandomIndex() {
        if (this.size == 0) {
            return 0;
        }
        if (this.size == 1) {
            return this.last - 1;
        }

        int index = StdRandom.uniform(this.last);
        while(this.array[index] == null) {
            index = StdRandom.uniform(this.last);
        }

        return index;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return this.array[getRandomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int i;
        private final Item[] items;

        public RandomizedIterator() {
            i = size - 1;
            items = (Item[]) new Object[size];
            int count = 0;

            for (Item item : array) {
                if (item != null) {
                    items[count] = item;
                    count++;
                }
            }
            StdRandom.shuffle(items);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[i--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.enqueue(1);
        System.out.println("remove: " + rq.dequeue());

        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        for (Integer integer : rq) {
            System.out.print(integer + " ");
        }
    }
}