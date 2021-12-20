package grupo1.tresenraya.DS;

public class EmptyHeapException extends RuntimeException {
    public EmptyHeapException() {
        super("Tried to pop from an empty heap!");
    }
}
