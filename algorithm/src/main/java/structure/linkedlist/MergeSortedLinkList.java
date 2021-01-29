package structure.linkedlist;

public class MergeSortedLinkList {


    public static void main(String[] args) {
        LinkList.ListNode head = new LinkList.ListNode(1);
        LinkList linkList = new LinkList(head);
        linkList.add(new LinkList.ListNode(3));
        linkList.add(new LinkList.ListNode(5));

        LinkList.ListNode head2 = new LinkList.ListNode(2);
        LinkList linkList2 = new LinkList(head2);
        linkList2.add(new LinkList.ListNode(4));
        linkList2.add(new LinkList.ListNode(6));

        LinkList.ListNode listNode = mergeTwoSortedLinkedList(head, head2);
        LinkList.printLinkList(listNode);

    }


    public static LinkList.ListNode mergeTwoSortedLinkedList(LinkList.ListNode head1, LinkList.ListNode head2) {

        LinkList.ListNode p1 = head1;
        LinkList.ListNode p2 = head2;

        LinkList.ListNode result = null;
        LinkList.ListNode tail = null;

        while (p1 != null && p2 != null) {

            if (p1.getData() <= p2.getData()) {
                if (result == null) {
                    result = p1;
                    tail = p1;
                } else {
                    tail.setNext(p1);
                    tail = p1;
                }
                p1 = p1.getNext();
            } else {
                if (result == null) {
                    result = p2;
                    tail = p2;
                } else {
                    tail.setNext(p2);
                    tail = p2;
                }
                p2 = p2.getNext();
            }
        }

        while (p1 != null) {
            tail.setNext(p1);
            tail = p1;
            p1 = p1.getNext();
        }

        while (p2 != null) {
            tail.setNext(p2);
            tail = p2;
            p2 = p2.getNext();
        }
        return result;
    }
}



