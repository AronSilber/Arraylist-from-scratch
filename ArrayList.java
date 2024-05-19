import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Aron Silberwasser
 * @version 1.0
 * @userid asilberwasser3
 * @GTID 903683147
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {

        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index cannot be < 0 or > size of array");
        } // if outbounds
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } // if invalid data

        /**
         * adding at an index
         * case 1: at max capacity
         *     after we check that its max size, we double the size
         *     then we copy elements until we reach the index, which we leave empty,
         *     then continue copying at index + 1.
         *
         *     then we just add data at index
         * case 2: not at max capacity
         *     we shift all elements after index
         */

        if (size() % INITIAL_CAPACITY == 0 && size() != 0) {
            T[] tempArr = getBackingArray();
            int tempSize = size();
            backingArray = (T[]) new Object[2 * tempSize];

            for (int i = 0; i < index; i++) {
                backingArray[i] = tempArr[i];
            }
            for (int i = index; i < tempSize; i++) {
                backingArray[i + 1] = tempArr[i];
            }
        } else {
            for (int i = size() - 1; i >= index; i--) {
                backingArray[i + 1] = getBackingArray()[i];
            }
        }

        getBackingArray()[index] = data;
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {

        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } // if invalid data

        if (size() % INITIAL_CAPACITY == 0 && size() != 0) {
            T[] tempArr = getBackingArray();
            int tempSize = size();
            backingArray = (T[]) new Object[2 * tempSize];

            for (int i = 0; i < size(); i++) {
                backingArray[i] = tempArr[i];
            }
        } // if size is at max, double size

        for (int i = size() - 1; i >= 0; i--) {
            backingArray[i + 1] = getBackingArray()[i];
        }

        getBackingArray()[0] = data;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {

        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } // if invalid data

        if (size() % INITIAL_CAPACITY == 0 && size() != 0) {
            T[] tempArr = getBackingArray();
            int tempSize = size();

            backingArray = (T[]) new Object[2 * tempSize];
            for (int i = 0; i < size(); i++) {
                backingArray[i] = tempArr[i];
            }
        } // if size is at max, double size

        getBackingArray()[size()] = data;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index cannot be < 0 or > size of array");
        } // if outbounds

        T data = getBackingArray()[index];
        for (int i = index; i < size(); i++) {
            backingArray[i] = getBackingArray()[i + 1];
        }
        backingArray[size()] = null;

        size--;
        return data;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {

        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty, cannot remove anything in the first place");
        }

        T data = getBackingArray()[0];
        for (int i = 0; i < size(); i++) {
            backingArray[i] = getBackingArray()[i + 1];
        }
        backingArray[size()] = null;

        size--;
        return data;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {

        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty, cannot remove anything in the first place");
        }

        T data = getBackingArray()[size()];
        backingArray[size()] = null;

        size--;
        return data;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        return getBackingArray()[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}