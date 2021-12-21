package grupo1.tresenraya.DS;

import java.util.Comparator;

public class RoseTree<T extends Comparable<T>> implements Comparable<RoseTree<T>> {
    private T content;
    private MaxHeap<RoseTree<T>> children;

    public T getContent() {
        return content;
    }

    public MaxHeap<RoseTree<T>> getChildren() {
        return children;
    }

    public RoseTree(T content) {
        this.content = content;
        this.children = new MaxHeap<>();
    }

    public RoseTree(T content, RoseTree<T>... children) {
        this.content = content;
        this.children = new MaxHeap<>(children);
    }

    @SafeVarargs
    final public void addChildren(RoseTree<T>... ts) {
        for (RoseTree<T> t : ts)
            children.insert(t);
    }

    @Override
    public int compareTo(RoseTree<T> other) {
        return content.compareTo(other.content);
    }
}
