package grupo1.tresenraya;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grupo1.tresenraya.DS.MaxHeap;
import grupo1.tresenraya.DS.EmptyHeapException;

public class TestMaxHeap {
    public static MaxHeap<Integer> pq;

    @BeforeEach
    public void setup() {
        pq = new MaxHeap<Integer>(4, 3, 2, 1);
    }

    @Test
    public void testInsert() {
        pq.insert(5);
        pq.insert(5);
        assertEquals(6, pq.size());
        assertEquals(5, pq.pop());
        assertEquals(5, pq.size());
        assertEquals(5, pq.pop());
        assertEquals(4, pq.size());
        assertEquals(4, pq.pop());
    }

    @Test
    public void testPop() {
        assertEquals(4, pq.size());
        assertEquals(4, pq.pop());
        assertEquals(3, pq.size());
        assertEquals(3, pq.pop());
        assertEquals(2, pq.size());
        assertEquals(2, pq.pop());
        assertEquals(1, pq.size());
        assertEquals(1, pq.pop());
        assertThrows(EmptyHeapException.class, pq::pop);
    }
}
