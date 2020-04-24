package structure;

public class LinkList {

    private ListNode head;


    public LinkList(ListNode head) {
        this.head = head;
    }

    public void add(ListNode node) {

        ListNode p = head;

        while (p.getNext() != null) {
            p = p.getNext();
        }

        // 得到尾部节点

        p.setNext(node);
    }

    public ListNode getHead() {
        return head;
    }

    public static class ListNode {
        private ListNode next;

        private int data;

        public ListNode(int data) {
            this.data = data;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }

}


