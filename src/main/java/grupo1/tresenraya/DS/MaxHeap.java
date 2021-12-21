package grupo1.tresenraya.DS;

public class MaxHeap<E extends Comparable<E>> {
    private E[] elems;
    private int length;
    private int cap;

    public MaxHeap() {
        this(10);
    }

    public MaxHeap(int initialCapacity) {
        elems = (E[])new Comparable[initialCapacity];
        this.cap = initialCapacity;
    }

    @SafeVarargs
    public MaxHeap(E... elems) {
        length = elems.length + 1;
        cap = elems.length + 1;
        this.elems = elems;
        // Insertamos los elementos desde la posicion 1
        for (int i = 0; i < elems.length; i++) this.elems[i+1] = elems[i];
        // Restauramos la propiedad de la heap
        for (int k = length/2; k >= 1; k--)    sink(k);
    }

    public int size() { return length; }

    public E pop() {
        if (length < 1)
            throw new EmptyHeapException();
        E max = elems[1];
        elems[1] = elems[length];
        elems[length] = null;
        length -= 1;
        sink(1);
        return max;
    }

    public void insert(E elem) {
        if (length >= cap) enlarge();
        length += 1; // we start indexing at 1
        elems[length] = elem;
        swim(length);
    }

    private void enlarge() {
        cap = length * 2;
        E[] newElems = (E[])new Object[cap];
        for (int i = 1; i < length; i++)
            newElems[i] = elems[i];
        elems = newElems;
    }

    private E getParent(int i) { return elems[i/2]; }
    private E getLeft(int i) { return elems[i*2]; }
    private E getRight(int i) { return elems[i*2+1]; }

    // Starting from an element that has become greater than its parent,
    // repeatedly swap it with the parent until the heap order has been
    // restablished.
    private void swim(int k) {
        while (k>1 && elems[k/2].compareTo(elems[k]) < 0) {
            swap(k/2, k);
            k /= 2;
        }
    }

    // Starting from an element that has become smaller than any of its
    // children, repeatedly swap it with the smaller of its children until the
    // heap order is restablished.
    private void sink(int k) {
        while (2*k <= length) {
            int j = 2*k;
            if (j < length && elems[j].compareTo(elems[j+1]) > 0) j += 1;
            if (elems[k].compareTo(elems[j]) >= 0) break;
            swap(k, j);
            k = j;
        }
    }

    private void swap(int i, int j) {
        E tmp = elems[i];
        elems[i] = elems[j];
        elems[j] = tmp;
    }
}
