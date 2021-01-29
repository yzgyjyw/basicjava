package structure.linkedlist;

public class LastKIndexNode {

    public static void main(String[] args) {
        // 总数是n,倒数第k个节点,从前往后数就是第n-k+1个,两个指针,一个先走到第k个节点

        LinkList linkList = LinkList.getInstance();

        LinkList.ListNode lastKIndexNode = getLastKIndexNode(linkList.getHead(), 1);

        if (lastKIndexNode != null) {
            System.out.println(lastKIndexNode.getData());
        }

    }

    public static LinkList.ListNode getLastKIndexNode(LinkList.ListNode head, int k) {
        LinkList.ListNode first = head;

        int firstPtrPos = 1;

        while (first != null && firstPtrPos < k) {
            first = first.getNext();
            firstPtrPos++;
        }

        if (first == null) {
            return null;
        }

        LinkList.ListNode second = head;

        while (first.getNext() != null) {
            first = first.getNext();
            second = second.getNext();
        }

        return second;
    }


}
