package structure.linkedlist;

public class DeleteListNode {

    public static void main(String[] args) {
        LinkList linkList = LinkList.getInstance();

        deleteListNode(linkList.getHead(),linkList.getHead().getNext().getNext().getNext().getNext().getNext().getNext());


    }

    public static void deleteListNode(LinkList.ListNode head, LinkList.ListNode deleted) {
        LinkList.ListNode next = deleted.getNext();

        // 尾节点直接删除
        if (next == null) {
            deleted = null;

        }else{
            int data = next.getData();
            deleted.setData(data);
            deleted.setNext(next.getNext());
            next = null;
        }

        LinkList.printLinkList(head);
    }
}
