package top100;

import structure.linkedlist.ListNode;

public class TwoSum {
    // 342 + 465 = 807
    // 2 4 3
    // 5 6 4
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;

        ListNode listNode4 = new ListNode(5);
        ListNode listNode5 = new ListNode(6);
        ListNode listNode6 = new ListNode(4);
        listNode4.next = listNode5;
        listNode5.next = listNode6;

        ListNode listNode = addTwoNumbers(listNode1, listNode4);

        System.out.println(1);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(-1);
        ListNode tail = result;

        ListNode p1 = l1;
        ListNode p2 = l2;

        int jinwei = 0;
        while(p1!=null && p2!=null){
            int current = (p1.val + p2.val + jinwei) % 10;
            jinwei = (p1.val + p2.val + jinwei) / 10;

            tail.next = new ListNode(current);
            tail = tail.next;

            p1 = p1.next;
            p2 = p2.next;
        }

        while(p1!=null){
            int current = (p1.val + jinwei) % 10;
            jinwei = (p1.val + jinwei) / 10;

            tail.next = new ListNode(current);
            tail = tail.next;

            p1 = p1.next;
        }

        while(p2!=null){
            int current = (p2.val + jinwei) % 10;
            jinwei = (p2.val + jinwei) / 10;

            tail.next = new ListNode(current);
            tail = tail.next;

            p2 = p2.next;
        }

        if(jinwei!=0){
            tail.next = new ListNode(jinwei);
        }

        return result.next;
    }
}
