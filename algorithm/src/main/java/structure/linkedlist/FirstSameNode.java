package structure.linkedlist;

public class FirstSameNode {

    public static void main(String[] args) {

        LinkList linkList = LinkList.getInstance();


        LinkList.ListNode head = new LinkList.ListNode(10);

        head.setNext(linkList.getHead().getNext());

        int firstSameNode = getFirstSameNode(linkList.getHead(), head);

        System.out.println(firstSameNode);


    }

    public static int getFirstSameNode(LinkList.ListNode head1, LinkList.ListNode head2) {
        int n1 = 0;
        int n2 = 0;

        LinkList.ListNode p1 = head1;
        LinkList.ListNode p2 = head2;

        while (p1 != null) {
            n1++;
            p1 = p1.getNext();
        }

        while (p2 != null) {
            n2++;
            p2 = p2.getNext();
        }

        LinkList.ListNode fast = n1 > n2 ? head1 : head2;
        LinkList.ListNode slow = n1 > n2 ? head2 : head1;

        int step = Math.abs(n1 - n2);

        while (step > 0) {
            step--;
            fast = fast.getNext();
        }

        while (fast != null) {

            if (fast.getData() == slow.getData()) {
                return fast.getData();
            }

            fast = fast.getNext();
            slow = slow.getNext();

        }

        return -1;
    }

}
