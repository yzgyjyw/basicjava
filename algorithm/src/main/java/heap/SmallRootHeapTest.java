package heap;

public class SmallRootHeapTest {
    public static void main(String[] args) {
        SmallRootHeap heap = new SmallRootHeap(10);
        heap.add(10);
        heap.add(9);
        heap.add(7);
        heap.add(12);
        heap.add(1);
        System.out.println(heap);

        heap.pop();
        System.out.println(heap);
    }
}
