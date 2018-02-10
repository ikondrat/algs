import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private static int N = 10;

    private static int start, size;
    private Item[] items;

    // construct an empty deque
    public Deque() {
        size = 0;
        start = N/2 - 1;
        items = (Item[]) new Object[N];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        N = capacity;
        Item[] copy = (Item[]) new Object[capacity];
        int first = capacity/4 - 1;
        for (int i = first, j = 0; j < size; i++) {
            copy[i] = items[start + 1 + j++];
        }


        start = capacity/4 - 2;
        items = copy;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException ("argument can't be null in addFirst");
        if (size() >= N/2) resize(N*2);
        int index = start >= 0 ? start-- : N - Math.abs(start--);
        items[index] = item;
        size++;
    }

    private int getLastIndex() {
        int index = start + size() + 1;
        if (index >= N) {
            index = index - N;
        }
        return index;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("argument can't be null in addLast");
        if (size() >= N/2) resize(N*2);
        items[getLastIndex()] = item;
        size++;
    }

    private void clear(int index) {
        items[index] = null;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) throw new java.util.NoSuchElementException("queue is empty on removeFirst");
        Item val = items[++start];
        clear(start);
        size--;

        if (size() <= N/4) resize(N/2);
        return val;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (size() == 0) throw new java.util.NoSuchElementException("queue is empty on removeLast");
        int index = getLastIndex();
        Item val = items[index];
        clear(index);
        size--;
        if (size() <= N/4) resize(N/2);
        return val;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements  Iterator<Item> {
        private int i = 0;
        public boolean hasNext() { return i < size; }
        public void remove() { /* not supported */ }
        public Item next()
        {
            if (i >= size) throw new java.util.NoSuchElementException("empty element on next");
            int index = start + 1 + i++;
            return items[index];
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque <Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addLast(3);
        dq.addLast(4);
        dq.addLast(4);
        dq.addLast(4);
        dq.addLast(4);
        dq.removeFirst();
        dq.addFirst(14);
        dq.addLast(20);
        dq.removeFirst();
        dq.removeLast();
        int s = dq.size();
        for (int num : dq)
            StdOut.println(num);
    }
}
