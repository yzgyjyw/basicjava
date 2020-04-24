package search;

import structure.LinkList;

import java.util.ArrayList;
import java.util.List;

public class SortedLinkedList {


    public static void main(String[] args) {

        LinkList list1 = new LinkList(new LinkList.ListNode(1));
        list1.add(new LinkList.ListNode(3));
        list1.add(new LinkList.ListNode(4));
        list1.add(new LinkList.ListNode(5));
        list1.add(new LinkList.ListNode(6));

        LinkList list2 = new LinkList(new LinkList.ListNode(2));
        list2.add(new LinkList.ListNode(4));
        list2.add(new LinkList.ListNode(5));
        list2.add(new LinkList.ListNode(6));
        list2.add(new LinkList.ListNode(8));
        list2.add(new LinkList.ListNode(10));

        getIntersection(list1,list2);


    }


    // 有序数组求交集
    public static void getIntersection(LinkList list1, LinkList list2) {

        List<Integer> results = new ArrayList<>();

        LinkList.ListNode p1 = list1.getHead();
        LinkList.ListNode p2 = list2.getHead();


        while (p1!= null && p2!= null) {


            while (p1.getData() < p2.getData() && p1.getNext() != null) {
                p1 = p1.getNext();
            }

            while (p1.getData() > p2.getData() && p2.getNext() != null) {
                p2 = p2.getNext();
            }

            if(p1.getData() == p2.getData()){
                results.add(p1.getData());

                p1 = p1.getNext();
                p2 = p2.getNext();
            }
        }

        results.forEach(System.out::println);

    }

}
