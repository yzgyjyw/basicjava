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
        LinkList.ListNode p1 = head1;

        LinkList.ListNode p2 = head2;

        int n1 = 0;
        int n2 = 0;

        while (p1 != null) {
            p1 = p1.getNext();
            n1++;
        }

        while (p2 != null) {
            p2 = p2.getNext();
            n2++;
        }

        LinkList.ListNode longerList = n1 > n2 ? head1 : head2;
        LinkList.ListNode shorterList = n1 > n2 ? head2 : head1;

        int step = Math.abs(n1 - n2);

        while (step > 0) {
            longerList = longerList.getNext();
            step--;
        }

        while (longerList != null && shorterList != null) {

            if (longerList.getData() == shorterList.getData()) {
                return longerList.getData();
            }

            longerList = longerList.getNext();

            shorterList = shorterList.getNext();
        }

        return -1;
    }

}
