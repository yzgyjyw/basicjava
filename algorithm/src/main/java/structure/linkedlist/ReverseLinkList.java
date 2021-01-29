package structure.linkedlist;

import java.util.LinkedList;

public class ReverseLinkList {

    public static void main(String[] args) {

        LinkList linkList = LinkList.getInstance();

        LinkList.ListNode reverse = reverse(linkList.getHead());

        LinkList.printLinkList(reverse);


    }

    public static LinkList.ListNode reverse(LinkList.ListNode head) {
        LinkList.ListNode reversed = null;

        LinkList.ListNode p = head;

        LinkList.ListNode prev;

        while (p != null) {
            LinkList.ListNode next = p.getNext();

            prev = p;
            prev.setNext(reversed);
            reversed = prev;

            p = next;
        }

        return reversed;
    }
}
