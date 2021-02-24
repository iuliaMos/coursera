import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;    // first element in queue
    private Node<Item> last;     // last element in queue
    private int size;            // queue size

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldFirst = this.first;
        this.first = new Node<>();
        this.first.item = item;
        this.first.next = oldFirst;
        if (isEmpty()) {
            this.last = this.first;
        } else {
            oldFirst.previous = this.first;
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldlast = this.last;
        this.last = new Node<>();
        this.last.item = item;
        this.last.next = null;
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldlast.next = this.last;
            this.last.previous = oldlast;
        }
        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }
        Item item = this.first.item;
        this.first = this.first.next;
        this.size--;
        if (isEmpty()) {
            this.last = null;
        } else {
            this.first.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }
        Item item = this.last.item;
        this.last = this.last.previous;
        this.size--;
        if (isEmpty()) {
            this.last = null;
            this.first = null;
        } else {
            this.last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(this.first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.removeFirst();

        deque.addFirst(2);
        deque.removeFirst();

        deque.addLast(3);
        deque.removeFirst();

        deque.addLast(4);
        deque.removeLast();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                deque.addFirst(i);
            } else {
                deque.addLast(i);
            }

            if (i == 5) {
                System.out.println("Remove last: " + deque.removeLast());
            }
            if (i > 7) {
                System.out.println("Remove first: " + deque.removeFirst());
            }
        }
        Iterator<Integer> iterator = deque.iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

}