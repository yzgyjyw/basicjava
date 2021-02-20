package structure.linkedlist;

public class LastKIndexNode {

    public static void main(String[] args) {
        // 总数是n,倒数第k个节点,从前往后数就是第n-k+1个,两个指针,一个先走到第k个节点

        LinkList linkList = LinkList.getInstance();

        LinkList.ListNode lastKIndexNode = getLastKIndexNode(linkList.getHead(), 2);

        if (lastKIndexNode != null) {
            System.out.println(lastKIndexNode.getData());
        }

    }

    public static LinkList.ListNode getLastKIndexNode(LinkList.ListNode head, int k) {
        int fastCount = k;

        LinkList.ListNode p = head;

        while (p != null && fastCount != 0) {
            fastCount--;
            p = p.getNext();
        }

        LinkList.ListNode slow = head;

        while (p != null) {
            p = p.getNext();

            slow = slow.getNext();
        }

        return slow;
    }


}
