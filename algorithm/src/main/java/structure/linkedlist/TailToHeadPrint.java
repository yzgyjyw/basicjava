package structure.linkedlist;

public class TailToHeadPrint {

    public static void main(String[] args) {
        LinkList linkList = LinkList.getInstance();

        tailToHeadPrint(linkList.getHead());

    }

    public static void tailToHeadPrint(LinkList.ListNode head) {
        if (head.getNext()==null) {
            System.out.println(head.getData());
            return;
        }

        tailToHeadPrint(head.getNext());

        System.out.println(head.getData());

    }
}
