package structure.linkedlist;

import java.util.LinkedList;

public class ReverseLinkList {

    public static void main(String[] args) {

        LinkList linkList = LinkList.getInstance();

        LinkList.ListNode reverse = reverse(linkList.getHead());

        LinkList.printLinkList(reverse);


    }

    public static LinkList.ListNode reverse(LinkList.ListNode head) {

        LinkList.ListNode result = null;

        LinkList.ListNode p = head;

        while (p != null) {

            LinkList.ListNode next = p.getNext();

            p.setNext(result);
            result = p;

            p = next;
        }

        return result;
    }
}
