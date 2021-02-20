package structure.linkedlist;

public class DeleteListNode {

    public static void main(String[] args) {
        LinkList linkList = LinkList.getInstance();

        deleteListNode(linkList.getHead(), linkList.getHead().getNext().getNext().getNext().getNext().getNext());

        System.out.println(1);
    }

    public static void deleteListNode(LinkList.ListNode head, LinkList.ListNode deleted) {
        LinkList.ListNode next = deleted.getNext();

        if (next == null) {
            deleted = null;
            return;
        }

        deleted.setData(next.getData());
        deleted.setNext(next.getNext());
    }
}
