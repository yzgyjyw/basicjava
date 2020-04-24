package search;

import java.util.Random;

// 跳表
public class SkipList {


    private SkipListNode head;

    public SkipList(int data) {
        head = new SkipListNode(data);
    }

    public void add(int data) {

    }

    private int randomLevel() {

        Random random = new Random();

        // 最多生成4层指针,每一层的概率为(1/2)^n
        int i = 0;
        for (; i < 4; i++) {
            if (random.nextDouble() <= Math.pow(0.5, i)) {
                continue;
            }
            break;
        }

        return i;
    }


    static class SkipListNode {
        private int data;

        private SkipListNode[] nexts = new SkipListNode[4];

        public SkipListNode(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public SkipListNode[] getNexts() {
            return nexts;
        }

        public void setNexts(SkipListNode[] nexts) {
            this.nexts = nexts;
        }

        public SkipListNode getMaxStepNextNode() {
            for (int i = 3; i >= 0; i++) {
                if (nexts[i] != null) {
                    return nexts[i];
                }
            }
            return null;
        }
    }

}
