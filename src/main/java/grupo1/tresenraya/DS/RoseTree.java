package grupo1.tresenraya.DS;

import java.util.Comparator;
import java.util.Iterator;

public class RoseTree<T> implements Iterable<RoseTree<T>> {
    private T content;
    private Heap<RoseTree<T>> children;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Heap<RoseTree<T>> getChildren() {
        return children;
    }

    public RoseTree(T content, Comparator<T> cmp) {
        this.content = content;
        this.children = new Heap<RoseTree<T>>((t1, t2) -> cmp.compare(t1.getContent(), t2.getContent()));
    }

    @SafeVarargs
    final public void addChildren(RoseTree<T>... ts) {
        for (RoseTree<T> t : ts)
            children.insert(t);
    }

    @Override
    public Iterator<RoseTree<T>> iterator() {
        return children.iterator();
    }

    @Override
    public String toString() {
        return  this.getContent().toString();
    }
}
