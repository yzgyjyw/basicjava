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

        for(int i=0;i<5;i++){
            System.out.println(heap.pop());
            System.out.println(heap);
        }
    }
}
