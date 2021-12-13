package grupo1.tresenraya.DS;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoseTree<T> {
    private T content;
    private Queue<RoseTree<T>> children;

    public T getContent() {
        return content;
    }

    public Queue<RoseTree<T>> getChildren() {
        return children;
    }

    public RoseTree(T content, Comparator<T> cmp) {
        this.content = content;
        this.children = new PriorityQueue(cmp);
    }

    @SafeVarargs
    final public void addChildren(RoseTree<T>... ts) {
        for (RoseTree<T> t : ts)
            children.offer(t);
    }
}
