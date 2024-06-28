package structure.linkedlist;

public class DeleteRepeatNode {


    public static void main(String[] args) {
        LinkList.ListNode listNode = new LinkList.ListNode(1);
        LinkList.ListNode listNode2 = new LinkList.ListNode(1);
        LinkList.ListNode listNode3 = new LinkList.ListNode(2);
        LinkList.ListNode listNode4 = new LinkList.ListNode(2);
        LinkList.ListNode listNode5 = new LinkList.ListNode(3);
        LinkList.ListNode listNode6 = new LinkList.ListNode(3);

        listNode.setNext(listNode2);
        listNode2.setNext(listNode3);
        listNode3.setNext(listNode4);
        listNode4.setNext(listNode5);
        listNode5.setNext(listNode6);

        deleteRepeatNode(listNode);
        System.out.println(listNode);
    }

    public static void deleteRepeatNode(LinkList.ListNode node) {

        LinkList.ListNode p = node;

        while (p.getNext()!=null){
            if(p.getData()==p.getNext().getData()){
                p.setNext(p.getNext().getNext());
            }else{
                p =  p.getNext();
            }
        }

    }

}
