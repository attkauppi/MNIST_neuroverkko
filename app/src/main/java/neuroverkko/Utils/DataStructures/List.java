package neuroverkko.Utils.DataStructures;

public class List<T> {
    
    private T[] values;
    private int valuesAdded;

    public List() {
        this.values = (T[]) new Object[10];
        this.valuesAdded = 0;
    }

    public void add(T arvo) {
        if (this.valuesAdded == this.values.length) {
            grow();
        }

        this.values[this.valuesAdded] = arvo;
        this.valuesAdded++;
    }

    public void grow() {
        T[] new_values = (T[]) new Object[this.values.length * 3 / 2 + 1];

        for (int i = 0; i < this.values.length; i++) {
            new_values[i] = this.values[i];
        }

        this.values = new_values;
    }

    public boolean contains(T value) {
        if (value != null) {
            return false;
        }

        for (int i = 0; i < this.valuesAdded; i++) {
            if (value == this.values[i] || this.values[i].equals(value)) {
                return true;
            }
        }
        return false;
    }



    public void remove(T value) {

        int valuesIndex = valuesIndex(value);

        if (valuesIndex == -1) {
            return;
        }

        moveLeft(valuesIndex);
        this.valuesAdded--;
    }

    public T get(int index) {
        if (index < 0 ||index >= this.valuesAdded) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds [0, " + this.valuesAdded + " [");
        }

        return this.values[index];
    }

    /**
     * valuesIndex
     * 
     * Returns the index from which the value being sought after is found.
     * @param value
     * @return index (int) or -1 for not found.
     */
    public int valuesIndex(T value) {
        for (int i = 0; i < this.valuesAdded; i++) {
            if (value == this.values[i] ||this.values[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public void moveLeft(int beginningIndex) {
        for (int i = beginningIndex; i < this.valuesAdded; i++) {
            this.values[i] = this.values[i+1];
        }
    }

    public int size() {
        return this.valuesAdded;
    }

    
}
