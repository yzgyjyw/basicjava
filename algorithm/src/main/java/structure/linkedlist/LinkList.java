package structure.linkedlist;

public class LinkList {

    private ListNode head;


    public LinkList(ListNode head) {
        this.head = head;
    }

    public static LinkList getInstance() {
        ListNode head = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);
        ListNode fifth = new ListNode(5);
        ListNode sixth = new ListNode(6);
        ListNode seventh = new ListNode(7);

        head.setNext(second);
        second.setNext(third);
        third.setNext(fourth);
        fourth.setNext(fifth);
        fifth.setNext(sixth);
        sixth.setNext(seventh);

        return new LinkList(head);
    }


    public void add(ListNode node) {
        ListNode p = head;

        while (p.getNext() != null) {
            p = p.getNext();
        }

        // 得到尾部节点

        p.setNext(node);
    }

    public static void printLinkList(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.println(p.getData());
            p = p.getNext();
        }
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


