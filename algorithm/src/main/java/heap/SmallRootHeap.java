package heap;


import java.util.Arrays;

public class SmallRootHeap {
    private int[] array;
    private int length;
    private int size;

    public SmallRootHeap(int length) {
        this.length = length;
        this.array = new int[length];
    }

    //O(logn)
    public void add(int element) {
        if (size >= length) {
            throw new RuntimeException("exceed max length");
        }
        //将新增加的元素放在数组尾部,从下往上调整,比父节点小就往上继续

        int parent = (size - 1) / 2;
        int current = size;

        while (current > 0) {
            if (array[parent] > element) {
                array[current] = array[parent];
                current = parent;
                parent = (parent - 1) / 2;
            } else {
                break;
            }
        }

        array[current] = element;

        size++;
    }

    //针对大顶堆与小顶堆,删除操作针对的是根节点 O(logn)
    public int pop() {
        if (size <= 0) {
            throw new RuntimeException("没有元素");
        }
        int element = array[0];
        array[0] = array[size - 1];

        int current = 0;
        int left = current * 2 + 1;
        while (left < size) {
            int temp = left;
            if (left + 1 < size && array[left] > array[left + 1]) {
                temp = left + 1;
            }
            if (array[temp] < array[current]) {
                array[current] = array[temp];
                current = temp;
            }else{
                break;
            }
        }

        array[current] = array[size - 1];
        size--;
        return element;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
