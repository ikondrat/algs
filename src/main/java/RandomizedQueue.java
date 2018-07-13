import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[10];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Null is not acceptable value");
        items[size()] = item;
        size++;
        if (size() >= items.length/2) resize(items.length*2);
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = items[i];
        items = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        int randIndex = StdRandom.uniform(0, size());

        Item val = items[randIndex];
        items[randIndex] = items[size() - 1];
        items[size() - 1] = null;
        size--;

        if (size() <= items.length/4) resize(items.length/2);
        return val;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        return items[StdRandom.uniform(0, size())];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int i;
        private Item[] iteratorItems;
        public RandomIterator() {
            i = size;
            iteratorItems = (Item[]) new Object[size];
            for (int j = 0; j < i; j++) {
                iteratorItems[j] = items[j];
            }
            StdRandom.shuffle(iteratorItems);
        }

        public boolean hasNext() {
            return i > 0;
        }
        public void remove() { throw new java.lang.UnsupportedOperationException("Not supported"); }
        public Item next() {
            if (i == 0) throw new java.util.NoSuchElementException("iteration is done");
            int randIndex = i > 1 ? StdRandom.uniform(0, i - 1) : 0;
            Item val = iteratorItems[randIndex];
            iteratorItems[randIndex] = iteratorItems[i-1];
            iteratorItems[--i] = null;
            return val;
        }
    }
}
